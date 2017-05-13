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

import br.senac.tads.pi3a.dao.DaoItensLoja;
import br.senac.tads.pi3a.dao.DaoProduto;
import br.senac.tads.pi3a.inputFilter.InputFilterProduto;
import br.senac.tads.pi3a.model.ItensLoja;
import br.senac.tads.pi3a.model.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Elton
 */
public class ControllerProduto implements Logica {

    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
       
        try{
            if(request.getMethod().equalsIgnoreCase("post")){
                    
                InputFilterProduto inputFilterProduto = new InputFilterProduto(request.getParameterMap());
                
                // cria objeto itens de loja
                
                ItensLoja itensLoja =  (ItensLoja)inputFilterProduto.getData();
                
                if(inputFilterProduto.isValid()){
                    
                    itensLoja = (ItensLoja) inputFilterProduto.createModel();
                    
                    DaoProduto dao = new DaoProduto();
                    
                    if(dao.findAll(itensLoja,"nome","=",itensLoja.getProduto().getNome())
                            .isEmpty()){
                        
                        itensLoja.setStatus(true);
                        
                        
                        if(dao.insert() !=-1){
                            session.setAttribute("alert", "alert-sucess");
                            session.setAttribute("alertMessage","Cadastro realizado com sucesso" );
                            return "novo";
                        }
                    }else{
                        session.setAttribute("itensLoja", itensLoja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage", "Produto já cadastrado.");
                        
                    }
                }else{
                    
                     session.setAttribute("itensLoja", itensLoja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage", "Verifique os campos em vermelho.");
                    
                }
            }
            
            return "WEB-INF/jsp/cadastrar-produto.jsp";
        }catch(Exception e){
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage", "Não foi possivel realizar o cadastro.");
            return "novo";
        }
        
        
        
    }

    @Override
    public String editar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
       try{
           if(request.getMethod().equalsIgnoreCase("post")) {
               InputFilterProduto inputFilterProduto = new InputFilterProduto(request.getParameterMap());
          
           ItensLoja itensLoja = new ItensLoja();
           
           if(inputFilterProduto.isValid()){
               
               itensLoja = (ItensLoja) inputFilterProduto.createModel();
               
               DaoProduto dao = new DaoProduto(itensLoja.getProduto());
               DaoItensLoja daoLoja = new DaoItensLoja(itensLoja);
               
               List<Model> lista = dao.findAll(itensLoja,"nome","=",
                       itensLoja.getProduto().getNome());
              
               if(lista.size() == 1){
                   if(lista.get(0).getId() == itensLoja.getId()){
                       if(dao.update()) {
                           session.setAttribute("alert", "alert-success");
                                session.setAttribute("alertMessage",
                                        "Cadastro alterado com sucesso.");
                                session.setAttribute("id", itensLoja.getId());
                                return "editar";
                        }
                   }else{
                        session.setAttribute("cliente", itensLoja);
                            session.setAttribute("alert", "alert-danger");
                            session.setAttribute("alertMessage",
                                    "Este produto já está cadastrado.");
                        
                   }
               }else{
                   session.setAttribute("cliente", itensLoja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Não foi encontrado nenhum produto com o nome"
                                        + " informado.");
                   
                   
               }
           }else{
               session.setAttribute("errorValidation",
                            inputFilterProduto.getErrorValidation());
                    session.setAttribute("cliente", itensLoja);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique os campo em vermelho.");
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
                    Model itensLoja = new ItensLoja();
                    DaoProduto dao = new DaoProduto();
                    itensLoja = dao.findOne(itensLoja, Integer.valueOf(request
                            .getParameter("id")));

                    session.setAttribute("cliente", itensLoja);
                }
            }

       
       }catch(Exception e){
           
       }
       return "/WEB-INF/jsp/cadastrar-produto.jsp";
    }

    @Override
    public String excluir(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String pesquisar(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        return "/WEB-INF/jsp/consultar-produto.jsp";
    }
    
    public String movimentar(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "/WEB-INF/jsp/manutencao-produto.jsp";
    }
    
    public String relatorio(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "/WEB-INF/jsp/relatorio-estoque.jsp";
    }
    
    public String historico(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "/WEB-INF/jsp/historico-produto.jsp";
    }
}
