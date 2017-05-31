/*
 * The MIT License
 *
 * Copyright 2017 Fillipe.
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
import br.senac.tads.pi3a.ado.Filter;
import br.senac.tads.pi3a.ado.SqlSelect;
import br.senac.tads.pi3a.model.Cliente;
import br.senac.tads.pi3a.model.Funcionario;
import br.senac.tads.pi3a.model.ItensLoja;
import br.senac.tads.pi3a.model.ItensVenda;
import br.senac.tads.pi3a.model.Loja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fillipe
 */
public class DaoVenda {

    private Connection conn;

    public DaoVenda(Connection conn) {
        this.conn = conn;
    }

    /**
     * Faz uma consulta na tabela de venda conforme os critérios informados
     * 
     * @param field
     * @param criteria
     * @param value
     * @param operator
     * @return 
     */
    public List<Venda> findAll(String[] field, String[] criteria,
            String[] value, String[] operator) {
        List<Venda> listaVenda = new ArrayList<>();
        
        try {
            PreparedStatement stmtVenda;
            ResultSet resultSetVenda;
            
            Criteria c = new Criteria();
            for (int i = 0; i < criteria.length; i++) {
                // Operator AND ou OR
                String o;
                if (operator[i].equalsIgnoreCase("or")) {
                    o = Criteria.OR_OPERATOR;
                } else {
                    o = Criteria.AND_OPERATOR;
                }
                
                c.add(new Filter(field[i], criteria[i], "?"), o);
            }
            
            SqlSelect sqlVenda = new SqlSelect();
            sqlVenda.setEntity("venda");
            sqlVenda.addColumn("*");
            sqlVenda.setCriteria(c);

            stmtVenda = this.conn.prepareStatement(sqlVenda.getInstruction());
            
            for (int i = 0; i < value.length; i++) {
                stmtVenda.setObject(i + 1, value[i]);
            }
            
            resultSetVenda = stmtVenda.executeQuery();

            while (resultSetVenda.next()) {
                // Objeto venda
                Venda venda = new Venda();
                venda.setId(resultSetVenda.getInt("id"));
                venda.setData(resultSetVenda.getDate("data_venda"));
                venda.setStatus(resultSetVenda.getBoolean("status"));
                venda.setValorVenda(resultSetVenda.getFloat("valor_venda"));
                
                DaoCliente daoCliente = new DaoCliente(conn);
                venda.setCliente((Cliente) daoCliente.findOne(new Cliente(),
                        resultSetVenda.getInt("cliente_id")));
                
                DaoFuncionario daoFuncionario = new DaoFuncionario(conn);
                venda.setFuncionario((Funcionario) daoFuncionario.findOne(
                        new Funcionario(), resultSetVenda
                                .getInt("funcionario_id")));

                DaoLoja daoLoja = new DaoLoja(conn);
                venda.setLoja((Loja) daoLoja.findOne(new Loja(),
                        resultSetVenda.getInt("loja_id")));                
                
                // Onjeto itens da venda
                Criteria criteriaItensVenda = new Criteria();
                criteriaItensVenda.add(new Filter("venda_id", "=", "?"));
                
                SqlSelect sqlSelectItensVenda = new SqlSelect();
                sqlSelectItensVenda.setEntity("itens_venda");
                sqlSelectItensVenda.addColumn("*");
                sqlSelectItensVenda.setCriteria(criteriaItensVenda);
                
                PreparedStatement stmtItensVenda;
                ResultSet resultSetItensVenda;
                
                stmtItensVenda = this.conn.prepareStatement(sqlSelectItensVenda
                        .getInstruction());
                stmtItensVenda.setInt(1, resultSetVenda.getInt("id"));
                
                resultSetItensVenda = stmtItensVenda.executeQuery();
                
                while (resultSetItensVenda.next()) {
                    ItensVenda itensVenda = new ItensVenda();
                    itensVenda.setVenda(venda);
                    itensVenda.setQuantidade(resultSetItensVenda
                            .getInt("qtde_item"));
                    itensVenda.setValorUnitario(resultSetItensVenda
                            .getFloat("valor_unitario"));
                    
                    DaoItensLoja daoItensLoja = new DaoItensLoja(conn);
                    List<Model> item = daoItensLoja.findAll(new ItensLoja(),
                            new String[]{"produto_id", "loja_id"},
                            new String[]{"=", "="},
                            new String[]{String.valueOf(resultSetItensVenda
                                    .getInt("produto_id")),
                                String.valueOf(venda.getLoja().getId())},
                            new String[]{"and", "and"});
                    
                    // A consulta só retorna um produto
                    if (item.size() == 1) {
                        itensVenda.setItens((ItensLoja) item.get(0));
                    }
                    
                    venda.addListaItensVenda(itensVenda);
                }
                
                listaVenda.add(venda);
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
        
        return listaVenda;
    }
}
