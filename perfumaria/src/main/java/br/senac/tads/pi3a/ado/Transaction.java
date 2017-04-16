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

import java.sql.SQLException;
import javax.naming.NamingException;

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
        } catch (NamingException | InstantiationException | IllegalAccessException | SQLException e) {
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
