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
import br.senac.tads.pi3a.ado.Transaction;
import br.senac.tads.pi3a.annotation.Table;
import br.senac.tads.pi3a.model.ItensLoja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author fabiano.bfcarvalho
 */
public class DaoItensLoja extends AbstractDao {

    public DaoItensLoja() {

    }

    public DaoItensLoja(ItensLoja model) {
        super(model);
    }

    /**
     * Faz uma consulta pelo campo informado
     * 
     * @param obj
     * @param field
     * @param criteria
     * @param value
     * @return
     * @throws Exception 
     */
    public List<Model> findAllField(Model obj, String field, String criteria,
            String value) throws Exception {
        ResultSet resultSet;
        ResultSet resultSet2;
        PreparedStatement stmt = null;
        Connection conn = null;
        List<Model> list = new ArrayList<>();

        try {
            if (!obj.getClass().isAnnotationPresent(Table.class)) {
                return null;
            }
            
            Criteria criteria2 = new Criteria();
            criteria2.add(new Filter(field, criteria, "?"));

            SqlSelect sql = new SqlSelect();
            sql.setEntity("produto");
            sql.addColumn("*");
            sql.setCriteria(criteria2);
            
            if (conn == null) {
                Transaction.open();

                conn = Transaction.get();
            }

            stmt = conn.prepareStatement(sql.getInstruction());
            stmt.setObject(1, value);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                ItensLoja itensLoja = new ItensLoja();

                SqlSelect sql2 = new SqlSelect();

                Criteria criteria3 = new Criteria();
                criteria3.add(new Filter("produto_id", "=", "?"),
                        Expression.AND_OPERATOR);
                criteria3.add(new Filter("loja_id", "=", "?"),
                        Expression.AND_OPERATOR); // #Mock

                sql2.setEntity("itens_loja");
                sql2.addColumn("*");
                sql2.setCriteria(criteria3);

                stmt = conn.prepareStatement(sql2.getInstruction());
                stmt.setInt(1, resultSet.getInt("id"));
                stmt.setInt(2, 1);

                resultSet2 = stmt.executeQuery();

                if (resultSet2.next()) {
                    Produto produto = new Produto();
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("nome"));
                    produto.setMarca(resultSet.getString("marca"));
                    produto.setCategoria(resultSet.getString("categoria"));
                    produto.setValorUnidadeMedida(resultSet
                            .getInt("vlr_unidade_medida"));
                    produto.setUnidadeMedida(resultSet
                            .getString("unidade_medida"));
                    produto.setGenero(resultSet.getString("genero"));
                    produto.setDescricao(resultSet.getString("descricao"));

                    itensLoja.setProduto(produto);
                }

                itensLoja.setStatus(resultSet2.getBoolean("status"));
                itensLoja.setDataCadastro(new Date(resultSet2
                        .getDate("data_cadastro").getTime()));
                itensLoja.setEstoque(resultSet2.getInt("estoque"));
                itensLoja.setValorCompra(resultSet2.getFloat("vlr_compra"));
                itensLoja.setValorVenda(resultSet2.getFloat("vlr_venda"));

                list.add(itensLoja);
            }
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }

            if (conn != null && !conn.isClosed()) {
                Transaction.close();
            }
        }

        return list;
    }
}
