<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
            <div class="row">
                <div class="col-md-12 content login"><br><br><br><br><br><br>
                    <form class="col-md-offset-4 col-md-4" action="sistema?controller=Usuario&action=login" method="post">
                        <div class="form-group col-md-12 <c:if test="${errorValidation.login eq true}">has-error</c:if>">
                            <label class="control-label" for="login">Login de Usuário</label>
                            <input type="text" class="form-control" id="login" placeholder="Login" name="login" maxlength="50" required pattern="^\w+$">
                        </div>
                        <div class="form-group col-md-12 <c:if test="${errorValidation.senha eq true}">has-error</c:if>">
                            <label class="control-label" for="senha">Senha</label>
                            <input type="password" class="form-control" id="senha" placeholder="Senha" name="senha" maxlength="8" required pattern="^\w+$">
                        </div>
                        <div class="form-group col-md-offset-7 col-md-5">
                            <input class="btn btn-default" type="reset" name="limpar" id="limpar" value="Limpar"/>
                            <input class="btn btn-default" type="submit" value="Entrar">
                        </div>
                    </form>
                </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>