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
package br.senac.tads.pi3a.filter;

import br.senac.tads.pi3a.model.Usuario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Elton
 */
@WebFilter(filterName = "LoginFilter", servletNames = {"ControllerServlet"}, urlPatterns = {"/sistema"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        // Converte as variáveis ao tipo necessário
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Se o usuário estiver tentando fazer login o sistema deixa ele passar
        if (httpRequest.getParameter("controller") != null
                && httpRequest.getParameter("action") != null) {
            String controller = httpRequest.getParameter("controller");
            String action = httpRequest.getParameter("action");
            
            if (controller.equalsIgnoreCase("Usuario")
                    && action.equalsIgnoreCase("login")) {
                chain.doFilter(request, response);
                return;
            }
        }
        
        // Tenta recuperar a sesão do usuário
        HttpSession session = httpRequest.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        
        // Usuário nulo significa que não está logado
        // Por tanto solicita o login
        if (usuario == null) {
            httpResponse.sendRedirect(httpRequest
                    .getContextPath()+ "/login.jsp");
            return;
        }
        
        // Nesse ponto é necessário verificar o nível de acesso
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
}
