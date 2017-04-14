package br.senac.tads.pi3a.ado;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta classe provê meios para manipulação de uma instrução de UPDATE
 * no banco de dados
 * 
 * @author Elton
 */
final public class SqlUpdate extends SqlInstruction {
    private Map<String, Object> columnValues = new HashMap<>();
    
    /**
     * Atribui valores do tipo String ou NULL à determinadas colunas no
     * banco de dados que serão inseridas
     * 
     * @param column
     * @param value String ou null
     */
    public void setRowData(String column, String value) {
        if (value.equalsIgnoreCase("null")) {
            this.columnValues.put(column, null);
        } else {
            this.columnValues.put(column, "'" + value + "'");
        }
    }
    
    /**
     * Atribui valores do tipo boolean à determinadas colunas no
     * banco de dados que serão inseridas
     * 
     * @param column
     * @param value 
     */
    public void setRowData(String column, boolean value) {
        if (value) {
            this.columnValues.put(column, "TRUE");
        } else {
            this.columnValues.put(column, "FALSE");
        }
    }
    
    /**
     * Atribui valores do tipo int à determinadas colunas no
     * banco de dados que serão inseridas
     * 
     * @param column
     * @param value 
     */
    public void setRowData(String column, int value) {
        this.columnValues.put(column, value);
    }
    
    /**
     * Atribui valores do tipo double à determinadas colunas no
     * banco de dados que serão inseridas
     * 
     * @param column
     * @param value 
     */
    public void setRowData(String column, double value) {
        this.columnValues.put(column, value);
    }

    /**
     * Retorna a instrução de UPDATE em forma de string
     * 
     * @return 
     */
    @Override
    public String getInstruction() {
        String set = "";
        
        this.sql = "UPDATE " + this.entity + " ";
        if (!this.columnValues.isEmpty()) {
            for (String key : this.columnValues.keySet()) {
                set += key + " = " + this.columnValues.get(key) + ", ";
            }

            set = set.substring(0, set.length() - 2);
        }
        this.sql += "SET " + set;
        if (this.criteria != null) {
            this.sql += " WHERE " + this.criteria.dump();
        }
        
        return this.sql;
    }
}
