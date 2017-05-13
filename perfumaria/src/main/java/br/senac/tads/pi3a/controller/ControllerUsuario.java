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
import br.senac.tads.pi3a.model.Usuario;
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
                    DaoUsuario dao = new DaoUsuario(usuario);

                    // Garante que o login não esteja cadastrado na base de dados
                    if (dao.findAll(usuario, "login", "=", usuario.getLogin())
                            .isEmpty()) {
                        // A dao retorna um id válido se fizer a inserção
                        if (dao.insert() != -1) {
                            session.setAttribute("alert", "alert-success");
                            session.setAttribute("alertMessage",
                                    "Usuario cadastrado com sucesso.");
                            return "novo";
                        }
                    } else {
                        // Manda para a jsp os campos inválidos e uma mensagem
                        session.setAttribute("usuario", usuario);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Este Login já está cadastrado.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterUsuario.getErrorValidation());
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique os campo em vermelho.");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String excluir(HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String pesquisar(HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
