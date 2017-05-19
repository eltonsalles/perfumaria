<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Cadastrar Usuário</h2>
    <form action="#" method="post" class="col-md-4">
        <div class="form-group col-md-12">
            <input type="hidden" class="form-control" id="id" name="id">
        </div>
        <!--Verificar-->
        <div class="form-group col-md-12 <c:if test="${errorValidation.funcionario eq true}">has-error</c:if>">
            <label class="control-label" for="funcionario">Código do Funcionário</label>
            <input type="text" class="form-control" id="funcionario" placeholder="Código do Funcionário" name="funcionario" maxlength="10" required>
        </div>
            <!--Validação Alfanumérica do Login de usuário - letras e numero-->
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="login">Login</label>
            <input type="text" class="form-control" id="login" placeholder="Login" name="login" maxlength="50" required pattern="[a-zA-Z0-9]+">
        </div>
            <!--Inicio da senha com Letra maiuscula, caracteres minusculo e numeros-->
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="senha">Senha</label>
            <input type="password" class="form-control" id="senha" placeholder="Senha" name="senha" maxlength="8" required pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="nivel-usuario">Nível de Usuário</label>
            <select class="form-control" id="nivel-usuario" name="nivel-usuario">
                <option value="">---</option>
                <option value="1">Admin</option>
                <option value="2">Diretor</option>
                <option value="3">Gerente De Backoffice</option>
                <option value="4">Gerente De TI</option>
                <option value="5">Gerente De Vendas</option>
                <option value="6">Retaguarda</option>
                <option value="7">Suporte Técnico</option>
                <option value="8">Vendedor</option>
            </select>
        </div>
        <div class="form-group col-md-12">
            <a href="#" class="btn btn-default">
                Excluir&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </a>
            <button type="reset" class="btn btn-default">
                Cancelar&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
            <button type="submit" class="btn btn-default">
                Salvar&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
            </button>
        </div>
    </form>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>