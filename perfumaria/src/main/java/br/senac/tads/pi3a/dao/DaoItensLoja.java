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
import br.senac.tads.pi3a.ado.SqlDelete;
import br.senac.tads.pi3a.ado.SqlSelect;
import br.senac.tads.pi3a.ado.SqlUpdate;
import br.senac.tads.pi3a.ado.Transaction;
import br.senac.tads.pi3a.model.ItensLoja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fabiano.bfcarvalho
 */
public class DaoItensLoja extends AbstractDao {

    public DaoItensLoja(Connection conn) {
        super(conn);
    }

    public DaoItensLoja(Connection conn, ItensLoja model) {
        super(conn, model);
    }
    
    /**
     * Traz todos os produtos pesquisando pelo nome e id da loja
     * 
     * @param nomeProduto
     * @param idLoja
     * @return
     * @throws Exception 
     */
    public List findAllPorNome(String nomeProduto, int idLoja)
            throws Exception {
        List<Model> lista = new ArrayList<>();
        
        try {
            PreparedStatement stmt;
            ResultSet resultSetProduto, resultSetItensLoja;
            
            Criteria criteriaProduto = new Criteria();
            criteriaProduto.add(new Filter("UPPER(produto.nome)", "LIKE", "?"),
                    Expression.AND_OPERATOR);
            criteriaProduto.add(new Filter("itens_loja.loja_id", "=", "?"),
                    Expression.AND_OPERATOR);
            
            SqlSelect sqlProduto = new SqlSelect();
            sqlProduto.setEntity("produto INNER JOIN itens_loja on"
                    + " itens_loja.produto_id = produto.id");
            sqlProduto.addColumn("produto.*");
            sqlProduto.setCriteria(criteriaProduto);
            
            stmt = this.getConnection().prepareStatement(sqlProduto
                    .getInstruction());
            stmt.setString(1, nomeProduto);
            stmt.setInt(2, idLoja);
            
            resultSetProduto = stmt.executeQuery();
            
            while (resultSetProduto.next()) {
                ItensLoja itensLoja = new ItensLoja();

                SqlSelect sqlItensLoja = new SqlSelect();

                Criteria criteriaItensLoja = new Criteria();
                criteriaItensLoja.add(new Filter("produto_id", "=", "?"),
                        Expression.AND_OPERATOR);
                criteriaItensLoja.add(new Filter("loja_id", "=", "?"),
                        Expression.AND_OPERATOR);

                sqlItensLoja.setEntity("itens_loja");
                sqlItensLoja.addColumn("*");
                sqlItensLoja.setCriteria(criteriaItensLoja);

                stmt = getConnection().prepareStatement(sqlItensLoja
                        .getInstruction());
                stmt.setInt(1, resultSetProduto.getInt("id"));
                stmt.setInt(2, idLoja);

                resultSetItensLoja = stmt.executeQuery();
                
                if (resultSetItensLoja.next()) {
                    Produto produto = new Produto();
                    
                    produto.setId(resultSetProduto.getInt("id"));
                    produto.setNome(resultSetProduto.getString("nome"));
                    produto.setMarca(resultSetProduto.getString("marca"));
                    produto.setCategoria(resultSetProduto
                            .getString("categoria"));
                    produto.setValorUnidadeMedida(resultSetProduto
                            .getInt("vlr_unidade_medida"));
                    produto.setUnidadeMedida(resultSetProduto
                            .getString("unidade_medida"));
                    produto.setGenero(resultSetProduto.getString("genero"));
                    produto.setDescricao(resultSetProduto
                            .getString("descricao"));

                    itensLoja.setProduto(produto);
                }

                itensLoja.setStatus(resultSetItensLoja.getBoolean("status"));
                itensLoja.setDataCadastro(new Date(resultSetItensLoja
                        .getDate("data_cadastro").getTime()));
                itensLoja.setEstoque(resultSetItensLoja.getInt("estoque"));
                itensLoja.setValorCompra(resultSetItensLoja
                        .getFloat("vlr_compra"));
                itensLoja.setValorVenda(resultSetItensLoja
                        .getFloat("vlr_venda"));

                lista.add(itensLoja);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return lista;
    }
    
    /**
     * Faz alteração nos registros da tabela itens de loja
     * 
     * @param idProduto
     * @param idLoja
     * @return
     * @throws Exception 
     */
    public boolean update(int idProduto, int idLoja) throws Exception {
        PreparedStatement stmt;
        
        try {
            Criteria criteria = new Criteria();
            criteria.add(new Filter("produto_id", "=", "?"),
                    Expression.AND_OPERATOR);
            criteria.add(new Filter("loja_id", "=", "?"),
                    Expression.AND_OPERATOR);
            
            SqlUpdate sql = new SqlUpdate();
            sql.setEntity("itens_loja");
            sql.setCriteria(criteria);
            
            Map<String, String> modelMap = this.getModelMap();

            for (String key : modelMap.keySet()) {
                sql.setRowData(modelMap.get(key), "?");
            }

            stmt = this.getConnection().prepareStatement(sql.getInstruction());

            this.setStmt(stmt);
            stmt.setInt(modelMap.size() + 1, idProduto);
            stmt.setInt(modelMap.size() + 2, idLoja);

            stmt.execute();
            
            return true;
        } catch (Exception e) {
            Transaction.rollback();
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * Deleta registros na tabela itens de loja
     * 
     * @param idProduto
     * @param idLoja
     * @return
     * @throws Exception 
     */
    public boolean delete(int idProduto, int idLoja) throws Exception {
        PreparedStatement stmt;
        
        try {
            Criteria criteria = new Criteria();
            criteria.add(new Filter("produto_id", "=", "?"),
                    Expression.AND_OPERATOR);
            criteria.add(new Filter("loja_id", "=", "?"),
                    Expression.AND_OPERATOR);
            
            SqlDelete sql = new SqlDelete();
            sql.setEntity("itens_loja");
            sql.setCriteria(criteria);

            stmt = this.getConnection().prepareStatement(sql.getInstruction());
            stmt.setInt(1, idProduto);
            stmt.setInt(2, idLoja);

            stmt.execute();
            
            return true;
        } catch (Exception e) {
            Transaction.rollback();
            throw new Exception(e.getMessage());
        }
    }
}
