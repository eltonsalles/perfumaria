/*
 * The MIT License
 *
 * Copyright 2017 fabiano.bfcarvalho.
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
package br.senac.tads.pi3a.dao;

import br.senac.tads.pi3a.ado.Criteria;
import br.senac.tads.pi3a.ado.Expression;
import br.senac.tads.pi3a.ado.Filter;
import br.senac.tads.pi3a.ado.SqlSelect;
import br.senac.tads.pi3a.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fabiano.bfcarvalho
 */
public class DaoProduto extends AbstractDao{

    public DaoProduto(Connection conn) {
        super(conn);
    }    
    
    public DaoProduto(Connection conn, Produto model){
        super(conn, model);
    }
    
    /**
     * Verifica se o nome do produto já existe na loja informada
     * 
     * @param nomeProduto
     * @param idLoja
     * @return 
     */
    public int produtoExisteLoja(String nomeProduto, int idLoja) {
        try {
            PreparedStatement stmt;
            ResultSet resultSet;
            
            Criteria criteria = new Criteria();
            criteria.add(new Filter("produto.nome", "=", "?"),
                    Expression.AND_OPERATOR);
            criteria.add(new Filter("itens_loja.loja_id", "=", "?"),
                    Expression.AND_OPERATOR);
            
            SqlSelect sql = new SqlSelect();
            sql.setEntity("produto INNER JOIN itens_loja on"
                    + " itens_loja.produto_id = produto.id");
            sql.addColumn("id");
            sql.addColumn("nome");
            sql.setCriteria(criteria);
            
            stmt = this.getConnection().prepareStatement(sql.getInstruction());
            stmt.setString(1, nomeProduto);
            stmt.setInt(2, idLoja);
            
            resultSet = stmt.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return -1;
    }
}
