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

import br.senac.tads.pi3a.annotation.Columm;
import br.senac.tads.pi3a.annotation.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
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
        String methods[] = new String[
                nameClass.getClass().getDeclaredFields().length - 1];
        
        try {
            // Cria um objeto do tipo SqlInsert para montar a instrução SQL
            SqlInsert sql = new SqlInsert();
            
            // Pega o nome da tabela pela anotação Table.name
            Table entity = nameClass.getClass().getAnnotation(Table.class);
            sql.setEntity(entity.name());
            
            // Método para montar a instrução sql com ? (interrogação)
            setRowData(nameClass, sql, methods);
            
            // Abre um conexão
            Transaction.open();
            
            // Pega a conexão aberta
            java.sql.Connection conn = Transaction.get();
            
            // Prepara o sql para fazer a inserção
            PreparedStatement stmt = conn.prepareStatement(sql.getInstruction());
            
            // Método para setar na classe PreparedStatement
            setStmt(methods, nameClass, stmt);
            
            // Executa o SQL
            result = stmt.execute();
            
            // Fecha o stmt
            stmt.isClosed();
            
            // Fecha a transação com o banco de dados
            Transaction.close();
        } catch (Exception e) {
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
     * Percorre todos os atributos e preenche com ? (interrogação) 
     * É necessário que cada atributo tenha a anotação Columm(name="")
     * 
     * @param nameClass
     * @param sql
     * @param methods 
     */
    private static void setRowData(Object nameClass, SqlInsert sql, 
            String methods[]) {
        int cont = 0;
        
        for (Field field : nameClass.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Columm.class) && 
                    !field.getName().equalsIgnoreCase("id")) {
                // Pega o nome do campo conforme a tabela no banco de dados
                Columm columm = field.getAnnotation(Columm.class);

                // Insere o nome do campo para montar a intrução SQL
                sql.setRowData(columm.name(), "?");

                // Guarda em ordem o nome do método get referente ao field
                methods[cont] = nameMethod(field.getName());
                cont++;
            }
        }
    }
    
    /**
     * Percorre o vetor com os métodos getters e seta no stmt 
     * conforme o necessário
     * 
     * @param methods
     * @param nameClass
     * @param stmt 
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private static void setStmt(String methods[], Object nameClass, 
            PreparedStatement stmt) {
        int cont = 1;
        
        try {
            for (String m : methods) {
                if (m != null) {
                    Method method = nameClass.getClass().getMethod(m);
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
                        throw new Exception("Tipo de campo não identificado!");
                    }

                    cont++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
