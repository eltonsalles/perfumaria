package br.senac.tads.pi3a.ado;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta classe provê meios para manipulação de uma instrução de INSERT no
 * banco de dados
 * 
 * @author Elton
 */
final public class SqlInsert extends SqlInstruction {
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
     * Não existe no contexto dessa classe, logo, irá lançar um 
     * erro se for executado
     * 
     * @param criteria 
     */
    @Override
    public void setCriteria(Criteria criteria) {
        try {
            throw new Exception("Cannot call setCriteria from SqlInsert");
        } catch (Exception ex) {
            
        }
    }

    /**
     * Retorna a instrução de INSERT em forma de string
     * 
     * @return 
     */
    @Override
    public String getInstruction() {
        String columns = "";
        String values = "";
        
        for (String key : this.columnValues.keySet()) {
            columns += key + ", ";
            values += this.columnValues.get(key) + ", ";
        }
        
        columns = columns.substring(0, columns.length() - 2);
        values = values.substring(0, values.length() - 2);
        
        this.sql = "INSERT INTO " + this.entity + " (";
        this.sql += columns + ")";
        this.sql += " values (" + values + ")";
        
        return this.sql;
    }
}
