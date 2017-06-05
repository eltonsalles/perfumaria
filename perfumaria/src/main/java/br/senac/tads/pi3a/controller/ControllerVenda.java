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

import br.senac.tads.pi3a.dao.DaoVenda;
import br.senac.tads.pi3a.inputFilter.InputFilterVenda;
import br.senac.tads.pi3a.model.Usuario;
import br.senac.tads.pi3a.model.Venda;
import br.senac.tads.pi3a.validation.ValidationDate;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Elton
 */
public class ControllerVenda implements Logica {
    
    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws Exception {
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário cliente
                InputFilterVenda inputFilterVenda
                        = new InputFilterVenda(request.getParameterMap());
                
                // Cria um variável com os dados do formulário,
                // mas sem validação
                Map<String, Object[]> data = inputFilterVenda.getDataForm();
                
                // Faz a validação do formulário de venda
                if (inputFilterVenda.isValid()) {
                    // Pega a conexão
                    Connection conn = (Connection) request
                            .getAttribute("connection");
                    
                    // Pega os dados do usuário logado
                    Usuario usuario = (Usuario) session
                            .getAttribute("usuarioLogado");
                    
                    // Cria um objeto venda com os dados válidos do formulário
                    Venda venda = (Venda) inputFilterVenda.createModel();
                    
                    // Seta as infomações do usuário
                    venda.getFuncionario().setId(usuario.getFuncionario()
                            .getId());
                    venda.getLoja().setId(usuario.getFuncionario().getLoja()
                            .getId());
                    
                    // Chama a daoVenda
                    DaoVenda daoVenda = new DaoVenda(conn);
                    
                    // Faz a inserção
                    if (daoVenda.insert(venda) > 0) {
                        session.setAttribute("alert", "alert-success");
                        session.setAttribute("alertMessage",
                                    "Cadastro realizado com sucesso.");
                        return "novo";
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterVenda.getErrorValidation());
                    session.setAttribute("data", data);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }
            }
            
            return "WEB-INF/jsp/cadastrar-venda.jsp";
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String excluir(HttpServletRequest request,
            HttpServletResponse response, HttpSession session)
            throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String pesquisar(HttpServletRequest request,
            HttpServletResponse response, HttpSession session)
            throws Exception {
        return "/WEB-INF/jsp/cancelar-venda.jsp";
    }
    
    public String relatorio(HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Pega os dados do usuário logado
                Usuario usuario = (Usuario) session
                        .getAttribute("usuarioLogado");
                
                DaoVenda dao = new DaoVenda(
                        (Connection) request.getAttribute("connection"));
                List<Venda> lista = null;

                // Verifica se as datas são válidas
                if (request.getParameter("data-inicial") != null
                        && !request.getParameter("data-inicial").isEmpty()
                        && request.getParameter("data-final") != null
                        && !request.getParameter("data-final").isEmpty()) {
                    String dataInicial = request.getParameter("data-inicial");
                    String dataFinal = request.getParameter("data-final");
                    
                    ValidationDate vD = new ValidationDate();
                    
                    if (vD.isValid(dataInicial) && vD.isValid(dataFinal)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        sdf.setLenient(false);
                        
                        Date dI = sdf.parse(dataInicial);
                        Date dF = sdf.parse(dataFinal);

                        // Verifica se a data final está depois da inicial e
                        // antes de hoje
                        if (dF.after(dI) && dF.before(new Date())) {
                            // 5 = gerente de vendas
                            if (usuario.getNivelUsuario().getId() == 5) {
                                String idLoja = String.valueOf(usuario
                                        .getFuncionario().getLoja().getId());
                                
                                lista = dao.findAll(
                                        new String[]{"status", "loja_id",
                                            "data_venda", "data_venda"},
                                        new String[]{"=", "=", ">=", "<="},
                                        new String[]{"true", idLoja,
                                            dataInicial, dataFinal},
                                        new String[]{"and", "and",
                                            "and", "and"});
                            } else {
                                lista = dao.findAll(
                                        new String[]{"status", "data_venda",
                                            "data_venda"},
                                        new String[]{"=", ">=", "<="},
                                        new String[]{"true", dataInicial,
                                            dataFinal},
                                        new String[]{"and", "and", "and"});
                            }
                        } else {
                            session.setAttribute("alert", "alert-danger");
                            session.setAttribute("alertMessage",
                                    "A data final não pode ser anterior a da"
                                    + " inicial.");
                            
                            return "/WEB-INF/jsp/relatorio-vendas.jsp";
                        }
                    }
                    
                    if (lista != null && !lista.isEmpty()) {
                        session.setAttribute("listaVendas", lista);
                        return "pesquisar";
                    } else {
                        session.setAttribute("alert", "alert-warning");
                        session.setAttribute("alertMessage",
                                "A consulta não retornou nenhum resultado.");
                    }
                } else {
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique as datas informadas.");
                }
            }
            
            return "/WEB-INF/jsp/relatorio-vendas.jsp";
            
        } catch (ParseException e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a consulta.");
            return "pesquisar";
        }
    }
}
