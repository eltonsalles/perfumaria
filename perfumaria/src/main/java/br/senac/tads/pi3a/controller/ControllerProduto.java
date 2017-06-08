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

import br.senac.tads.pi3a.dao.DaoHistoricoProduto;
import br.senac.tads.pi3a.dao.DaoItensLoja;
import br.senac.tads.pi3a.dao.DaoLoja;
import br.senac.tads.pi3a.dao.DaoProduto;
import br.senac.tads.pi3a.inputFilter.InputFilterManutencaoProduto;
import br.senac.tads.pi3a.inputFilter.InputFilterProduto;
import br.senac.tads.pi3a.model.HistoricoProduto;
import br.senac.tads.pi3a.model.ItensLoja;
import br.senac.tads.pi3a.model.Loja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Produto;
import br.senac.tads.pi3a.model.Usuario;
import br.senac.tads.pi3a.validation.ValidationDate;
import br.senac.tads.pi3a.validation.ValidationInt;
import java.sql.Connection;
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
public class ControllerProduto implements Logica {

    @Override
    public String novo(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws Exception {
        try {
            // Pega a conexão com o banco de dados
            Connection conn = (Connection) request
                    .getAttribute("connection");

            Loja loja = new Loja();
            DaoLoja daoLoja = new DaoLoja(conn);
            List<Model> listaLoja = daoLoja.findAll(loja);

            session.setAttribute("listaLoja", listaLoja);
            
            // Usuário logado
            Usuario user = (Usuario) request.getSession()
                    .getAttribute("usuarioLogado");

            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário de produto
                InputFilterProduto inputFilterProduto
                        = new InputFilterProduto(request.getParameterMap());

                // Cria um objeto itens loja com os dados do formulário,
                // mas sem validação
                ItensLoja itensLoja = (ItensLoja) inputFilterProduto.getData();

                if (inputFilterProduto.isValid()) {
                    // Atualiza o objeto itens loja com os dados validados
                    itensLoja = (ItensLoja) inputFilterProduto.createModel();

                    // Chama a DAO de produto passando a conexão e o objeto
                    // a ser inserido
                    DaoProduto daoProduto = new DaoProduto(conn,
                            itensLoja.getProduto());

                    // Garante que o produto ainda não existe na loja
                    // que está se cadastrando
                    if (daoProduto.produtoExisteLoja(itensLoja.getProduto()
                            .getNome().toUpperCase(), user.getFuncionario()
                                    .getLoja().getId()) == -1) {

                        List listaPorNome = daoProduto.findAll(itensLoja
                                .getProduto(), "UPPER(nome)", "=", itensLoja
                                .getProduto().getNome().toUpperCase());

                        int idProduto;
                        if (listaPorNome.isEmpty()) {
                            // Faz a inserção na tabela produto
                            idProduto = daoProduto.insert();
                        } else {
                            Produto produto = (Produto) listaPorNome.get(0);
                            idProduto = produto.getId();
                        }

                        itensLoja.getProduto().setId(idProduto);

                        // Chama a DAO de itens de loja passando a conexão
                        // e o objeto a ser inserido
                        DaoItensLoja daoItensLoja = new DaoItensLoja(conn,
                                itensLoja);

                        // Faz a inserção na tabela itens de loja
                        if (daoItensLoja.insert() != -1) {
                            session.setAttribute("alert", "alert-success");
                            session.setAttribute("alertMessage",
                                    "Cadastro realizado com sucesso.");
                            return "novo";
                        } else {
                            daoProduto.delete(itensLoja.getProduto().getId());
                        }
                    } else {
                        session.setAttribute("itensLoja", itensLoja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage", "Este nome de "
                                + "produto já está cadastrado para essa loja.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterProduto.getErrorValidation());
                    session.setAttribute("itensLoja", itensLoja);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }
            }

            return "WEB-INF/jsp/cadastrar-produto.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possivel realizar o cadastro.");
            return "novo";
        }
    }

    @Override
    public String editar(HttpServletRequest request,
            HttpServletResponse response, HttpSession session)
            throws Exception {
        try {
            // Pega a conexão
            Connection conn = (Connection) request.getAttribute("connection");

            Loja loja = new Loja();
            DaoLoja daoLoja = new DaoLoja(conn);
            List<Model> listaLoja = daoLoja.findAll(loja);

            session.setAttribute("listaLoja", listaLoja);

            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário de produtos
                InputFilterProduto inputFilterProduto
                        = new InputFilterProduto(request.getParameterMap());

                // Cria um objeto itensLoja com os dados do formulário,
                // mas sem validação
                ItensLoja itensLoja = (ItensLoja) inputFilterProduto.getData();

                if (inputFilterProduto.isValid()) {
                    // Atualiza os dados com as informações validadas
                    itensLoja = (ItensLoja) inputFilterProduto.createModel();

                    // Chama a DAO de produto passando a conexão e o objeto
                    // a ser alterado
                    DaoProduto daoProduto = new DaoProduto(conn,
                            itensLoja.getProduto());

                    // Garante que as alterações não dupliquem o nome do produto
                    if (daoProduto.produtoExisteLoja(
                            itensLoja.getProduto().getNome().toUpperCase(),
                            itensLoja.getLoja().getId()) 
                            == itensLoja.getProduto().getId()) {
                        // Chama a DAO de itens de loja passando a conexão e o
                        // objeto a ser alterado
                        DaoItensLoja daoItensLoja
                                = new DaoItensLoja(conn, itensLoja);

                        // Faz as alterações na tabela produto e itens de loja
                        if (daoProduto.update()
                                && daoItensLoja.update(
                                        itensLoja.getProduto().getId(),
                                        itensLoja.getLoja().getId())) {
                            session.setAttribute("alert", "alert-success");
                            session.setAttribute("alertMessage", "Produto"
                                    + " alterado com sucesso.");
                            session.setAttribute("id", itensLoja.getProduto()
                                    .getId());
                            return "editar";
                        }
                    } else {
                        // Manda para jsp os campos inválidos e uma mensagem
                        session.setAttribute("itensLoja", itensLoja);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Não foi encontrado nenhum produto com esse"
                                + " nome para essa loja.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterProduto.getErrorValidation());
                    session.setAttribute("itensLoja", itensLoja);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }
            } else {
                // Verifica se existe o parâmetro id
                if (request.getParameter("id") != null
                        && request.getParameter("loja") != null) {
                    String id = request.getParameter("id");
                    String idLoja = request.getParameter("loja");
                    
                    ValidationInt validationInt = new ValidationInt();

                    // Se os ids forem número válidos é feito a busca
                    if (validationInt.isValid(id)
                            && validationInt.isValid(idLoja)) {
                        Model itensLoja = new ItensLoja();
                        
                        // Chama a DAO passando a conexão
                        DaoItensLoja dao = new DaoItensLoja(conn);

                        // Faz uma consulta usando como critério o id do produto
                        // e id da loja para exibir os dados do produto para
                        // determinada loja
                        List lista = dao.findAll(itensLoja,
                                new String[]{"produto_id", "loja_id"},
                                new String[]{"=", "="},
                                new String[]{id, idLoja},
                                new String[]{"and", "and"});

                        if (!lista.isEmpty()) {
                            session.setAttribute("itensLoja", lista.get(0));
                        }
                    }
                }
            }
            
            return "/WEB-INF/jsp/cadastrar-produto.jsp";
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
            HttpServletResponse response, HttpSession session)
            throws Exception {
        try {
            // Verifica se existe o parâmetro id
            if (request.getParameter("id") != null) {
                String id = request.getParameter("id");
                boolean digito = true;

                // Garante que o id seja apenas números
                for (int i = 0; i < id.length(); i++) {
                    if (!Character.isDigit(id.charAt(i))) {
                        digito = false;
                        break;
                    }
                }

                if (digito) {
                    // Pega a conexão
                    Connection conn = (Connection) request
                            .getAttribute("connection");

                    ItensLoja itensLoja = new ItensLoja();

                    DaoItensLoja daoItensLoja
                            = new DaoItensLoja(conn, itensLoja);

                    Usuario user = (Usuario) request.getSession().getAttribute("usuarioLogado");

                    if (daoItensLoja.delete(Integer.valueOf(id),
                            user.getFuncionario().getLoja().getId())) {
                        session.setAttribute("alert", "alert-warning");
                        session.setAttribute("alertMessage",
                                "Produto excluído com sucesso.");
                        return "excluir";
                    }
                }
            }

            return "/WEB-INF/jsp/consultar-produto.jsp";
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
            HttpServletResponse response, HttpSession session)
            throws Exception {
        try {
            // Pega o usuário logado
            Usuario usuario = (Usuario) request.getSession()
                    .getAttribute("usuarioLogado");
            
            int idLoja = usuario.getFuncionario().getLoja().getId();
            int idNivelAcesso = usuario.getNivelUsuario().getId();
            
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                ItensLoja itensLoja = new ItensLoja();
                DaoItensLoja dao = new DaoItensLoja(
                        (Connection) request.getAttribute("connection"));
                List<Model> lista;

                // Se não houver valor para pesquisar então retorna tudo
                if (request.getParameter("pesquisar") != null
                        && !request.getParameter("pesquisar").isEmpty()) {
                    String pesquisar = request.getParameter("pesquisar");

                    // Verifica por onde a consulta é feita por id ou nome
                    boolean digito = true;
                    for (int i = 0; i < pesquisar.length(); i++) {
                        if (!Character.isDigit(pesquisar.charAt(i))) {
                            digito = false;
                            break;
                        }
                    }
                    
                    // 5 = gerente de vendas e 8 = vendedor
                    if (idNivelAcesso == 5 || idNivelAcesso == 8) {
                        if (digito) {
                            lista = dao.findAll(itensLoja,
                                    new String[]{"produto_id", "loja_id"},
                                    new String[]{"=", "="},
                                    new String[]{pesquisar,
                                        String.valueOf(idLoja)},
                                    new String[]{"and", "and"});
                        } else {
                            lista = dao.findAllPorNome(
                                    "%" + pesquisar.toUpperCase() + "%",
                                    idLoja);
                        }
                    } else {
                        if (digito) {
                            lista = dao.findAll(itensLoja, "produto_id",
                                    "=", pesquisar);
                        } else {
                            lista = dao.findAllPorNome(
                                "%" + pesquisar.toUpperCase() + "%");
                        }
                    }
                } else {
                    // 5 = gerente de vendas e 8 = vendedor
                    if (idNivelAcesso == 5 || idNivelAcesso == 8) {
                        lista = dao.findAll(itensLoja, "loja_id", "=",
                                String.valueOf(idLoja));
                    } else {
                        lista = dao.findAll(itensLoja);
                    }
                }

                if (lista != null && !lista.isEmpty()) {
                    session.setAttribute("listaItensLoja", lista);
                    return "pesquisar";
                } else {
                    session.setAttribute("alert", "alert-warning");
                    session.setAttribute("alertMessage",
                            "A consulta não retornou nenhum resultado.");
                }
            }

            return "/WEB-INF/jsp/consultar-produto.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a consulta.");
            return "pesquisar";
        }
    }

    public String movimentar(HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        try {
            Connection conn = (Connection) request.getAttribute("connection");

            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                // Classe de validação do formulário ManutencaoProduto
                InputFilterManutencaoProduto inputFilterManutencaoProduto
                        = new InputFilterManutencaoProduto(request.
                                getParameterMap());

                // Cria um objeto HistoricoProduto com os dados do formulário,
                // mas sem validação
                HistoricoProduto historicoProduto = (HistoricoProduto) inputFilterManutencaoProduto.getData();

                // Faz a validação do formulário Manutenção de Produto
                if (inputFilterManutencaoProduto.isValid()) {
                    // Atualiza o objeto HistoricoProduto com os dados validados
                    historicoProduto = (HistoricoProduto) inputFilterManutencaoProduto.createModel();

                    ItensLoja itensLoja = new ItensLoja();
                    DaoItensLoja daoItensLoja = new DaoItensLoja(conn);

                    List<Model> produto = daoItensLoja.findAll(itensLoja,
                            new String[]{"produto_id", "loja_id"},
                            new String[]{"=", "="},
                            new String[]{String.valueOf(historicoProduto
                                        .getProduto().getId()),
                                String.valueOf(historicoProduto.getLoja()
                                        .getId())},
                            new String[]{"and", "and"});

                    // Pega o produto se ele foi encontrado
                    if (produto.size() == 1) {
                        itensLoja = (ItensLoja) produto.get(0);
                    }

                    if (itensLoja.getProduto().getId() > 0) {
                        DaoHistoricoProduto daoHistoricoProduto
                                = new DaoHistoricoProduto(conn,
                                        historicoProduto);

                        // A dao retorna um id válido se fizer a inserção
                        if (daoHistoricoProduto.insert() != -1) {
                            // Faz a movimentação do produto na loja em questão
                            switch (historicoProduto.getTipoMovimentacao()
                                    .toLowerCase()) {
                                case "entrada":
                                    itensLoja.setEstoque(itensLoja.getEstoque()
                                            + historicoProduto.getQuantidade());
                                    break;

                                case "saida":
                                case "fora de linha":
                                case "quebra":
                                    itensLoja.setEstoque(itensLoja.getEstoque()
                                            - historicoProduto.getQuantidade());
                            }

                            daoItensLoja = new DaoItensLoja(conn, itensLoja);
                            daoItensLoja.update(itensLoja.getProduto().getId(),
                                    historicoProduto.getLoja().getId());

                            session.setAttribute("alert", "alert-success");
                            session.setAttribute("alertMessage",
                                    "Movimentação realizada com sucesso.");
                            return "novo";
                        }
                    } else {
                        // Manda para a jsp os campos inválidos e uma mensagem
                        session.setAttribute("errorValidation",
                                inputFilterManutencaoProduto
                                .getErrorValidation());
                        session.setAttribute("historicoProduto",
                                historicoProduto);
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "Este produto não existe para essa loja.");
                    }
                } else {
                    // Manda para a jsp os campos inválidos e uma mensagem
                    session.setAttribute("errorValidation",
                            inputFilterManutencaoProduto.getErrorValidation());
                    session.setAttribute("historicoProduto", historicoProduto);
                    session.setAttribute("alert", "alert-danger");
                    session.setAttribute("alertMessage",
                            "Verifique o(s) campo(s) em vermelho.");
                }
            }

            Produto produto = new Produto();
            DaoProduto daoProduto = new DaoProduto(conn);
            List<Model> listaProdutos = daoProduto.findAll(produto);

            session.setAttribute("listaProdutos", listaProdutos);

            Loja loja = new Loja();
            DaoLoja daoLoja = new DaoLoja(conn);
            List<Model> listaLoja = daoLoja.findAll(loja);

            session.setAttribute("listaLoja", listaLoja);

            return "/WEB-INF/jsp/manutencao-produto.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar o cadastro.");
            return "novo";
        }
    }

    public String relatorio(HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        try {
            // Se o formulário for submetido por post então entra aqui
            if (request.getMethod().equalsIgnoreCase("post")) {
                ItensLoja itensLoja = new ItensLoja();
                DaoItensLoja dao = new DaoItensLoja(
                        (Connection) request.getAttribute("connection"));
                List<Model> lista;

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
                            lista = dao.findAll(itensLoja,
                                    new String[]{"status", "loja_id",
                                        "data_cadastro", "data_cadastro"},
                                    new String[]{"=", "=", ">=", "<="},
                                    new String[]{"true", "1", // #MOCK
                                        dataInicial, dataFinal},
                                    new String[]{"and", "and", "and", "and"});
                        } else {
                            session.setAttribute("alert", "alert-danger");
                            session.setAttribute("alertMessage",
                                    "A data final não pode ser anterior a da"
                                    + " inicial.");

                            return "/WEB-INF/jsp/relatorio-estoque.jsp";
                        }
                    } else {
                        session.setAttribute("alert", "alert-danger");
                        session.setAttribute("alertMessage",
                                "A data final não pode ser após a data de hoje.");

                        return "/WEB-INF/jsp/relatorio-estoque.jsp";
                    }

                    if (lista != null && !lista.isEmpty()) {
                        session.setAttribute("listaProdutos", lista);
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

            return "/WEB-INF/jsp/relatorio-estoque.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a consulta.");
            return "pesquisar";
        }
    }

    public String historico(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            if (request.getMethod().equalsIgnoreCase("post")) {
                HistoricoProduto historicoProduto = new HistoricoProduto();
                DaoHistoricoProduto dao = new DaoHistoricoProduto(
                        (Connection) request.getAttribute("connection"));
                List<Model> lista;

                if (request.getParameter("pesquisar") != null
                        && !request.getParameter("pesquisar").isEmpty()) {
                    String pesquisar = request.getParameter("pesquisar");

                    // Verifica por onde a consulta é feita por id ou nome
                    boolean digito = true;
                    for (int i = 0; i < pesquisar.length(); i++) {
                        if (!Character.isDigit(pesquisar.charAt(i))) {
                            digito = false;
                            break;
                        }
                    }

                    if (digito) {
                        lista = dao.findAll(historicoProduto,
                                "produto_id", "=", pesquisar);
                        if (lista != null & !lista.isEmpty()) {
                            session.setAttribute("listaProdutos", lista);
                            return "pesquisar";
                        } else {
                            session.setAttribute("alert", "alert-warning");
                            session.setAttribute("alertMessage",
                                    "A consulta não retornou nenhum resultado.");
                        }
                    }
                } else {
                    session.setAttribute("alert", "alert-warning");
                    session.setAttribute("alertMessage",
                            "Informe o código do produto.");
                }
            }
            return "/WEB-INF/jsp/historico-produto.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            session.setAttribute("alert", "alert-danger");
            session.setAttribute("alertMessage",
                    "Não foi possível realizar a consulta.");
            return "historico";
        }
    }

    /**
     * Método que gera um json com as informações comuns dos produtos
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    public String produtos(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            if (request.getParameter("nome") != null
                    && !request.getParameter("nome").isEmpty()) {
                String nome = request.getParameter("nome");

                Produto produto = new Produto();
                DaoProduto dao = new DaoProduto((Connection) request
                        .getAttribute("connection"));

                List lista = dao.findAll(produto,
                        "UPPER(nome)", "LIKE", "%" + nome.toUpperCase() + "%");

                if (!lista.isEmpty()) {
                    request.setAttribute("produtos", lista);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return "/WEB-INF/api/produtos.jsp";
    }

    /**
     * Método que gera um json com as informações de produto para a venda
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    public String produto(HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        try {
            if (request.getParameter("id") != null
                    && !request.getParameter("id").isEmpty()) {
                Usuario usuario = (Usuario) session
                        .getAttribute("usuarioLogado");

                String idProduto = request.getParameter("id");
                String idLoja = String.valueOf(usuario.getFuncionario()
                        .getLoja().getId());

                Connection conn = (Connection) request
                        .getAttribute("connection");

                ItensLoja itensLoja = new ItensLoja();
                DaoItensLoja daoItensLoja = new DaoItensLoja(conn);
                List lista = daoItensLoja.findAll(itensLoja,
                        new String[]{"produto_id", "loja_id"},
                        new String[]{"=", "="},
                        new String[]{idProduto, idLoja},
                        new String[]{"and", "and"});

                if (lista.size() == 1) {
                    request.setAttribute("itensLoja", lista.get(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return "/WEB-INF/api/produto.jsp";
    }
}
