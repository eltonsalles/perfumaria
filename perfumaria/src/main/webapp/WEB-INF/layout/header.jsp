<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <title>The Code</title>
        <meta http-equiv="Content-Type" content="text/html" charset=UTF-8">
        
        <!-- Bootstrap minified CSS -->
        <link rel="stylesheet" href="assets/lib/bootstrap/3.3.7/css/bootstrap.min.css">

        <!-- Theme Bootstrap minified -->
        <link rel="stylesheet" href="assets/lib/bootstrap/3.3.7/css/bootstrap-theme.min.css">
        
        <!-- CSS Bootstrap + Chosen -->
        <link rel="stylesheet" href="assets/lib/chosen/1.7.0/css/bootstrap-chosen.min.css">
        
        <!-- CSS -->
        <link rel="stylesheet" href="assets/css/style.css">
        
        <!-- JQuery minified -->
        <script src="assets/lib/jquery/jquery-3.2.0.min.js"></script>

        <!-- Bootstrap minified JavaScript -->
        <script src="assets/lib/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        
        <!-- JQuery Chosen -->
        <script src="assets/lib/chosen/1.7.0/js/chosen.jquery.min.js"></script>
        
        <!-- JavaScript -->
        <script src="assets/js/programa.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row header">
                <div class="col-md-offset-1 col-md-9">
                    <img src="assets/img/the-code.jpg" alt="The Code">
                </div>
                <div class="col-md-2">
                    <c:if test="${not empty sessionScope.usuarioLogado}">
                        <h4><span class="label label-default">Usuário: <c:out value="${fn:substring(sessionScope.usuarioLogado.funcionario.nome, 0, fn:indexOf(sessionScope.usuarioLogado.funcionario.nome, ' '))}"></c:out></span></h4>
                    </c:if>
                </div>
            </div>
            <div class="row division"></div>