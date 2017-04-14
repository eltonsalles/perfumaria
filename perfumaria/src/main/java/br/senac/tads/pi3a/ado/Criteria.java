package br.senac.tads.pi3a.ado;

import java.util.ArrayList;
import java.util.HashMap;
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
    private Map<String, Object> properties = new HashMap<>();
    
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
