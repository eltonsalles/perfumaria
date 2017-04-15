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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Elton
 */
@WebServlet("/sistema")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void service(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String controller = request.getParameter("controller");
        String nomeClasse = 
                "br.senac.tads.pi3a.controller.Controller" + controller;
        
        try {
            // Cria uma instância conforme o parâmetro controller
            Class<?> classe = Class.forName(nomeClasse);
            
            // Toda classe de funcionalidade vai ter uma interface Logica
            Logica logica = (Logica) classe.newInstance();
            
            // Variável com parâmetros padrão para cada action
            Class<?> paramTypes[] = {
                HttpServletRequest.class, HttpServletResponse.class};
            
            // Cria o método para invoca-lo dinamicamente
            Method metodo = logica.getClass().getMethod(action, paramTypes);
            
            // Recebe a String (nome da jsp) após a execução do método
            String pagina = (String) metodo.invoke(logica, request, response);
            
            // Faz o forwand para a página JSP
            request.getRequestDispatcher(pagina).forward(request, response);
        } catch (
                ClassNotFoundException | 
                InstantiationException | 
                IllegalAccessException | 
                ServletException | 
                IOException e) {
            System.out.println(e.getMessage());
        } catch (
                NoSuchMethodException | 
                SecurityException | 
                IllegalArgumentException | 
                InvocationTargetException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
