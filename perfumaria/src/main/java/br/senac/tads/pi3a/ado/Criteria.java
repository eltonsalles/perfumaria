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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Essa classe provê uma interface ultilizada para definição de critérios
 * 
 * @author Elton
 */
public class Criteria extends Expression {
    private List<Expression> expressions;
    private List<String> operators;
    private Map<String, Object> properties = new LinkedHashMap<>();
    
    /**
     * 
     */
    public Criteria() {
        this.expressions = new ArrayList<>();
        this.operators = new ArrayList<>();
    }
    
    /**
     * Adiciona uma expressão ao critério
     * 
     * @param expression Objeto Expression
     * @param operator Objeto Expression.AND_OPERATOR ou Expression.OR_OPERATOR
     */
    public void add(Expression expression, String operator) {
        
        if (this.expressions.isEmpty()) {
            operator = "";
        }
        
        this.expressions.add(expression);
        this.operators.add(operator);
    }
    
    /**
     * Retorna a expressão final
     * 
     * @return 
     */
    @Override
    public String dump() {
        if (this.expressions.size() > 0) {
            String result = "";
            for (int i = 0; i < expressions.size(); i++) {
                Expression expression = expressions.get(i);
                String operator = this.operators.get(i);
                
                result += operator + expression.dump() + " ";
            }
            
            result = result.trim();
            return "(" + result + ")";
        }
        
        return null;
    }
    
    /**
     * Define o valor de uma propriedade
     * @param property
     * @param value 
     */
    public void setProperty(String property, Object value) {
        this.properties.put(property, value);
    }
    
    /**
     * Retorna o valor de uma propriedade
     * @param property
     * @return 
     */
    public Object getProperty(String property) {
        if (this.properties.containsKey(property)) {
            return this.properties.get(property);
        }
        
        return null;
    }
}
