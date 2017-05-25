<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Consultar Funcion�rio</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Funcionario&action=pesquisar" method="post">
        <div class="form-group col-md-6 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="pesquisar">Pesquisar</label>
            <div class="input-group">
                <input type="text" class="form-control" id="pesquisar" placeholder="Digite o CPF ou nome" name="pesquisar" maxlength="150" pattern="^([a-zA-Z�-��-�0-9])([a-zA-Z�-��-�0-9]|\.|-|\s)+$">
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
            <th>C�digo</th>
            <th>Status</th>
            <th>Nome</th>
            <th>Data de Nascimento</th>
            <th>CPF</th>
            <th>A��es</th>
        </tr>
        <c:forEach items="${sessionScope.listaFuncionarios}" var="funcionario">
            <tr>
                <td><c:out value="${funcionario.id}"></c:out></td>
                <td><c:out value="${funcionario.status eq true ? 'Ativo' : 'Inativo'}"></c:out></td>
                <td><c:out value="${funcionario.nome}"></c:out></td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${funcionario.dataNascimento}" /></td>
                <td class="cpfs"><c:out value="${funcionario.cpf}"></c:out></td>
                <td>
                    <a href="sistema?controller=Funcionario&action=editar&id=<c:out value="${funcionario.id}"></c:out>" class="btn btn-default" role="button" title="Editar">
                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
        <c:remove scope="session" var="listaFuncionarios"></c:remove>
    </table>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>