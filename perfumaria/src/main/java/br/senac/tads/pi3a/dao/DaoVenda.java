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
import br.senac.tads.pi3a.model.Venda;
import com.sun.xml.internal.txw2.output.ResultFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public List<Venda> findAll() {
        try {
            PreparedStatement stmtVenda;
            ResultSet resultSetVenda;

            SqlSelect sqlVenda = new SqlSelect();
            sqlVenda.setEntity("venda");
            sqlVenda.addColumn("*");

            stmtVenda = this.conn.prepareStatement(sqlVenda.getInstruction());
            resultSetVenda = stmtVenda.executeQuery();
            
            while (resultSetVenda.next()){
                Venda venda = new Venda();
                venda.setId(resultSetVenda.getInt("id"));
                venda.setData(resultSetVenda.getDate("data_venda"));
                venda.setStatus(resultSetVenda.getBoolean("status"));
                venda.setValorVenda(resultSetVenda.getFloat("valor_venda"));
                
                Criteria criteriaCliente = new Criteria();
                criteriaCliente.add(new Filter("id", "=", "?"));
                
                SqlSelect sqlCliente = new SqlSelect();
                sqlCliente.setEntity("cliente");
                sqlCliente.addColumn("*");
                sqlCliente.setCriteria(criteriaCliente);
                
                PreparedStatement stmtCliente;
                ResultSet resultSetCliente;
                
                stmtCliente = this.conn.prepareStatement(sqlCliente.getInstruction());
                stmtCliente.setInt(1, resultSetVenda.getInt("cliente_id"));
                
                resultSetCliente = stmtCliente.executeQuery();
                
                if(resultSetCliente.next()){
                    Cliente cliente = new Cliente();
                    cliente.setStatus(resultSetCliente.getBoolean("status"));
                    cliente.setCpf(resultSetCliente.getString("cpf"));
                    cliente.setNome(resultSetCliente.getString("nome"));
                    cliente.setDataNascimento(resultSetCliente.getDate("data_nasc"));
                    cliente.setSexo(resultSetCliente.getString("sexo").charAt(0));
                    cliente.setCelular(resultSetCliente.getString("celular"));
                    cliente.setTelefone(resultSetCliente.getString("telefone"));
                    cliente.setEmail(resultSetCliente.getString("email"));
                    cliente.setLogradouro(resultSetCliente.getString("logradouro"));
                    cliente.setNumero(resultSetCliente.getString("numero"));
                    cliente.setCep(resultSetCliente.getString("cep"));
                    cliente.setCidade(resultSetCliente.getString("cidade"));
                    cliente.setUf(resultSetCliente.getString("uf"));
                    
                    venda.setCliente(cliente);
                }
                
                Criteria criteriaFuncionario = new Criteria();
                criteriaFuncionario.add(new Filter("id", "=", "?"));
                
                SqlSelect sqlSelectFuncionario = new SqlSelect();
                sqlSelectFuncionario.setEntity("funcionario");
                sqlSelectFuncionario.addColumn("*");
                sqlSelectFuncionario.setCriteria(criteriaFuncionario);
                
                PreparedStatement stmtFuncionario;
                ResultSet resultSetFuncionario;
                
                stmtFuncionario = this.conn.prepareStatement(sqlSelectFuncionario.getInstruction());
                stmtFuncionario.setInt(1, resultSetVenda.getInt("funcionario__id"));
                
                resultSetFuncionario = stmtFuncionario.executeQuery();
                
                if(resultSetFuncionario.next()){
                    Funcionario funcionario = new Funcionario();
                    funcionario.setStatus(resultSetFuncionario.getBoolean("status"));
                    funcionario.setDataAdmissao(resultSetFuncionario.getDate("data-admissao"));
                    funcionario.setCargo(resultSetFuncionario.getString("cargo"));
                    funcionario.setNome(resultSetFuncionario.getString("nome"));
                    funcionario.setCpf(resultSetFuncionario.getString("cpf"));
                    funcionario.setDataNascimento(resultSetFuncionario.getDate("data_nasc"));
                    funcionario.setSexo(resultSetFuncionario.getString("sexo").charAt(0));
                    funcionario.setEstadoCivil(resultSetFuncionario.getString("est_civil"));
                    funcionario.setCelular(resultSetFuncionario.getString("celular"));
                    funcionario.setTelefone(resultSetFuncionario.getString("telefone"));
                    funcionario.setEmail(resultSetFuncionario.getString("email"));
                    funcionario.setLogradouro(resultSetFuncionario.getString("logradouro"));
                    funcionario.setNumero(resultSetFuncionario.getString("numero"));
                    funcionario.setComplemento(resultSetFuncionario.getString("complemento"));
                    funcionario.setBairro(resultSetFuncionario.getString("bairro"));
                    funcionario.setCep(resultSetFuncionario.getString("cep"));
                    funcionario.setCidade(resultSetFuncionario.getString("cidade"));
                    funcionario.setUf(resultSetFuncionario.getString("uf"));
                    
                    venda.setFuncionario(funcionario);
                    
                }
            }

        } catch (Exception e) {
        }
        return null;
    }

}
