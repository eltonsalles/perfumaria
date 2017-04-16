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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Esta classe provê meios para manipulação de uma instrução de UPDATE
 * no banco de dados
 * 
 * @author Elton
 */
final public class SqlUpdate extends SqlInstruction {
    private Map<String, Object> columnValues = new LinkedHashMap<>();
    
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
            if (value.equalsIgnoreCase("?")) {
                this.columnValues.put(column, value);
            } else {
                this.columnValues.put(column, "'" + value + "'");
            }
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
