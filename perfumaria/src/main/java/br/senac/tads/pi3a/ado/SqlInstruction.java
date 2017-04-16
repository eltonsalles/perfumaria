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
