<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<div class="col-md-10 content">
    <form action="#" method="post" class="col-md-4">
        <div class="form-group col-md-12">
            <input type="hidden" class="form-control" id="id" name="id">
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="login">Login de Usuário</label>
            <input type="text" class="form-control" id="login" placeholder="Informe seu login" name="login" maxlength="50" required>
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="senha">Senha</label>
            <input type="password" class="form-control" id="senha" placeholder="informe sua senha" name="senha" maxlength="8" required>
        </div>
        <div class="form-group col-md-12" id="">
            <input class="btn btn-default" type="reset" name="limpar" id="limparbtn" value="Limpar"/>
            <input class="btn btn-primary" type="submit" value="Entrar"><br><br>
            <span class="glyphicon glyphicon-log-out" aria-hidden="true"  ></span>&nbsp;Esqueceu sua senha?
        </div>
    </form>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>