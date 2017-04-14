package br.senac.tads.pi3a.ado;

/**
 * Esta classe provê os métodos em comum entre todas instruções
 * SQL (SELECT, INSERT, DELETE e UPDATE)
 * 
 * @author Elton
 */
public abstract class SqlInstruction {
    protected String sql;
    protected Criteria criteria;
    protected String entity;
    
    /**
     * Define o nome da entidade (tabela) manipulada pela instrução SQL
     * 
     * @param entity Tabela
     */
    final public void setEntity(String entity) {
        this.entity = entity;
    }
    
    /**
     * Retorna o nome da entidade (tabela)
     * 
     * @return 
     */
    final public String getEntity() {
        return this.entity;
    }
    
    /**
     * Define um critério de seleção dos dados através da composição de um 
     * objeto to tipo Criteria, que oferece uma interface para definição de 
     * critérios
     * 
     * @param criteria Objeto Criteria
     */
    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }
    
    /**
     * Declarando-o como abstract obrigamos sua declaração nas classes filhas,
     * uma vez que seu comportamento será distinto em cada uma delas, 
     * configurando poliformismo
     * 
     * @return 
     */
    public abstract String getInstruction();
}
