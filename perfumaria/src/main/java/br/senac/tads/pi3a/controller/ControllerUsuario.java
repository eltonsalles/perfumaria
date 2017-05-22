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

import br.senac.tads.pi3a.dao.DaoUsuario;
import br.senac.tads.pi3a.inputFilter.InputFilterUsuario;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Usuario;
import java.sql.Connection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Elton
 */
public class ControllerUsuario implements Logica {

    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws Exception {
        try {
            if (request.getMethod().equalsIgnoreCase("post")) {
                //classe de validação do formulário usuário
                InputFilterUsuario inputFilterUsuario
                        = new InputFilterUsuario(request.getParameterMap());

                // Cria um objeto usuario com os dados do formulário,
                // mas sem validação
                Usuario usuario = (Usuario) inputFilterUsuario.getData();

                if (inputFilterUsuario.isValid()) {
                    // Atualiza o objeto cliente com os dados validados
                    usuario = (Usuario) inputFilterUsuario.createModel();
                    DaoUsuario dao = new DaoUsuario(
                            (Connection) request.getAttribute("connection"),
                            usuario);

                    // Garante que o login não esteja cadastrado na base de dados
                    if (dao.findAll(usuario, "login", "=", usuario.getLogin())
                            .isEmpty()) {
                        // A dao retorna um id válido se fizer a inserção
                        if (dao.insert() != -1) {
                            session.setAttribute("alert", "alert-success");
                            session.setAttribute("alertMessage",
                                    "Usuário cadastrado com sucesso.");
                            return "novo";
                        }
                    } else {
                        // Manda para a jsp os campos inválidos e uma mensagem
                        session.setAttribute("usuario", usuario);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Este login já está cadastrado.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterUsuario.getErrorValidation());
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }
            }

            return "/WEB-INF/jsp/cadastrar-usuario.jsp";
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
            HttpServletResponse response,
            HttpSession session) throws Exception {
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário cliente
                InputFilterUsuario inputFilterUsuario
                        = new InputFilterUsuario(request.getParameterMap());
                
                // Cria um objeto usuario com os dados do formulário,
                // mas sem validação
                Usuario usuario = (Usuario) inputFilterUsuario.getData();
                
                // Faz a validação do formulário usuario
                if (inputFilterUsuario.isValid()) {
                    // Atualiza o objeto cliente com os dados validados
                    usuario = (Usuario) inputFilterUsuario.createModel();
                    
                    DaoUsuario dao = new DaoUsuario(
                            (Connection) request.getAttribute("connection"),
                            usuario);
                    
                    // Garante que não exista login repetido na base de dados
                    List<Model> lista = dao.findAll(usuario, "login", "=",
                            usuario.getLogin());
                    
                    if (lista.size() == 1) {
                        if (lista.get(0).getId() == usuario.getId()) {
                            if (dao.update()) {
                                session.setAttribute("alert", "alert-success");
                                session.setAttribute("alertMessage",
                                        "Cadastro alterado com sucesso.");
                                session.setAttribute("id", usuario.getId());
                                return "editar";
                            }
                        } else {
                            // Manda para jsp os campos inválidos e uma mensagem
                            session.setAttribute("usuario", usuario);
                            session.setAttribute("alert", "alert-danger");
                            session.setAttribute("alertMessage",
                                    "Este login já está cadastrado.");
                        }
                    } else {
                        // Manda para jsp os campos inválidos e uma mensagem
                        session.setAttribute("usuario", usuario);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Não foi encontrado nenhum cadastro com o login"
                                        + " informado.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterUsuario.getErrorValidation());
                    session.setAttribute("usuario", usuario);
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
                        Model usuario = new Usuario();
                        DaoUsuario dao
                                = new DaoUsuario((Connection) request
                                        .getAttribute("connection"));

                        usuario = dao.findOne(usuario, Integer.valueOf(id));
                        session.setAttribute("usuario", usuario);
                    }
                }
            }

            return "/WEB-INF/jsp/cadastrar-usuario.jsp";
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
            HttpServletResponse response,
            HttpSession session) throws Exception {
        try {
            //Procura o id que o usuário deseja excluir.
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
                    Usuario usuario = new Usuario();
                    DaoUsuario dao = new DaoUsuario(
                            (Connection) request.getAttribute("connection"),
                            usuario);

                    if (dao.delete(Integer.valueOf(id))) {
                        session.setAttribute("alert", "alert-warning");
                        session.setAttribute("alertMessage",
                                "Cadastro excluído com sucesso.");
                        return "excluir";
                    }
                }
            }

            return "/WEB-INF/jsp/cadastrar-usuario.jsp";
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
            HttpServletResponse response,
            HttpSession session) throws Exception {
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                Usuario usuario = new Usuario();
                DaoUsuario dao = new DaoUsuario(
                        (Connection) request.getAttribute("connection"));
                List<Model> lista;

                // Se não houver valor para pesquisar então retorna tudo
                if (request.getParameter("pesquisar") != null
                        && !request.getParameter("pesquisar").isEmpty()) {
                    String pesquisar = request.getParameter("pesquisar");

                    // Verifica por onde a consulta será feita por id ou login
                    boolean codigo = true;
                    for (int i = 0; i < pesquisar.length(); i++) {
                        if (!Character.isDigit(pesquisar.charAt(i))) {
                            codigo = false;
                            break;
                        }
                    }
                    if (codigo) {
                        lista = dao.findAll(usuario, "id", "=", pesquisar);
                    } else {
                        lista = dao.findAll(usuario, "UPPER(login)", "LIKE",
                                "%" + pesquisar.toUpperCase() + "%");
                    }

                } else {
                    lista = dao.findAll(usuario);
                }
                if (lista != null && !lista.isEmpty()) {
                    session.setAttribute("listaUsuarios", lista);
                    return "pesquisar";
                } else {
                    session.setAttribute("alert", "alert-warning");
                    session.setAttribute("alertMessage",
                            "A consulta não retornou nenhum resultado.");
                }
            }
            return "/WEB-INF/jsp/consultar-usuario.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a consulta.");
            return "pesquisar";
        }
    }
    
    public String login(HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        Connection conn = (Connection) request.getAttribute("connection");
        
        DaoUsuario dao = new DaoUsuario(conn);
        
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        
        Usuario usuario = new Usuario();
        
        List list = dao.findAll(usuario,
                new String[]{"login", "senha"},
                new String[]{"=", "="},
                new String[]{login, senha},
                new String[]{"and", "and"});
        
        if (list.size() == 1) {
            HttpSession sessionLogin = request.getSession(false);
            if (sessionLogin != null) {
                sessionLogin.invalidate();
            }
            
            sessionLogin = request.getSession(true);
            sessionLogin.setAttribute("usuarioLogado", (Usuario) list.get(0));
            
            return "index";
        }
        
        return "/login.jsp";
    }
}
