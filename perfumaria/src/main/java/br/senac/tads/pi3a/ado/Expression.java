package br.senac.tads.pi3a.ado;

/**
 * Classe absttrata para gerenciar expressões
 * 
 * @author Elton
 */
public abstract class Expression {
    // Operadores lógicos
    public static final String AND_OPERATOR = "AND ";
    public static final String OR_OPERATOR = "OR ";
    
    // Método obrigatório
    public abstract String dump();
}
