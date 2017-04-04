<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>The Code</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!-- Bootstrap minified CSS -->
        <link rel="stylesheet" href="assets/lib/bootstrap/3.3.7/css/bootstrap.min.css">

        <!-- Theme Bootstrap minified -->
        <link rel="stylesheet" href="assets/lib/bootstrap/3.3.7/css/bootstrap-theme.min.css">
        
        <!-- CSS -->
        <link rel="stylesheet" href="assets/css/style.css">
        
        <!-- JQuery minified -->
        <script src="assets/lib/jquery/jquery-3.2.0.min.js"></script>

        <!-- Bootstrap minified JavaScript -->
        <script src="assets/lib/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row header">
                <div class="col-md-offset-1 col-md-11">
                    <img src="assets/img/the-code.jpg" alt="The Code">
                </div>
            </div>
            <div class="row division"></div>
            <div class="row">
                <div class="col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <li class="active">
                            <a href="#">
                                <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                                Inicio
                            </a>
                        </li>
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                Cliente
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-check" aria-hidden="true"></span>
                                Funcionário
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-tent" aria-hidden="true"></span>
                                Loja
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-tag" aria-hidden="true"></span>
                                Produto
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
                                Vendas
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                                Usuários
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                                        Cadastrar
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                                        Consultar
                                    </a>
                                </li>
                            </ul>
                        </li>                        
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
                                Relatórios
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
                                        Vendas
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-tag" aria-hidden="true"></span>
                                        Estoque
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="glyphicon glyphicon-inbox" aria-hidden="true"></span>
                                        Histórico de Produto
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <br><br><br><br>
                    </ul>
                </div>
                <div class="col-md-9 content">Conteúdo</div>
            </div>
            <div class="row division"></div>
            <div class="row footer">
                <div class="col-md-offset-8 col-md-4">
                    <a href="#">Política de privacidade | </a>
                    <a href="#">Sobre | </a>
                    <a href="#">Encontrou um erro?</a>
                </div>
            </div>
            <div class="row rights">
                <div class="col-md-12">
                    <p>Todos os direitos reservados - 2017 - The Code</p>
                </div>
            </div>
        </div>
    </body>
</html>
