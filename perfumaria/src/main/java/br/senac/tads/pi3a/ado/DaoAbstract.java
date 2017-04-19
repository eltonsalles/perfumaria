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
package br.senac.tads.pi3a.ado;

import br.senac.tads.pi3a.annotation.Association;
import br.senac.tads.pi3a.annotation.Columm;
import br.senac.tads.pi3a.annotation.ForeignKey;
import br.senac.tads.pi3a.annotation.Table;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Elton
 */
public abstract class DaoAbstract {

    @SuppressWarnings("CallToPrintStackTrace")
    public static boolean insert(Object nameClass) {
        // Para retornar um resultado para insert
        boolean result = false;

        // Cria um vetor com o tamanho dos atributos da classe 
        // menos 1 (menos o id)
        ArrayList<Object[]> methods = new ArrayList<>();

        try {
            // Cria um objeto do tipo SqlInsert para montar a instrução SQL
            SqlInsert sql = new SqlInsert();

            // Pega o nome da tabela pela anotação Table.name
            Table entity = nameClass.getClass().getAnnotation(Table.class);
            sql.setEntity(entity.name());

            // Loop para montar a instrução sql
            for (Field declaredField : nameClass.getClass().getDeclaredFields()) {
                if (!declaredField.getName().equalsIgnoreCase("id")) {
                    if (declaredField.isAnnotationPresent(Association.class)) {
                        setRowDataAssociation(nameClass, declaredField, sql, methods);
                    } else if (declaredField.isAnnotationPresent(ForeignKey.class)) {
                        setRowDataForeignKey(declaredField, sql, methods);
                    } else {
                        setRowDataColumm(declaredField, sql, methods);
                    }
                }
            }

            // Abre um conexão
            Transaction.open();

            // Pega a conexão aberta
            java.sql.Connection conn = Transaction.get();

            // Prepara o sql para fazer a inserção
            String s = sql.getInstruction();
            PreparedStatement stmt = conn.prepareStatement(s);

            // Método para setar na classe PreparedStatement
            setStmt(methods, nameClass, stmt);

            // Executa o SQL
            result = stmt.execute();

            // Fecha o stmt
            stmt.isClosed();

            // Fecha a transação com o banco de dados
            Transaction.close();
        } catch (SecurityException | SQLException e) {
            // Reseta as transações no banco de dados e fecha a conexão
            Transaction.rollback();

            // Printa o erro
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Converter a string para o nome de um método padrão do tipo getter
     *
     * @param field
     * @return
     */
    private static String nameMethod(String field) {
        char firstLetter = field.charAt(0);
        return "get" + String.valueOf(firstLetter)
                .toUpperCase() + field.substring(1);
    }

    /**
     * Insere uma coluna simples na instrução SQL
     * 
     * @param field
     * @param sql
     * @param methods 
     */
    private static void setRowDataColumm(Field field, SqlInsert sql, ArrayList<Object[]> methods) {
        // Pega a annotation Columm
        Columm columm = field.getAnnotation(Columm.class);

        // Insere o nome do campo para montar a intrução SQL
        sql.setRowData(columm.name(), "?");

        // Guarda em ordem o nome do método get referente ao field
        String[] nameMethod = {nameMethod(field.getName())};
        
        // Adiciona o nome do método no ArrayList
        methods.add(nameMethod);
    }

    /**
     * Insere várias colunas na instrução SQL
     * 
     * @param nameClass
     * @param field
     * @param sql
     * @param methods 
     */
    private static void setRowDataAssociation(Object nameClass, Field field, SqlInsert sql, ArrayList<Object[]> methods) {
        try {
            // Pega a annotation Association
            Association association = field.getAnnotation(Association.class);
            
            // Pega o método que faz referencia a outra classe
            Method method = nameClass.getClass().getMethod(nameMethod(association.referenced()));
            
            // Chama o método que vai retornar uma classe
            Object object = method.invoke(nameClass);

            // Cria um vetor com o tamanho dos atributos mais 1
            String[] m = new String[object.getClass().getDeclaredFields().length + 1];
            
            // Na primeira posição sempre vai o método que chama a outra classe
            m[0] = nameMethod(association.referenced());
            
            // Percorre os atributos da outra classe 
            int count = 1;
            for (Field f : object.getClass().getDeclaredFields()) {
                // Verificação que a annotation Columm existe
                if (f.isAnnotationPresent(Columm.class)) {
                    // Pega a annotation Columm
                    Columm c = f.getAnnotation(Columm.class);

                    // Insere o nome do campo para montar a intrução SQL
                    sql.setRowData(c.name(), "?");
                    // Guarda em ordem o nome do método get referente ao field
                    m[count] = nameMethod(f.getName());
                    count++;
                }
            }
            // Adiciona o vetor com os nomes dos métodos no ArrayList
            methods.add(m);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
    }

    /**
     * Insere uma coluna do tipo Foreign Key na instrução SQL
     * 
     * @param field
     * @param sql
     * @param methods 
     */
    private static void setRowDataForeignKey(Field field, SqlInsert sql, ArrayList<Object[]> methods) {
        // Pega a annotation Foreign Key
        ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);

        // Insere o nome do campo para montar a intrução SQL
        sql.setRowData(foreignKey.name(), "?");

        // Guarda em ordem o nome do método get referente ao field
        String[] name = {nameMethod(field.getName())};
        
        // Adiciona o nome do método no ArrayList
        methods.add(name);
    }
    
    /**
     * Percorre o vetor com os métodos getters e seta no stmt conforme o
     * necessário
     *
     * @param methods
     * @param nameClass
     * @param stmt
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private static void setStmt(ArrayList<Object[]> methods, Object nameClass,
            PreparedStatement stmt) {
        int cont = 1;

        try {
            for (int i = 0; i < methods.size(); i++) {
                Object[] object = methods.get(i);

                if (object.length > 1) {
                    Method method = nameClass.getClass().getMethod((String) object[0]);
                    Object objectAssociation = method.invoke(nameClass);

                    for (int j = 1; j < object.length; j++) {
                        Method m = objectAssociation.getClass().getMethod((String) object[j]);
                        String nameMethod = m.getReturnType().getName();

                        if (nameMethod.equalsIgnoreCase("java.util.Date")) {
                            Date data = (Date) m.invoke(objectAssociation);
                            java.sql.Date dataSql = new java.sql.Date(data.getTime());

                            stmt.setDate(cont, dataSql);

                        } else if (nameMethod.equalsIgnoreCase("java.lang.String")) {
                            stmt.setString(cont, (String) m.invoke(objectAssociation));

                        } else if (nameMethod.equalsIgnoreCase("int")) {
                            stmt.setInt(cont, (Integer) m.invoke(objectAssociation));

                        } else if (nameMethod.equalsIgnoreCase("float")) {
                            stmt.setFloat(cont, (Float) m.invoke(objectAssociation));

                        } else if (nameMethod.equalsIgnoreCase("double")) {
                            stmt.setDouble(cont, (Double) m.invoke(objectAssociation));

                        } else if (nameMethod.equalsIgnoreCase("boolean")) {
                            stmt.setBoolean(cont, (Boolean) m.invoke(objectAssociation));

                        } else if (nameMethod.equalsIgnoreCase("char")) {
                            stmt.setString(cont, String.valueOf(m.invoke(objectAssociation)));

                        } else {
                            throw new Exception("Tipo de campo não identificado!");
                        }

                        cont++;
                    }
                } else {
                    //for (Object o : object) {
                    Method method = nameClass.getClass().getMethod((String) object[0]);
                    String nameMethod = method.getReturnType().getName();

                    if (nameMethod.equalsIgnoreCase("java.util.Date")) {
                        Date data = (Date) method.invoke(nameClass);
                        java.sql.Date dataSql = new java.sql.Date(data.getTime());

                        stmt.setDate(cont, dataSql);

                    } else if (nameMethod.equalsIgnoreCase("java.lang.String")) {
                        stmt.setString(cont, (String) method.invoke(nameClass));

                    } else if (nameMethod.equalsIgnoreCase("int")) {
                        stmt.setInt(cont, (Integer) method.invoke(nameClass));

                    } else if (nameMethod.equalsIgnoreCase("float")) {
                        stmt.setFloat(cont, (Float) method.invoke(nameClass));

                    } else if (nameMethod.equalsIgnoreCase("double")) {
                        stmt.setDouble(cont, (Double) method.invoke(nameClass));

                    } else if (nameMethod.equalsIgnoreCase("boolean")) {
                        stmt.setBoolean(cont, (Boolean) method.invoke(nameClass));

                    } else if (nameMethod.equalsIgnoreCase("char")) {
                        stmt.setString(cont, String.valueOf(method.invoke(nameClass)));

                    } else {
                        Object l = method.invoke(nameClass);
                        Method w = l.getClass().getMethod("getId");
                        stmt.setInt(cont, (int) w.invoke(l));
                    }

                    cont++;
                    //}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
