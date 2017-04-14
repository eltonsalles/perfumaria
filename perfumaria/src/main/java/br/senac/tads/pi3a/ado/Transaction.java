package br.senac.tads.pi3a.ado;

/**
 * 
 * 
 * @author Elton
 */
final public class Transaction {
    private static java.sql.Connection conn;
    
    /**
     * Está declarado como private para impedir que se crie 
     * instâncias de Transaction
     */
    private Transaction(){}
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static void open() {
        try {
            if (Transaction.conn == null) {
                Transaction.conn = Connection.open();
                Transaction.conn.setAutoCommit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static java.sql.Connection get() {
        return Transaction.conn;
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static void rollback() {
        try {
            if (Transaction.conn != null) {
                Transaction.conn.rollback();
                Transaction.conn.close();
                Transaction.conn = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static void close() {
        try {
            if (Transaction.conn != null) {
                Transaction.conn.commit();
                Transaction.conn.close();
                Transaction.conn = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
