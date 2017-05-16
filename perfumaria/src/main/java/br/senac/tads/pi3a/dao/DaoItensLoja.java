/*
 * The MIT License
 *
 * Copyright 2017 fabiano.bfcarvalho.
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
import br.senac.tads.pi3a.ado.SqlSelect;
import br.senac.tads.pi3a.ado.Transaction;
import br.senac.tads.pi3a.annotation.Table;
import br.senac.tads.pi3a.model.ItensLoja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author fabiano.bfcarvalho
 */
public class DaoItensLoja extends AbstractDao{

    public DaoItensLoja() {
        
    }
    
    public DaoItensLoja(ItensLoja model){
        super(model);
    }

    @Override
    public Model findOne(Model obj, int id) throws Exception {
        ResultSet resultSet;
        ResultSet resultSet2;
        PreparedStatement stmt = null;
        Connection conn = null;
        Model result = null;
        
        try {
            if (!obj.getClass().isAnnotationPresent(Table.class)) {
                return null;
            }
            
            Table table = obj.getClass().getAnnotation(Table.class);
            
            Criteria criteria = new Criteria();
            criteria.add(new Filter("produto_id", "=", "?"), Criteria.AND_OPERATOR);
            criteria.add(new Filter("loja_id", "=", "?"), Criteria.AND_OPERATOR);
            
            SqlSelect sql = new SqlSelect();
            sql.setEntity(table.name());
            sql.addColumn("*");
            sql.setCriteria(criteria);
            
            if (conn == null) {
                Transaction.open();
            
                conn = Transaction.get();
            }
            String s = sql.getInstruction();
            stmt = conn.prepareStatement(sql.getInstruction());
            stmt.setInt(1, id);
            stmt.setInt(2, 1); // #MOCK
            
            resultSet = stmt.executeQuery();
            
            if (resultSet.next()) {
                ItensLoja itensLoja = new ItensLoja();
                
                Criteria criteria2 = new Criteria();
                criteria2.add(new Filter("id", "=", "?"));
            
                SqlSelect sql2 = new SqlSelect();
                sql2.setEntity("produto");
                sql2.addColumn("*");
                sql2.setCriteria(criteria2);

                if (conn == null) {
                    Transaction.open();

                    conn = Transaction.get();
                }

                stmt = conn.prepareStatement(sql2.getInstruction());
                stmt.setInt(1, resultSet.getInt("produto_id"));

                resultSet2 = stmt.executeQuery();

                if (resultSet2.next()) {
                    Produto produto = new Produto();
                    produto.setId(resultSet2.getInt("id"));
                    produto.setNome(resultSet2.getString("nome"));
                    produto.setMarca(resultSet2.getString("marca"));
                    produto.setCategoria(resultSet2.getString("categoria"));
                    produto.setValorUnidadeMedida(resultSet2.getInt("vlr_unidade_medida"));
                    produto.setUnidadeMedida(resultSet2.getString("unidade_medida"));
                    produto.setGenero(resultSet2.getString("genero"));
                    produto.setDescricao(resultSet2.getString("descricao"));
                    
                    itensLoja.setProduto(produto);
                }
                
                itensLoja.setStatus(resultSet.getBoolean("status"));
                itensLoja.setDataCadastro(new Date(resultSet.getDate("data_cadastro").getTime()));
                itensLoja.setEstoque(resultSet.getInt("estoque"));
                itensLoja.setValorCompra(resultSet.getFloat("vlr_compra"));
                itensLoja.setValorVenda(resultSet.getFloat("vlr_venda"));
                
                result = itensLoja;
            }
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            
            if (conn != null && !conn.isClosed()) {
                Transaction.close();
            }
        }
        
        return result;
    }

    @Override
    public List<Model> findAll(Model obj) throws Exception {
        ResultSet resultSet;
        ResultSet resultSet2;
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
                ItensLoja itensLoja = new ItensLoja();
                
                Criteria criteria = new Criteria();
                criteria.add(new Filter("id", "=", "?"));
            
                SqlSelect sql2 = new SqlSelect();
                sql2.setEntity("produto");
                sql2.addColumn("*");
                sql2.setCriteria(criteria);

                if (conn == null) {
                    Transaction.open();

                    conn = Transaction.get();
                }

                stmt = conn.prepareStatement(sql2.getInstruction());
                stmt.setInt(1, resultSet.getInt("produto_id"));

                resultSet2 = stmt.executeQuery();

                if (resultSet2.next()) {
                    Produto produto = new Produto();
                    produto.setId(resultSet2.getInt("id"));
                    produto.setNome(resultSet2.getString("nome"));
                    produto.setMarca(resultSet2.getString("marca"));
                    produto.setCategoria(resultSet2.getString("categoria"));
                    produto.setValorUnidadeMedida(resultSet2.getInt("vlr_unidade_medida"));
                    produto.setUnidadeMedida(resultSet2.getString("unidade_medida"));
                    produto.setGenero(resultSet2.getString("genero"));
                    produto.setDescricao(resultSet2.getString("descricao"));
                    
                    itensLoja.setProduto(produto);
                }
                
                itensLoja.setStatus(resultSet.getBoolean("status"));
                itensLoja.setDataCadastro(new Date(resultSet.getDate("data_cadastro").getTime()));
                itensLoja.setEstoque(resultSet.getInt("estoque"));
                itensLoja.setValorCompra(resultSet.getFloat("vlr_compra"));
                itensLoja.setValorVenda(resultSet.getFloat("vlr_venda"));
                
                list.add(itensLoja);
            }
        } catch (SQLException e) {
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
}
