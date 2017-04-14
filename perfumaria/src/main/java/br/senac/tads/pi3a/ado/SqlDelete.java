package br.senac.tads.pi3a.ado;

/**
 * Esta classe provê meios para manipulação de uma instrução de DELETE
 * no banco de dados
 * 
 * @author Elton
 */
final public class SqlDelete extends SqlInstruction {
    /**
     * Retorna a instrução de DELETE em forma de string
     * 
     * @return 
     */
    @Override
    public String getInstruction() {
        this.sql = "DELETE FROM " + this.entity;
        if (this.criteria != null) {
            String expression = this.criteria.dump();
            if (!expression.isEmpty()) {
                this.sql += " WHERE " + expression;
            }
        }
        
        return this.sql;
    }
}
