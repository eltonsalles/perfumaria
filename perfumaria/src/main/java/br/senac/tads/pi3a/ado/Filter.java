package br.senac.tads.pi3a.ado;

/**
 * Esta classe provê uma interface para definição de filtros de seleção
 * @author Elton
 */
public class Filter extends Expression {
    private String variable;
    private String operator;
    private String value;
    
    /**
     * Instância um novo filtro usando um vetor de Object
     * 
     * @param variable
     * @param operator
     * @param value 
     */
    public Filter(String variable, String operator, Object value[]) {
        this.variable = variable;
        this.operator = operator;
        this.value = this.transform(value);
    }
    
    /**
     * Instância um novo filtro usando uma String
     * 
     * @param variable
     * @param operator
     * @param value 
     */
    public Filter(String variable, String operator, String value) {
        this.variable = variable;
        this.operator = operator;
        this.value = this.transform(value);
    }
    
    /**
     * Instância um novo filtro usando um boolean
     * 
     * @param variable
     * @param operator
     * @param value 
     */
    public Filter(String variable, String operator, boolean value) {
        this.variable = variable;
        this.operator = operator;
        this.value = this.transform(value);
    }
    
    /**
     * Instância um novo filtro usanto um int
     * 
     * @param variable
     * @param operator
     * @param value 
     */
    public Filter(String variable, String operator, int value) {
        this.variable = variable;
        this.operator = operator;
        this.value = this.transform(value);
    }
    
    /**
     * Instância um novo filtro usando um double
     * 
     * @param variable
     * @param operator
     * @param value 
     */
    public Filter(String variable, String operator, double value) {
        this.variable = variable;
        this.operator = operator;
        this.value = this.transform(value);
    }
    
    /**
     * Transforma o vetor de Object em uma string válida numa instrução SQL
     * 
     * @param value
     * @return 
     */
    private String transform(Object value[]) {
        String result = "";
        
        for (Object obj : value) {
            if (obj instanceof String) {
                result += "'" + obj + "', ";
            } else {
                result += String.valueOf(obj) + ", ";
            }
        }
        
        result = "(" + result.substring(0, result.length() - 2) + ")";
        
        return result;
    }
    
    /**
     * Verifica o valor de value e retorna NULL ou o padrão da 
     * escrita em string do SQL
     * 
     * @param value
     * @return 
     */
    private String transform(String value) {
        if (value.equalsIgnoreCase("null")) {
            return "NULL";
        }
        
        return "'" + value + "'";
    }
    
    /**
     * Retorna o boolean em uma string válida para usar como instrução no SQL
     * 
     * @param value
     * @return 
     */
    private String transform(boolean value) {
        if (value) {
            return "TRUE";
        }
        
        return "FALSE";
    }
    
    /**
     * Transforma o int em String
     * 
     * @param value
     * @return 
     */
    private String transform(int value) {
        return String.valueOf(value);
    }
    
    /**
     * Transforma o double em string
     * 
     * @param value
     * @return 
     */
    private String transform(double value) {
        return String.valueOf(value);
    }

    /**
     * Monta a instrução SQL
     * 
     * @return 
     */
    @Override
    public String dump() {
        return this.variable + " " + this.operator + " " + this.value;
    }
}