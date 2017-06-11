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
import br.senac.tads.pi3a.ado.Expression;
import br.senac.tads.pi3a.ado.Filter;
import br.senac.tads.pi3a.ado.SqlInsert;
import br.senac.tads.pi3a.ado.SqlSelect;
import br.senac.tads.pi3a.ado.SqlUpdate;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Fillipe
 */
public class DaoVenda {

    private Connection conn;

    public DaoVenda(Connection conn) {
        this.conn = conn;
    }
    
    public int insert(Venda venda) {
        int id = -1;
        
        try {
            SqlInsert sqlVenda = new SqlInsert();
            sqlVenda.setEntity("venda");
            sqlVenda.setRowData("valor_venda", "?");
            sqlVenda.setRowData("cliente_id", "?");
            sqlVenda.setRowData("funcionario_id", "?");
            sqlVenda.setRowData("loja_id", "?");
            
            PreparedStatement stmtVenda = this.conn.prepareStatement(
                    sqlVenda.getInstruction(),
                    Statement.RETURN_GENERATED_KEYS);
            
            stmtVenda.setFloat(1, venda.getValorVenda());
            stmtVenda.setInt(2, venda.getCliente().getId());
            stmtVenda.setInt(3, venda.getFuncionario().getId());
            stmtVenda.setInt(4, venda.getLoja().getId());
            
            stmtVenda.execute();
            ResultSet resultSetVenda = stmtVenda.getGeneratedKeys();
            
            if (resultSetVenda.next()) {
                id = resultSetVenda.getInt(1); // id
                
                SqlInsert sqlItensVenda = new SqlInsert();
                sqlItensVenda.setEntity("itens_venda");
                sqlItensVenda.setRowData("venda_id", "?");
                sqlItensVenda.setRowData("produto_id", "?");
                sqlItensVenda.setRowData("qtde_item", "?");
                sqlItensVenda.setRowData("valor_unitario", "?");
                
                PreparedStatement stmtItensVenda = this.conn.prepareStatement(
                        sqlItensVenda.getInstruction());
                
                for (int i = 0; i < venda.getListaItensVenda().size(); i++) {
                    stmtItensVenda.setInt(1, id);
                    stmtItensVenda.setInt(2, venda.getListaItensVenda().get(i)
                            .getItens().getProduto().getId());
                    stmtItensVenda.setInt(3, venda.getListaItensVenda().get(i)
                            .getQuantidade());
                    stmtItensVenda.setFloat(4, venda.getListaItensVenda().get(i)
                            .getValorUnitario());
                    
                    stmtItensVenda.execute();
                }
                
                // Dá baixa dos produtos no estoque
                if (this.baixarEstoque(venda.getListaItensVenda())) {
                    return id;
                } else {
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return -1;
        }
        
        return id;
    }
    
    /**
     * Esse update serve para cancelar uma venda alterando apenas o status
     * 
     * @param venda
     * @return
     * @throws Exception 
     */
    public boolean update(Venda venda) throws Exception {
        try {
            Criteria criteria = new Criteria();
            criteria.add(new Filter("id", "=", "?"));
            
            SqlUpdate sql = new SqlUpdate();
            sql.setEntity("venda");
            sql.setRowData("status", "?");
            sql.setCriteria(criteria);

            PreparedStatement stmt = this.conn.prepareStatement(
                    sql.getInstruction());

            stmt.setBoolean(1, false);
            stmt.setInt(2, venda.getId());

            stmt.execute();
            
            // Retorna os produtos ao estoque
            if (this.retornarEstoque(venda.getListaItensVenda())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return false;
    }

    /**
     * Faz uma consulta na tabela de venda conforme os critérios informados
     * 
     * @param field
     * @param criteria
     * @param value
     * @return 
     */
    public List<Venda> findAll(String field, String criteria, String value) {
        List<Venda> listaVenda = new ArrayList<>();
        
        try {
            PreparedStatement stmtVenda;
            ResultSet resultSetVenda;
            
            Criteria c = new Criteria();
            c.add(new Filter(field, criteria, "?"));
            
            SqlSelect sqlVenda = new SqlSelect();
            sqlVenda.setEntity("venda");
            sqlVenda.addColumn("*");
            sqlVenda.setCriteria(c);

            stmtVenda = this.conn.prepareStatement(sqlVenda.getInstruction());
            
            stmtVenda.setObject(1, value);
            
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
    
    /**
     * Retorna as lojas que têm vendas
     * 
     * @param idProduto
     * @return 
     */
    public List<Object[]> findAllLoja(int idProduto) {
        List<Object[]> lista = new ArrayList<>();
        
        try {
            Criteria criteria = new Criteria();
            criteria.add(new Filter("itens_venda.produto_id", "=", "?"));
            
            SqlSelect sql = new SqlSelect();
            sql.setEntity("loja inner join venda on venda.loja_id = loja.id inner join itens_venda on itens_venda.venda_id = venda.id");
            sql.addColumn("loja.id");
            sql.addColumn("loja.razao_social");
            sql.setCriteria(criteria);
            
            PreparedStatement stmt = this.conn.prepareStatement(sql.getInstruction());
            stmt.setInt(1, idProduto);
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                Object[] object = new Object[2];
                
                object[0] = resultSet.getInt("id");
                object[1] = resultSet.getString("razao_social");
                
                lista.add(object);
            }
            
            return lista;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
    
    /**
     * Retorna os dados de uma venda
     * 
     * @param idVenda
     * @return 
     */
    public List<Map<String, Object[]>> findOne(int idVenda) {
        List<Map<String, Object[]>> listaVendas = new ArrayList<>();
        
        try {
            List<Venda> lista = findAll("id", "=", String.valueOf(idVenda));

            if (!lista.isEmpty()) {
                Venda venda = lista.get(0);
                listaVendas.add(this.montarVenda(venda));
                
                return listaVendas;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return null;
    }
    
    /**
     * Retorna os dados de uma venda de uma determinada loja
     * 
     * @param idVenda
     * @param idLoja
     * @return 
     */
    public List<Map<String, Object[]>> findOne(int idVenda, int idLoja) {
        List<Map<String, Object[]>> listaVendas = new ArrayList<>();
        
        try {
            List<Venda> lista = findAll(
                    new String[]{"id", "loja_id"},
                    new String[]{"=", "="},
                    new String[]{String.valueOf(idVenda),
                        String.valueOf(idLoja)},
                    new String[]{"and", "and"});

            if (!lista.isEmpty()) {
                Venda venda = lista.get(0);
                listaVendas.add(this.montarVenda(venda));
                
                return listaVendas;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return null;
    }
    
    /**
     * Retorna os dados das vendas dos clientes
     * 
     * @param clientes
     * @return 
     */
    public List<Map<String, Object[]>> findAll(List<Model> clientes) {
        List<Map<String, Object[]>> listaVendas = new ArrayList<>();
        
        try {
            String[] fields = new String[clientes.size()];
            String[] criterias = new String[clientes.size()];
            String[] values = new String[clientes.size()];
            String[] operetors = new String[clientes.size()];
            for (int i = 0; i < fields.length; i++) {
                fields[i] = "cliente_id";
                criterias[i] = "=";
                values[i] = String.valueOf(clientes.get(i).getId());
                operetors[i] = "or";
            }
            
            List<Venda> lista = findAll(fields, criterias, values, operetors);

            for (int i = 0; i < lista.size(); i++) {
                Venda venda = lista.get(i);
                listaVendas.add(this.montarVenda(venda));
            }
            
            return listaVendas;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return null;
    }
    
    /**
     * Retorna os dados das vendas dos clientes de uma determinada loja
     * 
     * @param clientes
     * @param idLoja
     * @return 
     */
    public List<Map<String, Object[]>> findAll(List<Model> clientes,
            int idLoja) {
        List<Map<String, Object[]>> listaVendas = new ArrayList<>();
        
        try {
            String[] fields = new String[clientes.size() + 1];
            String[] criterias = new String[clientes.size() + 1];
            String[] values = new String[clientes.size() + 1];
            String[] operetors = new String[clientes.size() + 1];
            int i;
            for (i = 0; i < fields.length - 1; i++) {
                fields[i] = "cliente_id";
                criterias[i] = "=";
                values[i] = String.valueOf(clientes.get(i).getId());
                operetors[i] = "or";
            }
            fields[i] = "loja_id";
            criterias[i] = "=";
            values[i] = String.valueOf(idLoja);
            operetors[i] = "and";
            
            List<Venda> lista = findAll(fields, criterias, values, operetors);

            for (i = 0; i < lista.size(); i++) {
                Venda venda = lista.get(i);
                listaVendas.add(this.montarVenda(venda));
            }
            
            return listaVendas;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return null;
    }
    
    /**
     * Monta uma venda conforme o necessário para exibir na view
     * 
     * @param venda
     * @return 
     */
    private Map<String, Object[]> montarVenda(Venda venda) {
        Map<String, Object[]> dados = new LinkedHashMap<>();

        dados.put("venda", new Object[]{venda.getId()});
        dados.put("status", new Object[]{venda.getStatus()});
        dados.put("dataCadastro", new Object[]{venda.getData()});
        dados.put("cpf", new Object[]{venda.getCliente().getCpf()});
        dados.put("nome", new Object[]{venda.getCliente().getNome()});
        dados.put("idCliente", new Object[]{venda.getCliente().getId()});
        dados.put("total", new Object[]{venda.getValorVenda()});

        Object[] codigos = new Object[venda.getListaItensVenda().size()];
        Object[] produtos = new Object[codigos.length];
        Object[] marcas = new Object[codigos.length];
        Object[] quantidades = new Object[codigos.length];
        Object[] precosUnidades = new Object[codigos.length];
        Object[] precosTotais = new Object[codigos.length];
        for (int i = 0; i < codigos.length; i++) {
            codigos[i] = venda.getListaItensVenda().get(i).getItens()
                    .getProduto().getId();
            produtos[i] = venda.getListaItensVenda().get(i).getItens()
                    .getProduto().getNome();
            marcas[i] = venda.getListaItensVenda().get(i).getItens()
                    .getProduto().getMarca();
            quantidades[i] = venda.getListaItensVenda().get(i).getQuantidade();
            precosUnidades[i] = venda.getListaItensVenda().get(i)
                    .getValorUnitario();
            precosTotais[i] = (int) quantidades[i] * (float) precosUnidades[i];
        }

        dados.put("codigo", codigos);
        dados.put("produto", produtos);
        dados.put("marca", marcas);
        dados.put("quantidade", quantidades);
        dados.put("precoUnidade", precosUnidades);
        dados.put("precoTotal", precosTotais);
        dados.put("cont", new Object[]{codigos.length});
        
        return dados;
    }
    
    /**
     * Método para retirar os produtos do estoque quando uma venda for realizada
     * 
     * @param listaItensVenda
     * @return 
     */
    private boolean baixarEstoque(List<ItensVenda> listaItensVenda) {
        try {
            for (ItensVenda itensVenda : listaItensVenda) {
                Criteria criteriaSelect = new Criteria();
                criteriaSelect.add(new Filter("produto_id", "=", "?"),
                        Expression.AND_OPERATOR);
                criteriaSelect.add(new Filter("loja_id", "=", "?"),
                        Expression.AND_OPERATOR);

                SqlSelect sqlSelect = new SqlSelect();
                sqlSelect.setEntity("itens_loja");
                sqlSelect.addColumn("estoque");
                sqlSelect.setCriteria(criteriaSelect);
                
                PreparedStatement stmtSelect = this.conn.prepareStatement(sqlSelect
                        .getInstruction());
                
                int idProduto = itensVenda.getItens().getProduto().getId();
                int idLoja = itensVenda.getVenda().getLoja().getId();
                
                stmtSelect.setInt(1, idProduto);
                stmtSelect.setInt(2, idLoja);
                
                ResultSet resultSetSelect = stmtSelect.executeQuery();
                
                if (resultSetSelect.next()) {
                    Criteria criteriaUpdate = new Criteria();
                    criteriaUpdate.add(new Filter("produto_id", "=", "?"),
                            Expression.AND_OPERATOR);
                    criteriaUpdate.add(new Filter("loja_id", "=", "?"),
                            Expression.AND_OPERATOR);
                    
                    SqlUpdate sqlUpdate = new SqlUpdate();
                    sqlUpdate.setEntity("itens_loja");
                    sqlUpdate.setRowData("estoque", "?");
                    sqlUpdate.setCriteria(criteriaUpdate);
                    
                    PreparedStatement stmtUpdate = this.conn.prepareStatement(
                            sqlUpdate.getInstruction());
                    
                    stmtUpdate.setInt(1, resultSetSelect.getInt("estoque")
                            - itensVenda.getQuantidade());
                    stmtUpdate.setInt(2, idProduto);
                    stmtUpdate.setInt(3, idLoja);
                    
                    stmtUpdate.execute();
                }
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }
    
    /**
     * Método para retornar os produtos ao estoque quando uma venda for
     * cancelada
     * 
     * @param listaItensVenda
     * @return 
     */
    private boolean retornarEstoque(List<ItensVenda> listaItensVenda) {
        try {
            for (ItensVenda itensVenda : listaItensVenda) {
                Criteria criteriaSelect = new Criteria();
                criteriaSelect.add(new Filter("produto_id", "=", "?"),
                        Expression.AND_OPERATOR);
                criteriaSelect.add(new Filter("loja_id", "=", "?"),
                        Expression.AND_OPERATOR);

                SqlSelect sqlSelect = new SqlSelect();
                sqlSelect.setEntity("itens_loja");
                sqlSelect.addColumn("estoque");
                sqlSelect.setCriteria(criteriaSelect);
                
                PreparedStatement stmtSelect = this.conn.prepareStatement(sqlSelect
                        .getInstruction());
                
                int idProduto = itensVenda.getItens().getProduto().getId();
                int idLoja = itensVenda.getVenda().getLoja().getId();
                
                stmtSelect.setInt(1, idProduto);
                stmtSelect.setInt(2, idLoja);
                
                ResultSet resultSetSelect = stmtSelect.executeQuery();
                
                if (resultSetSelect.next()) {
                    Criteria criteriaUpdate = new Criteria();
                    criteriaUpdate.add(new Filter("produto_id", "=", "?"),
                            Expression.AND_OPERATOR);
                    criteriaUpdate.add(new Filter("loja_id", "=", "?"),
                            Expression.AND_OPERATOR);
                    
                    SqlUpdate sqlUpdate = new SqlUpdate();
                    sqlUpdate.setEntity("itens_loja");
                    sqlUpdate.setRowData("estoque", "?");
                    sqlUpdate.setCriteria(criteriaUpdate);
                    
                    PreparedStatement stmtUpdate = this.conn.prepareStatement(
                            sqlUpdate.getInstruction());
                    
                    stmtUpdate.setInt(1, resultSetSelect.getInt("estoque")
                            + itensVenda.getQuantidade());
                    stmtUpdate.setInt(2, idProduto);
                    stmtUpdate.setInt(3, idLoja);
                    
                    stmtUpdate.execute();
                }
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }
}
