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

import br.senac.tads.pi3a.dao.DaoFuncionario;
import br.senac.tads.pi3a.inputFilter.InputFilterFuncionario;
import br.senac.tads.pi3a.model.Funcionario;
import br.senac.tads.pi3a.model.Model;
import java.sql.Connection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Fillipe
 */
public class ControllerFuncionario implements Logica {

    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws Exception {
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário funcionário
                InputFilterFuncionario inputFilterFuncionario
                        = new InputFilterFuncionario(request.getParameterMap());
                // Cria um objeto funcionário com os dados do formulário,
                // mas sem validação
                Funcionario funcionario = (Funcionario) inputFilterFuncionario
                        .getData();
                // Faz a validação do formulário funcionário
                if (inputFilterFuncionario.isValid()) {
                    //Atualiza o objeto funcionário com os dados validados
                    funcionario = (Funcionario) inputFilterFuncionario
                            .createModel();
                    DaoFuncionario dao = new DaoFuncionario(
                            (Connection) request.getAttribute("connection"),
                            funcionario);
                    // Garante que o cpf não esteja cadastrado na base de dados
                    if (dao.findAll(funcionario, "cpf", "=",
                            funcionario.getCpf()).isEmpty()) {
                        // Todo funcionário deve ser cadastrado com status true
                        funcionario.setStatus(true);
                        // A dao retorna um id válido se fizer a inserção
                        if (dao.insert() != -1) {
                            session.setAttribute("alert", "alert-success");
                            session.setAttribute("alertMessage",
                                    "Cadastro realizado com sucesso.");
                            return "novo";
                        }
                    } else {
                        // Manda para a jsp os campos inválidos e uma mensagem   
                        session.setAttribute("funcionario", funcionario);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Este CPF já está cadastrado.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterFuncionario.getErrorValidation());
                    session.setAttribute("funcionario", funcionario);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }

            }

            return "/WEB-INF/jsp/cadastrar-funcionario.jsp";
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
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário funcionário
                InputFilterFuncionario inputFilterFuncionario
                        = new InputFilterFuncionario(request.getParameterMap());

                // Cria um objeto funcionário com os dados do formulário,
                // mas sem validação
                Funcionario funcionario = (Funcionario) inputFilterFuncionario
                        .getData();

                // Faz a validação do formulário funcionário
                if (inputFilterFuncionario.isValid()) {
                    // Atualiza o objeto funcionário com os dados validados
                    funcionario = (Funcionario) inputFilterFuncionario
                            .createModel();

                    DaoFuncionario dao = new DaoFuncionario(
                            (Connection) request.getAttribute("connection"),
                            funcionario);

                    // Garante que não exista cpf repetido na base de dados
                    List<Model> lista = dao.findAll(funcionario, "cpf", "=",
                            funcionario.getCpf());

                    if (lista.size() == 1) {
                        if (lista.get(0).getId() == funcionario.getId()) {
                            if (dao.update()) {
                                session.setAttribute("alert", "alert-success");
                                session.setAttribute("alertMessage",
                                        "Cadastro alterado com sucesso.");
                                session.setAttribute("id", funcionario.getId());
                                return "editar";
                            }
                        } else {
                            // Manda para jsp os campos inválidos e uma mensagem
                            session.setAttribute("funcionario", funcionario);
                            session.setAttribute("alert", "alert-danger");
                            session.setAttribute("alertMessage",
                                    "Este CPF já está cadastrado.");
                        }
                    } else {
                        // Manda para jsp os campos inválidos e uma mensagem
                        session.setAttribute("funcionario", funcionario);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Não foi encontrado nenhum cadastro com o CPF"
                                + " informado.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterFuncionario.getErrorValidation());
                    session.setAttribute("funcionario", funcionario);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }
            } else {
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
                        Model funcionario = new Funcionario();
                        DaoFuncionario dao 
                                = new DaoFuncionario((Connection) request
                                        .getAttribute("connection"));
                        funcionario = dao.findOne(funcionario,
                                Integer.valueOf(request.getParameter("id")));

                        session.setAttribute("funcionario", funcionario);
                    }
                }
            }

            return "/WEB-INF/jsp/cadastrar-funcionario.jsp";
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
                    Funcionario funcionario = new Funcionario();
                    DaoFuncionario dao = new DaoFuncionario(
                            (Connection) request.getAttribute("connection"),
                            funcionario);

                    if (dao.delete(Integer.valueOf(id))) {
                        session.setAttribute("alert", "alert-warning");
                        session.setAttribute("alertMessage",
                                "Cadastro excluído com sucesso.");
                        return "excluir";
                    }
                }
            }

            return "/WEB-INF/jsp/cadastrar-funcionario.jsp";
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
                Funcionario funcionario = new Funcionario();
                DaoFuncionario dao = new DaoFuncionario(
                        (Connection) request.getAttribute("connection"));
                List<Model> lista;

                // Se não houver valor para pesquisar então retorna tudo
                if (request.getParameter("pesquisar") != null
                        && !request.getParameter("pesquisar").isEmpty()) {
                    String pesquisar = request.getParameter("pesquisar");

                    // Verifica por onde a consulta será feita por cpf ou nome
                    boolean digito = true;
                    for (int i = 0; i < pesquisar.length(); i++) {
                        if (!Character.isDigit(pesquisar.charAt(i))) {
                            digito = false;
                            break;
                        }
                    }
                    if (digito && pesquisar.length() == 11) {
                        lista = dao.findAll(funcionario, "cpf", "=", pesquisar);
                    } else {
                        lista = dao.findAll(funcionario, "UPPER(nome)", "LIKE",
                                "%" + pesquisar.toUpperCase() + "%");
                    }

                } else {
                    lista = dao.findAll(funcionario);
                }

                if (lista != null && !lista.isEmpty()) {
                    session.setAttribute("listaFuncionarios", lista);
                    return "pesquisar";
                } else {
                    session.setAttribute("alert", "alert-warning");
                    session.setAttribute("alertMessage",
                            "A consulta não retornou nenhum resultado.");
                }
            }

            return "/WEB-INF/jsp/consultar-funcionario.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a consulta.");
            return "pesquisar";
        }
    }
}
