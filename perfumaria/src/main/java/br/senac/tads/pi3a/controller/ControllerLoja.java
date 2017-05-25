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

import br.senac.tads.pi3a.dao.DaoLoja;
import br.senac.tads.pi3a.inputFilter.InputFilterLoja;
import br.senac.tads.pi3a.model.Loja;
import br.senac.tads.pi3a.model.Model;
import java.sql.Connection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Elton
 */
public class ControllerLoja implements Logica {

    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws Exception {

        try {

            //Valida se o formulário que está sendo submetido é do tipo post
            if (request.getMethod().equalsIgnoreCase("post")) {

                //Instancia a classe de Validação do formulário Loja
                InputFilterLoja inputFilterLoja
                        = new InputFilterLoja(request.getParameterMap());

                //Cria um obejto Lojacom dos dados do fomulário, sem validação
                Loja loja = (Loja) inputFilterLoja.getData();

                // Faz a validação do formulário Loja
                if (inputFilterLoja.isValid()) {
                    // Atualiza o objeto Loja com os dados validados
                    loja = (Loja) inputFilterLoja.createModel();

                    DaoLoja dao = new DaoLoja(
                            (Connection) request.getAttribute("connection"),
                            loja);

                    // Garante que o cnpj não esteja cadastrado na base de dados
                    if (dao.findAll(loja, "cnpj", "=", loja.getCnpj())
                            .isEmpty()) {
                        // Toda Loja deve ser cadastrado com status true
                        loja.setStatus(true);

                        // A dao retorna um id válido se fizer a inserção
                        if (dao.insert() != -1) {
                            session.setAttribute("alert", "alert-success");
                            session.setAttribute("alertMessage",
                                    "Cadastro realizado com sucesso.");
                            return "novo";
                        }
                    } else {
                        // Manda para a jsp os campos inválidos e uma mensagem
                        session.setAttribute("loja", loja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Este CNPJ já está cadastrado.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterLoja.getErrorValidation());
                    session.setAttribute("loja", loja);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }
            }

            return "/WEB-INF/jsp/cadastrar-loja.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar o cadastro.");
            return "novo";
        }
    }

    @Override
    public String editar(HttpServletRequest request,
            HttpServletResponse response, HttpSession session)
            throws Exception {

        try {
            //Valida se o formulário que está sendo submetido é do tipo post
            if (request.getMethod().equalsIgnoreCase("post")) {

                //Instancia a classe de Validação do formulário Loja
                InputFilterLoja inputFilterLoja
                        = new InputFilterLoja(request.getParameterMap());

                //Cria um obejto Lojacom dos dados do fomulário, sem validação
                Loja loja = (Loja) inputFilterLoja.getData();

                // Faz a validação do formulário Loja
                if (inputFilterLoja.isValid()) {

                    // Atualiza o objeto Loja com os dados validados
                    loja = (Loja) inputFilterLoja.createModel();

                    DaoLoja dao = new DaoLoja(
                            (Connection) request.getAttribute("connection"),
                            loja);

                    // Garante que não exista cpf repetido na base de dados
                    List<Model> lista = dao.findAll(loja, "cnpj", "=",
                            loja.getCnpj());

                    if (lista.size() == 1) {
                        if (lista.get(0).getId() == loja.getId()) {
                            if (dao.update()) {
                                session.setAttribute("alert", "alert-success");
                                session.setAttribute("alertMessage",
                                        "Cadastro alterado com sucesso.");
                                session.setAttribute("id", loja.getId());
                                return "editar";
                            }
                        } else {
                            // Manda para jsp os campos inválidos e uma mensagem
                            session.setAttribute("loja", loja);
                            session.setAttribute("alert", "alert-danger");
                            session.setAttribute("alertMessage",
                                    "Este CNPJ já está cadastrado.");
                        }
                    } else {
                        // Manda para jsp os campos inválidos e uma mensagem
                        session.setAttribute("loja", loja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Não foi encontrado nenhum cadastro com o CNPJ"
                                + " informado.");
                    }

                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterLoja.getErrorValidation());
                    session.setAttribute("loja", loja);
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
                    Model loja = new Loja();
                    DaoLoja dao = new DaoLoja(
                            (Connection) request.getAttribute("connection"));
                    loja = dao.findOne(loja, Integer.valueOf(request
                            .getParameter("id")));

                    session.setAttribute("loja", loja);
                }
            }

            return "/WEB-INF/jsp/cadastrar-loja.jsp";

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
                    Loja loja = new Loja();
                    DaoLoja dao = new DaoLoja(
                            (Connection) request.getAttribute("connection"),
                            loja);

                    if (dao.delete(Integer.valueOf(id))) {
                        session.setAttribute("alert", "alert-warning");
                        session.setAttribute("alertMessage",
                                "Cadastro excluído com sucesso.");
                        return "excluir";
                    }
                }
            }

            return "/WEB-INF/jsp/cadastrar-loja.jsp";

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
                Loja loja = new Loja();
                DaoLoja dao = new DaoLoja(
                        (Connection) request.getAttribute("connection"));
                List<Model> lista;

                // Se não houver valor para pesquisar então retorna tudo
                if (request.getParameter("pesquisar") != null
                        && !request.getParameter("pesquisar").isEmpty()) {
                    String pesquisar = request.getParameter("pesquisar");

                    // Verifica por onde a consulta será feita por CNPJ ou nome
                    boolean digito = true;
                    for (int i = 0; i < pesquisar.length(); i++) {
                        if (!Character.isDigit(pesquisar.charAt(i))) {
                            digito = false;
                            break;
                        }
                    }
                    if (digito && pesquisar.length() == 14) {
                        lista = dao.findAll(loja, "cnpj", "=", pesquisar);
                    } else {
                        lista = dao.findAll(loja, "nome_fantasia", "LIKE",
                                "%" + pesquisar + "%");
                    }

                } else {
                    lista = dao.findAll(loja);
                }

                if (lista != null && !lista.isEmpty()) {
                    session.setAttribute("listaLojas", lista);
                    return "pesquisar";
                } else {
                    session.setAttribute("alert", "alert-warning");
                    session.setAttribute("alertMessage",
                            "A consulta não retornou nenhum resultado.");
                }
            }

            return "/WEB-INF/jsp/consultar-loja.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a consulta.");
            return "pesquisar";
        }
    }
}
