<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2><c:out value="${sessionScope.usuario.id > 0 ? 'Alterar' : 'Cadastrar'}"></c:out> Usuário</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Usuario&action=<c:out value="${sessionScope.usuario.id > 0 ? 'editar' : 'novo'}"></c:out>" method="post" class="col-md-4">
        <div class="form-group col-md-12">
            <input type="hidden" class="form-control" id="id" name="id" value="<c:out value="${sessionScope.usuario.id > 0 ? sessionScope.usuario.id : ''}"></c:out>">
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation.funcionario eq true}">has-error</c:if>">
            <label class="control-label" for="funcionario">Código do Funcionário</label>
            <select class="form-control" id="funcionario" name="funcionario">
                <option value></option>
                <c:forEach items="${sessionScope.listaFuncionarios}" var="funcionario">
                    <option value="<c:out value="${funcionario.id}"></c:out>"<c:if test="${sessionScope.usuario.id > 0}">selected</c:if>><c:out value="${funcionario.nome}"></c:out></option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation.login eq true}">has-error</c:if>">
            <label class="control-label" for="login">Login</label>
            <input type="text" class="form-control" id="login" placeholder="Login" name="login" maxlength="50" required pattern="^\w+$" value="<c:out value="${sessionScope.usuario.login}"></c:out>" <c:if test="${sessionScope.usuario.id > 0}">readonly</c:if>>
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation.senha eq true}">has-error</c:if>">
            <label class="control-label" for="senha">Senha</label>
            <input type="password" class="form-control" id="senha" placeholder="Senha" name="senha" maxlength="8" required pattern="^\w+$" value="">
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation.nivelUsuario eq true}">has-error</c:if>">
            <label class="control-label" for="nivel-usuario">Nível de Usuário</label>
            <select class="form-control" id="nivel-usuario" name="nivel-usuario">
            <c:set var="nivelUsuario" value="${sessionScope.usuario.nivelUsuario.id}"></c:set>
                <option value="">---</option>
                <option value="1" <c:if test="${fn:containsIgnoreCase(nivelUsuario, '1')}">selected</c:if> >Admin</option>
                <option value="2" <c:if test="${fn:containsIgnoreCase(nivelUsuario, '2')}">selected</c:if> >Diretor</option>
                <option value="3" <c:if test="${fn:containsIgnoreCase(nivelUsuario, '3')}">selected</c:if> >Gerente De Backoffice</option>
                <option value="4" <c:if test="${fn:containsIgnoreCase(nivelUsuario, '4')}">selected</c:if> >Gerente De TI</option>
                <option value="5" <c:if test="${fn:containsIgnoreCase(nivelUsuario, '5')}">selected</c:if> >Gerente De Vendas</option>
                <option value="6" <c:if test="${fn:containsIgnoreCase(nivelUsuario, '6')}">selected</c:if> >Retaguarda</option>
                <option value="7" <c:if test="${fn:containsIgnoreCase(nivelUsuario, '7')}">selected</c:if> >Suporte Técnico</option>
                <option value="8" <c:if test="${fn:containsIgnoreCase(nivelUsuario, '8')}">selected</c:if> >Vendedor</option>
            </select>
        </div>
        <div class="form-group col-md-12">
        <c:if test="${sessionScope.usuario.id > 0}">
            <a href="sistema?controller=Usuario&action=excluir&id=<c:out value="${sessionScope.usuario.id}"></c:out>" class="btn btn-default">
                Excluir&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </a>
        </c:if>

        <c:if test="${empty sessionScope.usuario.id || sessionScope.usuario.id == 0}">
            <button type="reset" class="btn btn-default">
                Cancelar&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
        </c:if>
        <button type="submit" class="btn btn-default">
            <c:out value="${sessionScope.usuario.id > 0 ? 'Alterar' : 'Salvar'}"></c:out>&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
        </button>
        </div>
    </form>
    <c:remove scope="session" var="usuario"></c:remove>
    <c:remove scope="session" var="errorValidation"></c:remove>
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>