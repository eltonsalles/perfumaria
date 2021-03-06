<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
                <div class="col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <li class="active">
                            <a href="/perfumaria/">
                                <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                                Inicio
                            </a>
                        </li>
                        <c:set var="nivelAcesso" value="${sessionScope.usuarioLogado.nivelUsuario.tipo}"></c:set>
                        <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de vendas' || nivelAcesso == 'vendedor'}">
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                Cliente
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Cliente&action=novo">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="/perfumaria/sistema?controller=Cliente&action=pesquisar">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de ti' || nivelAcesso == 'suporte tecnico'}">
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-check" aria-hidden="true"></span>
                                Funcionário
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Funcionario&action=novo">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="/perfumaria/sistema?controller=Funcionario&action=pesquisar">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de ti' || nivelAcesso == 'suporte tecnico'}">
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-tent" aria-hidden="true"></span>
                                Loja
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Loja&action=novo">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="/perfumaria/sistema?controller=Loja&action=pesquisar">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de backoffice' || nivelAcesso == 'retaguarda' || nivelAcesso == 'gerente de vendas' || nivelAcesso == 'vendedor'}">
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-tag" aria-hidden="true"></span>
                                Produto
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de backoffice' || nivelAcesso == 'retaguarda'}">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Produto&action=novo">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                </c:if>
                                <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de backoffice' || nivelAcesso == 'retaguarda' || nivelAcesso == 'gerente de vendas' || nivelAcesso == 'vendedor'}">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Produto&action=pesquisar">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                                </c:if>
                                <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de backoffice' || nivelAcesso == 'retaguarda'}">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Produto&action=movimentar">
                                        <span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>
                                        Movimentar
                                    </a>
                                </li>
                                </c:if>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de vendas' || nivelAcesso == 'vendedor'}">
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
                                Vendas
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de vendas' || nivelAcesso == 'vendedor'}">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Venda&action=novo">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                </c:if>
                                <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de vendas'}">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Venda&action=pesquisar">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                                </c:if>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de ti'}">
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                                Usuários
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Usuario&action=novo">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="/perfumaria/sistema?controller=Usuario&action=pesquisar">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'diretor' || nivelAcesso == 'gerente de vendas' || nivelAcesso == 'gerente de backoffice'}">
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
                                Relatórios
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'diretor' || nivelAcesso == 'gerente de vendas'}">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Venda&action=relatorio">
                                        <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
                                        Vendas
                                    </a>
                                </li>
                                </c:if>
                                <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'diretor' || nivelAcesso == 'gerente de backoffice'}">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Produto&action=relatorio">
                                        <span class="glyphicon glyphicon-tag" aria-hidden="true"></span>
                                        Estoque
                                    </a>
                                </li>
                                </c:if>
                                <c:if test="${nivelAcesso == 'admin' || nivelAcesso == 'gerente de backoffice'}">
                                <li>
                                    <a href="/perfumaria/sistema?controller=Produto&action=historico">
                                        <span class="glyphicon glyphicon-inbox" aria-hidden="true"></span>
                                        Histórico de Produto
                                    </a>
                                </li>
                                </c:if>
                            </ul>
                        </li>
                        </c:if>
                        <li>
                            <a href="/perfumaria/sistema?controller=Usuario&action=logout">
                                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                                Sair
                            </a>
                        </li>
                        <br><br><br><br>
                    </ul>
                </div>