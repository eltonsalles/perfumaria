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

import br.senac.tads.pi3a.dao.DaoCliente;
import br.senac.tads.pi3a.inputFilter.InputFilterCliente;
import br.senac.tads.pi3a.model.Cliente;
import br.senac.tads.pi3a.model.Loja;
import br.senac.tads.pi3a.model.Model;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Elton
 */
public class ControllerCliente implements Logica {
    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws Exception {
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário cliente
                InputFilterCliente inputFilterCliente
                        = new InputFilterCliente(request);
                
                // Faz a validação do formulário cliente
                if (inputFilterCliente.isValid()) {
                    // Estando tudo certo, então é criado o objeto cliente com
                    // os dados do formulário
                    Cliente cliente = (Cliente) inputFilterCliente
                            .createModel();
                    
                    DaoCliente dao = new DaoCliente(cliente);

                    // A dao retorna um id válido se conseguir fazer a inserção
                    if (dao.insert() != -1) {
                        session.setAttribute("alert", "alert-success");
                        session.setAttribute("alertMessage",
                                "Cadastro realizado com sucesso.");
                        return "novo";
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    // de aviso
                    session.setAttribute("errorValidation",
                            inputFilterCliente.getErrorValidation());
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique os campo em vermelho.");
                }
            }
            
            return "/WEB-INF/jsp/cadastrar-cliente.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar o cadastro.");
            return "novo";
        }
    }

    @Override
    public String editar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Implatar validação

                /**
                 * #Mock - Pegando os dados do formulário e apenas arrumando
                 * o tamanho para fazer a alteração
                 */
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Cliente cliente = new Cliente();
                int id = Integer.valueOf(request.getParameter("id"));

                cliente.setId(id);
                cliente.setStatus(Boolean.valueOf(request.getParameter("status")));
                cliente.setCpf(request.getParameter("cpf").replaceAll("\\D", ""));
                cliente.setNome(request.getParameter("nome"));

                Date dataNascimento = new Date(sdf.parse(request.getParameter("data-nascimento")).getTime());
                cliente.setDataNascimento(dataNascimento);
                cliente.setSexo(request.getParameter("sexo").charAt(0));
                cliente.setEstadoCivil(request.getParameter("estado-civil"));
                cliente.setCelular(request.getParameter("celular").replaceAll("\\D", ""));
                cliente.setTelefone(request.getParameter("telefone").replaceAll("\\D", ""));
                cliente.setEmail(request.getParameter("email"));
                cliente.setLogradouro(request.getParameter("logradouro"));
                cliente.setNumero(request.getParameter("numero"));
                cliente.setComplemento(request.getParameter("complemento"));
                cliente.setBairro(request.getParameter("bairro"));
                cliente.setCep(request.getParameter("cep").replaceAll("\\D", ""));
                cliente.setCidade(request.getParameter("cidade"));
                cliente.setUf(request.getParameter("uf"));

                Loja loja = new Loja();
                loja.setId(1);
                cliente.setLoja(loja);

                DaoCliente dao = new DaoCliente(cliente);

                if (dao.update()) {
                    session.setAttribute("alert", "alert-success");
                    session.setAttribute("alertMessage", "Cadastro alterado com sucesso.");
                    session.setAttribute("id", id);
                    return "editar";
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
                    Model cliente = new Cliente();
                    DaoCliente dao = new DaoCliente();
                    cliente = dao.findOne(cliente, Integer.valueOf(request.getParameter("id")));

                    session.setAttribute("cliente", cliente);
                }
            }

            return "/WEB-INF/jsp/cadastrar-cliente.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage", "Não foi possível realizar a alteração.");
            session.setAttribute("id", 0);
            return "editar";
        }
    }

    @Override
    public String excluir(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
                    Cliente cliente = new Cliente();
                    DaoCliente dao = new DaoCliente(cliente);

                    if (dao.delete(Integer.valueOf(id))) {
                        session.setAttribute("alert", "alert-warning");
                        session.setAttribute("alertMessage", "Cadastro excluído com sucesso.");
                        return "excluir";
                    }
                }
            }

            return "/WEB-INF/jsp/cadastrar-cliente.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage", "Não foi possível realizar a exclusão.");
            return "excluir";
        }
    }

    @Override
    public String pesquisar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            if (request.getMethod().equalsIgnoreCase("post")) {
                Cliente cliente = new Cliente();
                DaoCliente dao = new DaoCliente();
                List<Model> lista;

                // Se não houver valor para pesquisar então retorna tudo
                if (request.getParameter("pesquisar") !=  null & !request.getParameter("pesquisar").isEmpty()) {
                    String pesquisar = request.getParameter("pesquisar");

                    /**
                     * #Mock para fazer consulta por cpf ou ppor nome
                     */
                    boolean digito = true;
                    for (int i = 0; i < pesquisar.length(); i++) {
                        if (!Character.isDigit(pesquisar.charAt(i))) {
                            digito = false;
                            break;
                        }
                    }
                    if (digito) {
                        lista = dao.findAll(cliente, "cpf", "=", pesquisar);
                    } else {
                        lista = dao.findAll(cliente, "nome", "LIKE", "%" + pesquisar + "%");
                    }

                } else {
                    lista = dao.findAll(cliente);
                }

                if (lista != null && !lista.isEmpty()) {
                    session.setAttribute("listaClientes", lista);
                    return "pesquisar";
                } else {
                    session.setAttribute("alert", "alert-warning");
                    session.setAttribute("alertMessage", "A consulta não retornou nenhum resultado.");
                }
            }

            return "/WEB-INF/jsp/consultar-cliente.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage", "Não foi possível realizar a consulta.");
            return "pesquisar";
        }
    }
}
