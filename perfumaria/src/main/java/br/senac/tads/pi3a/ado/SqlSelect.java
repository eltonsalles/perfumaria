package br.senac.tads.pi3a.ado;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe provê meios para manipulação de uma instrução de SELECT
 * no banco de dados
 * 
 * @author Elton
 */
final public class SqlSelect extends SqlInstruction {
    private List<String> columns = new ArrayList<>();
    
    /**
     * Adiciona uma coluna a ser retornada pelo SELECT
     * 
     * @param column
     */
    public void addColumn(String column) {
        this.columns.add(column);
    }
    
    /**
     * Retorna a instrução de SELECT em forma de string
     * 
     * @return 
     */
    @Override
    public String getInstruction() {
        String select = "";
        
        for (int i = 0; i < this.columns.size(); i++) {
            String column = this.columns.get(i);
            
            select += column + ", ";
        }
        
        select = select.substring(0, select.length() - 2);
        
        this.sql = "SELECT " + select + " FROM " + this.entity;
        
        if (this.criteria != null) {
            String expression = this.criteria.dump();
            if (!expression.isEmpty()) {
                this.sql += " WHERE " + expression;
            }
            
            String order = (String) this.criteria.getProperty("order");
            Integer limit = (Integer) this.criteria.getProperty("limit");
            Integer offset = (Integer) this.criteria.getProperty("offset");
            
            if (order != null) {
                this.sql += " ORDER BY " + order;
            }
            
            if (limit != null) {
                this.sql += " LIMIT " + limit;
            }
            
            if (offset != null) {
                this.sql += " OFFSET " + offset;
            }
        }
        
        return this.sql;
    }
}
