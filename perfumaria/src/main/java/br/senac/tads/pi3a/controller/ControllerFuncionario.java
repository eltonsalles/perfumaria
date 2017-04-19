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
import br.senac.tads.pi3a.model.Endereco;
import br.senac.tads.pi3a.model.Funcionario;
import br.senac.tads.pi3a.model.Loja;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Elton
 */
public class ControllerFuncionario implements Logica {
    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Se o formulário for submetido por post então entra aqui
        if (request.getMethod().equalsIgnoreCase("post")) {
            // Implatar validação...
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            Funcionario funcionario = new Funcionario();
            
            funcionario.setStatus(true);
            Date dataAdmissao = new Date(sdf.parse(request.getParameter("data-admissao")).getTime());
            funcionario.setDataAdmissao(dataAdmissao);
            funcionario.setCargo(request.getParameter("cargo"));
            funcionario.setNome(request.getParameter("nome"));
            funcionario.setCpf(request.getParameter("cpf"));
            Date dataNascimento = new Date(sdf.parse(request.getParameter("data-nascimento")).getTime());
            funcionario.setDataNascimento(dataNascimento);
            funcionario.setSexo(request.getParameter("sexo").charAt(0));
            funcionario.setEstCivil(request.getParameter("estado-civil"));
            funcionario.setCelular(request.getParameter("celular"));
            funcionario.setTelefone(request.getParameter("telefone"));
            funcionario.setEmail(request.getParameter("email"));
            
            Endereco endereco = new Endereco();
            endereco.setLogradouro(request.getParameter("logradouro"));
            endereco.setNumero(request.getParameter("numero"));
            endereco.setComplemento(request.getParameter("complemento"));
            endereco.setBairro(request.getParameter("bairro"));
            endereco.setCep(request.getParameter("cep"));
            endereco.setCidade(request.getParameter("cidade"));
            endereco.setUf(request.getParameter("uf"));
            
            funcionario.setEndereco(endereco);
            
            Loja loja = new Loja();
            funcionario.setLoja(loja);
            
            if (DaoFuncionario.insert(funcionario)) {
                // Deu tudo certo...
            } else {
                // Deu errado...
            }
        }
        
        return "/WEB-INF/jsp/cadastrar-funcionario.jsp";
    }

    @Override
    public String editar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String excluir(HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String pesquisar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String listar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
