<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<div class="col-md-10 content">   
    <h2>Redefinição de Senha</h2>
    <form action="#" method="post" class="col-md-4">
        <div class="form-group col-md-12">
            <input type="hidden" class="form-control" id="id" name="id">
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="login">Informe seu Login</label>
                <input type="text" class="form-control" id="login" placeholder="Informe seu login" name="login" maxlength="50" required>
            </div>
            <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="senha">Infome seu CPF:</label>
                <input type="text" class="form-control" id="senha" placeholder="informe sua senha" name="senha" maxlength="11" required>
            </div>
            <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="novaSenha">Informe a Senha:</label>
                <input type="password" class="form-control" id="senha" placeholder="informe sua senha" name="novasenha" maxlength="8" required>
            </div>
            <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="confirmarNovaSenha">Confirme a senha:</label>
                <input type="password" class="form-control" id="senha" placeholder="informe sua senha" name="senha" maxlength="8" required>
            </div>
            <div class="form-group col-md-12" id="">
                <input class="btn btn-default" type="reset" name="limpar" id="limparbtn" value="Limpar"/>
                <input class="btn btn-primary" type="submit" value="Enviar"><br><br>
            </div>
        </form>
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>