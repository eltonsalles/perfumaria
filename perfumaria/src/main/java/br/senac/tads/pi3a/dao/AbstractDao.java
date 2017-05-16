/*
 * The MIT License
 *
 * Copyright 2017 Elton.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.senac.tads.pi3a.dao;

import br.senac.tads.pi3a.ado.Criteria;
import br.senac.tads.pi3a.ado.Filter;
import br.senac.tads.pi3a.ado.SqlDelete;
import br.senac.tads.pi3a.ado.SqlInsert;
import br.senac.tads.pi3a.ado.SqlSelect;
import br.senac.tads.pi3a.ado.SqlUpdate;
import br.senac.tads.pi3a.ado.Transaction;
import br.senac.tads.pi3a.annotation.Columm;
import br.senac.tads.pi3a.annotation.ForeignKey;
import br.senac.tads.pi3a.annotation.Table;
import br.senac.tads.pi3a.model.Model;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elton
 */
public abstract class AbstractDao implements GenericDao<Model>{
    private boolean all = false;
    private Model model;
    private String entity;
    private Map<String, String> modelMap;

    /**
     * Contrustor padrão
     */
    public AbstractDao() {
        
    }
    
    /**
     * Construtor passando o objeto como parâmetro
     * 
     * @param model 
     */
    public AbstractDao(Model model) {
        this.model = model;
        this.entity = readEntity(model);
        this.modelMap = readModel(model);
    }

    /**
     * Faz inserção no banco de dados
     * 
     * @return
     * @throws Exception 
     */
    @Override
    @SuppressWarnings("UseSpecificCatch")
    public int insert() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            SqlInsert sql = new SqlInsert();
            sql.setEntity(this.entity);

            for (String key : this.modelMap.keySet()) {
                sql.setRowData(this.modelMap.get(key), "?");
            }

            Transaction.open();

            conn = Transaction.get();

            stmt = conn.prepareStatement(sql.getInstruction(),
                    Statement.RETURN_GENERATED_KEYS);

            this.setStmt(stmt);

            stmt.execute();
            ResultSet resultSet = stmt.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getInt(1); // Id
            }
        } catch (Exception e) {
            Transaction.rollback();
            throw new Exception(e.getMessage());
        } finally {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            
            if (conn != null && !conn.isClosed()) {
                Transaction.close();
            }
        }
        
        return -1;
    }

    /**
     * Faz alteração no banco de dados
     * 
     * @return
     * @throws Exception 
     */
    @Override
    public boolean update() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            Criteria criteria = new Criteria();
            criteria.add(new Filter("id", "=", "?"));
            
            SqlUpdate sql = new SqlUpdate();
            sql.setEntity(this.entity);
            sql.setCriteria(criteria);

            for (String key : this.modelMap.keySet()) {
                sql.setRowData(this.modelMap.get(key), "?");
            }

            Transaction.open();

            conn = Transaction.get();

            stmt = conn.prepareStatement(sql.getInstruction());

            this.setStmt(stmt);
            stmt.setInt(this.modelMap.size() + 1, this.model.getId());

            stmt.execute();
            
            return true;
        } catch (Exception e) {
            Transaction.rollback();
            throw new Exception(e.getMessage());
        } finally {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            
            if (conn != null && !conn.isClosed()) {
                Transaction.close();
            }
        }
    }

    /**
     * Faz a exclusão no banco de dados
     * 
     * @param id
     * @return
     * @throws Exception 
     */
    @Override
    public boolean delete(int id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            Criteria criteria = new Criteria();
            criteria.add(new Filter("id", "=", "?"));
            
            SqlDelete sql = new SqlDelete();
            sql.setEntity(this.entity);
            sql.setCriteria(criteria);

            Transaction.open();

            conn = Transaction.get();

            stmt = conn.prepareStatement(sql.getInstruction());

            stmt.setInt(1, id);

            stmt.execute();
            
            return true;
        } catch (Exception e) {
            Transaction.rollback();
            throw new Exception(e.getMessage());
        } finally {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            
            if (conn != null && !conn.isClosed()) {
                Transaction.close();
            }
        }
    }

    /**
     * Retorna um objeto preenchido
     * 
     * @param obj
     * @param id
     * @return
     * @throws Exception 
     */
    @Override
    @SuppressWarnings("UseSpecificCatch")
    public Model findOne(Model obj, int id) throws Exception {
        ResultSet resultSet;
        PreparedStatement stmt = null;
        Connection conn = null;
        Model result = null;
        
        try {
            if (!obj.getClass().isAnnotationPresent(Table.class)) {
                return null;
            }
            
            Table table = obj.getClass().getAnnotation(Table.class);
            
            Criteria criteria = new Criteria();
            criteria.add(new Filter("id", "=", "?"));
            
            SqlSelect sql = new SqlSelect();
            sql.setEntity(table.name());
            sql.addColumn("*");
            sql.setCriteria(criteria);
            
            if (conn == null) {
                Transaction.open();
            
                conn = Transaction.get();
            }
            
            stmt = conn.prepareStatement(sql.getInstruction());
            stmt.setInt(1, id);
            
            resultSet = stmt.executeQuery();
            
            if (resultSet.next()) {
                result = prepareModel(obj, resultSet);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (stmt != null && !stmt.isClosed() && !this.all) {
                stmt.close();
            }
            
            if (conn != null && !conn.isClosed() && !this.all) {
                Transaction.close();
            }
        }
        
        return result;
    }

    /**
     * Retorna uma lista de objetos preenchidos
     * 
     * @param obj
     * @return
     * @throws Exception 
     */
    @Override
    public List<Model> findAll(Model obj) throws Exception {
        // Para evitar que a conexão feche antes de finalizar o resultSet
        this.all = true;
        
        ResultSet resultSet;
        PreparedStatement stmt = null;
        Connection conn = null;
        List<Model> list = new ArrayList<>();
        
        try {
            if (!obj.getClass().isAnnotationPresent(Table.class)) {
                return null;
            }
            
            Table table = obj.getClass().getAnnotation(Table.class);
            
            SqlSelect sql = new SqlSelect();
            sql.setEntity(table.name());
            sql.addColumn("*");
            
            if (conn == null) {
                Transaction.open();
            
                conn = Transaction.get();
            }
            
            stmt = conn.prepareStatement(sql.getInstruction());
            
            resultSet = stmt.executeQuery();
            
            while(resultSet.next()) {
                list.add(prepareModel(obj, resultSet));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            
            if (conn != null && !conn.isClosed()) {
                Transaction.close();
            }
        }
        
        return list;
    }

    /**
     * Retorna uma lista de objetos preenchidos conforme o critério de busca
     * 
     * @param obj
     * @param field
     * @param criteria
     * @param value
     * @return
     * @throws Exception 
     */
    @Override
    public List<Model> findAll(Model obj, String field, String criteria,
            String value) throws Exception {
        // Para evitar que a conexão feche antes de finalizar o resultSet
        this.all = true;
        
        ResultSet resultSet;
        PreparedStatement stmt = null;
        Connection conn = null;
        List<Model> list = new ArrayList<>();
        
        try {
            if (!obj.getClass().isAnnotationPresent(Table.class)) {
                return null;
            }
            
            Table table = obj.getClass().getAnnotation(Table.class);
            
            Criteria c = new Criteria();
            c.add(new Filter(field, criteria, "?"));
            
            SqlSelect sql = new SqlSelect();
            sql.setEntity(table.name());
            sql.addColumn("*");
            sql.setCriteria(c);
            
            if (conn == null) {
                Transaction.open();
            
                conn = Transaction.get();
            }
            
            stmt = conn.prepareStatement(sql.getInstruction());
            stmt.setObject(1, value);
            
            resultSet = stmt.executeQuery();
            
            while(resultSet.next()) {
                list.add(prepareModel(obj, resultSet));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            
            if (conn != null && !conn.isClosed()) {
                Transaction.close();
            }
        }
        
        return list;
    }
    
    /**
     * Pega o nome da tabela no banco de dados
     * 
     * @param model
     * @return 
     */
    private String readEntity(Model model) {
        if (model.getClass().isAnnotationPresent(Table.class)) {
            Table table = model.getClass().getAnnotation(Table.class);
            
            return table.name();
        }
        
        return null;
    }
    
    /**
     * Faz uma mapa da classe. É guardado o nome do atributo e
     * o nome da coluna no banco de dados
     * 
     * @param model
     * @return 
     */
    private LinkedHashMap<String, String> readModel(Model model) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        
        Field[] fields = model.getClass().getDeclaredFields();
        
        for (Field field : fields) {
            if (field.isAnnotationPresent(ForeignKey.class)) {
                ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
                
                map.put(field.getName(), foreignKey.name());
            } else {
                if (field.isAnnotationPresent(Columm.class)) {
                    Columm columm = field.getAnnotation(Columm.class);
                    
                    map.put(field.getName(), columm.name());
                }
            }
        }
        
        return map;
    }
    
    /**
     * Recebe um objeto do tipo PreparedStatement e percorre todos os
     * atributos da model que estão no modelMap e faz o set
     * 
     * @param stmt
     * @throws Exception 
     */
    private void setStmt(PreparedStatement stmt) throws Exception {
        int pos = 1;
        for (String key : this.modelMap.keySet()) {
            char firstLetter = key.charAt(0);
            key = "get" + String.valueOf(firstLetter).toUpperCase()
                    + key.substring(1);

            Method method = this.model.getClass().getMethod(key);
            
            if (method.getReturnType().getName().equalsIgnoreCase("char")) {
                stmt.setString(pos, String.valueOf(method.invoke(this.model)));
            } else if (method.getReturnType().getName()
                    .contains("br.senac.tads.pi3a.model")) {
                Model foreignKey = (Model) method.invoke(this.model);
                
                Method m = foreignKey.getClass().getMethod("getId");
                stmt.setInt(pos, (int) m.invoke(foreignKey));
            } else {
                stmt.setObject(pos, method.invoke(this.model));
            }

            pos++;
        }
    }
    
    /**
     * Cria um objeto preenchendo os atributos com o resultado da consulta
     * 
     * @param obj
     * @param resultSet
     * @return
     * @throws Exception 
     */
    private Model prepareModel(Model obj, ResultSet resultSet)
            throws Exception {
        Model newObj = obj.getClass().newInstance();
        
        newObj.setId(resultSet.getInt("id"));

        // Se o objeto for igual ao atributo model, então não é
        // necessário fazer o mapa do objeto porque já foi feito no construtor
        Map<String, String> objMap;
        if (newObj == this.model) {
            objMap = this.modelMap;
        } else {
            objMap = this.readModel(newObj);
        }

        for (String key : objMap.keySet()) {
            char firstLetter = key.charAt(0);
            String nameMethod = String.valueOf(firstLetter).toUpperCase()
                    + key.substring(1);

            Field field = newObj.getClass().getDeclaredField(key);
            Method method = newObj.getClass().getMethod("set" + nameMethod,
                    field.getType());

            if (field.getType().getName().equalsIgnoreCase("char")) {
                method.invoke(newObj, resultSet.getString(objMap.get(key))
                        .charAt(0));
                
            } else if (field.getType().getName()
                    .contains("br.senac.tads.pi3a.model")) {
                Class clazz = Class
                        .forName("br.senac.tads.pi3a.model." + nameMethod);
                Model foreignKey = (Model) clazz.newInstance();

                int id = resultSet.getInt(objMap.get(key));
                foreignKey = this.findOne(foreignKey, id);

                method.invoke(newObj, foreignKey);
                
            } else {
                method.invoke(newObj, resultSet.getObject(objMap.get(key)));
            }
        }
        
        return newObj;
    }
}
