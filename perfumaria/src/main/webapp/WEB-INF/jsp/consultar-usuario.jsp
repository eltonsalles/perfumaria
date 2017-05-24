<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Consultar Usuário</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Usuario&action=pesquisar" method="post">
        <div class="form-group col-md-6 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="pesquisar">Pesquisar</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="pesquisar" placeholder="Digite o código ou login" name="pesquisar" maxlength="50" pattern="^\w+$">
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        </button>
                    </span>
                </div>
            </div>
        </form>
        <table class="table table-striped">
            <tr>
                <th>Código</th>
                <th>Login</th>
                <th>Nível de Usuário</th>
                <th>Ações</th>
            </tr>
        <c:forEach items="${sessionScope.listaUsuarios}" var="usuario">
            <tr>
                <td><c:out value="${usuario.id}"></c:out></td>
                <td><c:out value="${usuario.login}"></c:out></td>
                <td><c:out value="${usuario.nivelUsuario.tipo}"></c:out></td>
                    <td>
                        <a href="sistema?controller=Usuario&action=editar&id=<c:out value="${usuario.id}"></c:out>" class="btn btn-default" role="button" title="Editar">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                        </a>
                    </td>
                </tr>
        </c:forEach>
        <c:remove scope="session" var="listaUsuarios"></c:remove>
        </table>
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>