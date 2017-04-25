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
import br.senac.tads.pi3a.model.Cliente;
import br.senac.tads.pi3a.model.Endereco;
import br.senac.tads.pi3a.model.Loja;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Elton
 */
public class ControllerCliente implements Logica {
    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        // Se o formulário for submetido por post então entra aqui
        if (request.getMethod().equalsIgnoreCase("post")) {
            // Implatar validação...
            
            /**
             * #Mock - Pegando os dados do formulário e apenas arrumando
             * o tamanho para fazer a inserção
             */
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            Cliente cliente = new Cliente();
            cliente.setStatus(true);
            cliente.setCpf(request.getParameter("cpf").replaceAll("\\D", ""));
            cliente.setNome(request.getParameter("nome"));
            
            Date dataNascimento = new Date(sdf.parse(request.getParameter("data-nascimento")).getTime());
            cliente.setDataNascimento(dataNascimento);
            cliente.setSexo(request.getParameter("sexo").charAt(0));
            cliente.setEstadoCivil(request.getParameter("estado-civil"));
            cliente.setCelular(request.getParameter("celular").replaceAll("\\D", ""));
            cliente.setTelefone(request.getParameter("telefone").replaceAll("\\D", ""));
            cliente.setEmail(request.getParameter("email"));
            
            Endereco endereco = new Endereco();
            endereco.setLogradouro(request.getParameter("logradouro"));
            endereco.setNumero(request.getParameter("numero"));
            endereco.setComplemento(request.getParameter("complemento"));
            endereco.setBairro(request.getParameter("bairro"));
            endereco.setCep(request.getParameter("cep").replaceAll("\\D", ""));
            endereco.setCidade(request.getParameter("cidade"));
            endereco.setUf(request.getParameter("uf"));
            
            cliente.setEndereco(endereco);
            
            Loja loja = new Loja();
            loja.setId(1);
            cliente.setLoja(loja);
            
            if (DaoCliente.insert(cliente) != -1) {
                session.setAttribute("msgTeste", true);
                return "redirect";
                // Deu certo
            } else {
                // Deu errado
            }
        }
        
        return "/WEB-INF/jsp/cadastrar-cliente.jsp";
    }

    @Override
    public String editar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String excluir(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String pesquisar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "/WEB-INF/jsp/consultar-cliente.jsp";
    }

    @Override
    public String listar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
