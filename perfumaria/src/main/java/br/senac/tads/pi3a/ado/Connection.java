package br.senac.tads.pi3a.ado;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static java.sql.Connection open() {
        // Variáveis da classe Constants usadas para conectar ao banco
        String address = Constants.DB_ADDRESS;
        String user = Constants.DB_USER;
        String pass = Constants.DB_PASS;
        
        java.sql.Connection conn = null;
        
        try {
            // Registra o driver JDBC
            // Class.forName("org.apache.derby.jdbc.ClientDataSource");
            
            Properties properties = new Properties();
            properties.put("user", user);
            properties.put("password", pass);
            
            // Realiza a conexão com o banco de dados
            conn = DriverManager.getConnection(address, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
//        catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
        
        return conn;
    }
}
