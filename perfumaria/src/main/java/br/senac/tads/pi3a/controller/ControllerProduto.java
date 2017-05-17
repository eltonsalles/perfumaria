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
                // Classe de validação do formulário cliente
                InputFilterProduto inputFilterProduto
                        = new InputFilterProduto(request.getParameterMap());

                // Cria um objeto itens loja com os dados do formulário,
                // mas sem validação
                ItensLoja itensLoja = (ItensLoja) inputFilterProduto.getData();

                // Faz a validação do formulário produto
                if (inputFilterProduto.isValid()) {
                    // Atualiza o objeto itens loja com os dados validados
                    itensLoja = (ItensLoja) inputFilterProduto.createModel();

                    DaoProduto daoProduto = new DaoProduto(itensLoja
                            .getProduto());

                    // Garante que o nome não esteja cadastrado na base de dados
                    if (daoProduto.findAll(itensLoja.getProduto(), "nome", "=",
                            itensLoja.getProduto().getNome()).isEmpty()) {
                        // Insere a parte dos dados gerais do produto
                        int idProduto = daoProduto.insert();

                        if (idProduto != -1) {
                            // Id gerado ao inserir os dados gerais de produto
                            itensLoja.getProduto().setId(idProduto);

                            // Todo item deve ser cadastrado com status true
                            itensLoja.setStatus(true);

                            DaoItensLoja daoItensLoja
                                    = new DaoItensLoja(itensLoja);

                            if (daoItensLoja.insert() != -1) {
                                session.setAttribute("alert", "alert-success");
                                session.setAttribute("alertMessage",
                                        "Cadastro realizado com sucesso");
                                return "novo";
                            }
                        }
                    } else {
                        session.setAttribute("itensLoja", itensLoja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Este nome de produto já está cadastrado.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterProduto.getErrorValidation());
                    session.setAttribute("itensLoja", itensLoja);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique os campos em vermelho.");
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
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário itensLoja
                InputFilterProduto inputFilterProduto
                        = new InputFilterProduto(request.getParameterMap());

                // Cria um objeto itensLoja com os dados do formulário,
                // mas sem validação            
                ItensLoja itensLoja = (ItensLoja) inputFilterProduto.getData();

                // faz validação do formulario itensLoja
                if (inputFilterProduto.isValid()) {
                    // Atualiza os dados com as informações validadas
                    itensLoja = (ItensLoja) inputFilterProduto.createModel();

                    DaoProduto dao = new DaoProduto(itensLoja.getProduto());

                    //garante que o nome não se repita na base
                    List<Model> lista = dao.findAll(itensLoja.getProduto(),
                            "nome", "=", itensLoja.getProduto().getNome());

                    if (lista.size() == 1) {
                        if (lista.get(0).getId() == itensLoja.getProduto()
                                .getId()) {

                            // Atualiza os detalhes do produto
                            if (dao.update()) {
                                DaoItensLoja daoItensLoja
                                        = new DaoItensLoja(itensLoja);

                                // Atualiza as informações do produto
                                // relacionadas a loja
                                if (daoItensLoja.update("produto_id")) {
                                    session.setAttribute("alert",
                                            "alert-success");
                                    session.setAttribute("alertMessage",
                                            "Produto alterado com sucesso.");
                                    session.setAttribute("id", itensLoja
                                            .getProduto().getId());
                                    return "editar";
                                }
                            }
                        } else {
                            // Manda para jsp os campos inválidos e uma mensagem
                            session.setAttribute("itensLoja", itensLoja);
                            session.setAttribute("alert", "alert-danger");
                            session.setAttribute("alertMessage",
                                    "Este nome já está cadastrado.");

                        }
                    } else {
                        // Manda para jsp os campos inválidos e uma mensagem
                        session.setAttribute("itensLoja", itensLoja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Não foi encontrado nenhum produto com esse"
                                        + " nome informado.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterProduto.getErrorValidation());
                    session.setAttribute("itensLoja", itensLoja);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique os campo em vermelho.");
                }
            }
            if (request.getParameter("id") != null) {
                String id = request.getParameter("id");
                boolean digito = true;

                for (int i = 0; i < id.length(); i++) {
                    if (!Character.isDigit(id.charAt(i))) {
                        digito = false;
                        break;
                    }
                }

                if (digito) {
                    Model itensLoja = new ItensLoja();
                    DaoItensLoja dao = new DaoItensLoja();

                    List lista = dao.findAll(itensLoja,
                            new String[]{"produto_id", "loja_id"},
                            new String[]{"=", "="},
                            new String[]{id, "1"}, // #MOCK
                            new String[]{"and", "and"});

                    if (lista.size() == 1) {
                        session.setAttribute("itensLoja", lista.get(0));
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
            if (request.getParameter("id") != null) {
                String id = request.getParameter("id");
                boolean digito = true;

                for (int i = 0; i < id.length(); i++) {
                    if (!Character.isDigit(id.charAt(i))) {
                        digito = false;
                        break;
                    }
                }

                if (digito) {
                    Produto produto = new Produto();
                    DaoProduto dao = new DaoProduto(produto);

                    if (dao.delete(Integer.valueOf(id))) {
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
                DaoItensLoja dao = new DaoItensLoja();
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
                        lista = dao.findAllField(itensLoja, "nome", "LIKE",
                                "%" + pesquisar + "%");
                    }

                } else {
                    lista = dao.findAll(itensLoja);
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
}
