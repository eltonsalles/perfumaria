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

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;

/**
 * Gerencia conexões com o banco de dados
 * 
 * @author Elton
 */
final public class Connection {
    /**
     * Não existirão instâncias de Connection, por isso estamos 
     * marcando-o como privte
     */
    private Connection (){}
    
    /**
     * Abre uma conexão com o banco de dados
     * 
     * @return
     * @throws NamingException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static java.sql.Connection open() 
            throws NamingException, InstantiationException, 
            IllegalAccessException {
        // Variáveis da classe Constants usadas para conectar ao banco
        String address = Constants.DB_ADDRESS;
        String user = Constants.DB_USER;
        String pass = Constants.DB_PASS;
        
        java.sql.Connection conn = null;
        
        try {
            // Registra o driver JDBC
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            
            Properties properties = new Properties();
            properties.put("user", user);
            properties.put("password", pass);
            
            // Realiza a conexão com o banco de dados
            conn = DriverManager.getConnection(address, properties);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return conn;
    }
}
