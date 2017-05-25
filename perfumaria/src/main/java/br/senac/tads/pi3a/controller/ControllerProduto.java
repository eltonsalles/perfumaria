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
package br.senac.tads.pi3a.controller;

import br.senac.tads.pi3a.dao.DaoItensLoja;
import br.senac.tads.pi3a.dao.DaoProduto;
import br.senac.tads.pi3a.inputFilter.InputFilterProduto;
import br.senac.tads.pi3a.model.ItensLoja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Produto;
import java.sql.Connection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Elton
 */
public class ControllerProduto implements Logica {

    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws Exception {
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário de produto
                InputFilterProduto inputFilterProduto
                        = new InputFilterProduto(request.getParameterMap());
                
                // Cria um objeto itens loja com os dados do formulário,
                // mas sem validação
                ItensLoja itensLoja = (ItensLoja) inputFilterProduto.getData();
                
                if (inputFilterProduto.isValid()) {
                    // Atualiza o objeto itens loja com os dados validados
                    itensLoja = (ItensLoja) inputFilterProduto.createModel();
                    
                    // Pega a conexão com o banco de dados
                    Connection conn = (Connection) request
                            .getAttribute("connection");
                    
                    // Chama a DAO de produto passando a conexão e o objeto
                    // a ser inserido
                    DaoProduto daoProduto = new DaoProduto(conn,
                            itensLoja.getProduto());
                    
                    // #MOCK - id da loja
                    // Garante que o produto ainda não existe na loja
                    // que está se cadastrando
                    if (daoProduto.produtoExisteLoja(itensLoja.getProduto()
                            .getNome().toUpperCase(), 1) == -1) {
                        
                        List listaPorNome = daoProduto.findAll(itensLoja
                                .getProduto(), "UPPER(nome)", "=", itensLoja
                                        .getProduto().getNome().toUpperCase());
                        
                        int idProduto;
                        if (listaPorNome.isEmpty()) {
                            // Faz a inserção na tabela produto
                            idProduto = daoProduto.insert();
                        } else {
                            Produto produto = (Produto) listaPorNome.get(0);
                            idProduto = produto.getId();
                        }
                        
                        itensLoja.getProduto().setId(idProduto);

                        // Chama a DAO de itens de loja passando a conexão
                        // e o objeto a ser inserido
                        DaoItensLoja daoItensLoja = new DaoItensLoja(conn,
                                itensLoja);
                        
                        // Faz a inserção na tabela itens de loja
                        if (daoItensLoja.insert() != -1) {
                            session.setAttribute("alert", "alert-success");
                            session.setAttribute("alertMessage",
                                    "Cadastro realizado com sucesso.");
                            return "novo";
                        } else {
                            daoProduto.delete(itensLoja.getProduto().getId());
                        }
                    } else {
                        session.setAttribute("itensLoja", itensLoja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage", "Este nome de "
                                + "produto já está cadastrado para essa loja.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterProduto.getErrorValidation());
                    session.setAttribute("itensLoja", itensLoja);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }
            }

            return "WEB-INF/jsp/cadastrar-produto.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possivel realizar o cadastro.");
            return "novo";
        }
    }

    @Override
    public String editar(HttpServletRequest request,
            HttpServletResponse response, HttpSession session)
            throws Exception {
        try {
            // Pega a conexão
            Connection conn = (Connection) request.getAttribute("connection");
                        
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário de produtos
                InputFilterProduto inputFilterProduto
                        = new InputFilterProduto(request.getParameterMap());
                
                // Cria um objeto itensLoja com os dados do formulário,
                // mas sem validação
                ItensLoja itensLoja = (ItensLoja) inputFilterProduto.getData();
                
                if (inputFilterProduto.isValid()) {
                    // Atualiza os dados com as informações validadas
                    itensLoja = (ItensLoja) inputFilterProduto.createModel();
                    
                    // Chama a DAO de produto passando a conexão e o objeto
                    // a ser alterado
                    DaoProduto daoProduto = new DaoProduto(conn,
                            itensLoja.getProduto());
                    
                    // #MOCK - id da loja
                    // Garante que as alterações não dupliquem o nome do produto
                    if (daoProduto.produtoExisteLoja(
                            itensLoja.getProduto().getNome().toUpperCase(), 1)
                            == itensLoja.getProduto().getId()) {
                        // Chama a DAO de itens de loja passando a conexão e o
                        // objeto a ser alterado
                        DaoItensLoja daoItensLoja
                                = new DaoItensLoja(conn, itensLoja);
                        
                        // Faz as alterações na tabela produto e itens de loja
                        if (daoProduto.update()
                                && daoItensLoja.update(
                                        itensLoja.getProduto().getId(),
                                        itensLoja.getLoja().getId())) {
                            session.setAttribute("alert", "alert-success");
                            session.setAttribute("alertMessage", "Produto"
                                    + " alterado com sucesso.");
                            session.setAttribute("id", itensLoja.getProduto()
                                    .getId());
                            return "editar";
                        }
                    } else {
                        // Manda para jsp os campos inválidos e uma mensagem
                        session.setAttribute("itensLoja", itensLoja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Não foi encontrado nenhum produto com esse"
                                        + " nome para essa loja.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterProduto.getErrorValidation());
                    session.setAttribute("itensLoja", itensLoja);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }
            } else {
                // Verifica se existe o parâmetro id
                if (request.getParameter("id") != null) {
                    String id = request.getParameter("id");
                    boolean digito = true;

                    // Garante que o id seja apenas números
                    for (int i = 0; i < id.length(); i++) {
                        if (!Character.isDigit(id.charAt(i))) {
                            digito = false;
                            break;
                        }
                    }

                    if (digito) {
                        Model itensLoja = new ItensLoja();
                        
                        // Chama a DAO passando a conexão
                        DaoItensLoja dao = new DaoItensLoja(conn);

                        // Faz uma consulta usando como critério o id do produto
                        // e id da loja para exibir os dados do produto para
                        // determinada loja
                        List lista = dao.findAll(itensLoja,
                                new String[]{"produto_id", "loja_id"},
                                new String[]{"=", "="},
                                new String[]{id, "1"}, // #MOCK - id da loja
                                new String[]{"and", "and"});

                        if (lista.size() == 1) {
                            session.setAttribute("itensLoja", lista.get(0));
                        }
                    }
                }
            }

            return "/WEB-INF/jsp/cadastrar-produto.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a alteração.");
            session.setAttribute("id", 0);
            return "editar";
        }
    }

    @Override
    public String excluir(HttpServletRequest request,
            HttpServletResponse response, HttpSession session)
            throws Exception {
        try {
            // Verifica se existe o parâmetro id
            if (request.getParameter("id") != null) {
                String id = request.getParameter("id");
                boolean digito = true;

                // Garante que o id seja apenas números
                for (int i = 0; i < id.length(); i++) {
                    if (!Character.isDigit(id.charAt(i))) {
                        digito = false;
                        break;
                    }
                }

                if (digito) {
                    // Pega a conexão
                    Connection conn = (Connection) request
                            .getAttribute("connection");
                    
                    ItensLoja itensLoja = new ItensLoja();
                    
                    DaoItensLoja daoItensLoja
                            = new DaoItensLoja(conn, itensLoja);

                    // #MOCK - id da loja
                    if (daoItensLoja.delete(Integer.valueOf(id), 1)) {
                        session.setAttribute("alert", "alert-warning");
                        session.setAttribute("alertMessage",
                                "Produto excluído com sucesso.");
                        return "excluir";
                    }
                }
            }

            return "/WEB-INF/jsp/consultar-produto.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a exclusão.");
            return "excluir";
        }
    }

    @Override
    public String pesquisar(HttpServletRequest request,
            HttpServletResponse response, HttpSession session)
            throws Exception {
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                ItensLoja itensLoja = new ItensLoja();
                DaoItensLoja dao = new DaoItensLoja(
                        (Connection) request.getAttribute("connection"));
                List<Model> lista;

                // Se não houver valor para pesquisar então retorna tudo
                if (request.getParameter("pesquisar") != null
                        && !request.getParameter("pesquisar").isEmpty()) {
                    String pesquisar = request.getParameter("pesquisar");

                    // Verifica por onde a consulta é feita por id ou nome
                    boolean digito = true;
                    for (int i = 0; i < pesquisar.length(); i++) {
                        if (!Character.isDigit(pesquisar.charAt(i))) {
                            digito = false;
                            break;
                        }
                    }
                    if (digito) {
                        lista = dao.findAll(itensLoja,
                                new String[]{"produto_id", "loja_id"},
                                new String[]{"=", "="},
                                new String[]{pesquisar, "1"},
                                new String[]{"and", "and"});
                    } else {
                        lista = dao.findAllPorNome(
                                "%" + pesquisar.toUpperCase() + "%", 1);
                    }

                } else {
                    // #MOCK - id da loja
                    // Traz todos os produtos da loja informada
                    lista = dao.findAll(itensLoja, "loja_id", "=", "1");
                }

                if (lista != null && !lista.isEmpty()) {
                    session.setAttribute("listaItensLoja", lista);
                    return "pesquisar";
                } else {
                    session.setAttribute("alert", "alert-warning");
                    session.setAttribute("alertMessage",
                            "A consulta não retornou nenhum resultado.");
                }
            }

            return "/WEB-INF/jsp/consultar-produto.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a consulta.");
            return "pesquisar";
        }
    }

    public String movimentar(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "/WEB-INF/jsp/manutencao-produto.jsp";
    }

    public String relatorio(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "/WEB-INF/jsp/relatorio-estoque.jsp";
    }

    public String historico(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "/WEB-INF/jsp/historico-produto.jsp";
    }
    
    /**
     * Método que gera um json com as informações comuns dos produtos
     * 
     * @param request
     * @param response
     * @param session
     * @return 
     */
    public String produtos(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            if (request.getParameter("nome") != null 
                    && !request.getParameter("nome").isEmpty()) {
                String nome = request.getParameter("nome");
                
                Produto produto = new Produto();
                DaoProduto dao = new DaoProduto((Connection) request
                        .getAttribute("connection"));

                List lista = dao.findAll(produto,
                        "UPPER(nome)", "LIKE", "%" + nome.toUpperCase() + "%");
                
                if (!lista.isEmpty()) {
                    request.setAttribute("produtos", lista);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return "/WEB-INF/api/produtos.jsp";
    }
}
