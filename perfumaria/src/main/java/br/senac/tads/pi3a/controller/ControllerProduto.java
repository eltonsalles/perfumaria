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
public class ControllerProduto implements Logica {
    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Se o formulário for submetido por post então entra aqui
        if (request.getMethod().equalsIgnoreCase("post")) {
            // Implatar validação...
            
            
        }
        
        return "/WEB-INF/jsp/cadastrar-produto.jsp";
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
