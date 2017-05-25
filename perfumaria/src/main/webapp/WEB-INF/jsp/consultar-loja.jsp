<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Consultar Loja</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Loja&action=pesquisar" method="post">
        <div class="form-group col-md-6 <c:if test="${errorValidation.pesquisar eq true}">has-error</c:if>">
            <label class="control-label" for="pesquisar">Pesquisar</label>
            <div class="input-group">
                <input type="text" class="form-control" id="pesquisar" placeholder="Digite o nome ou CNPJ" name="pesquisar" maxlength="150" pattern="[a-z\s][0-9]{1,150}+$">
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
            <th>Status</th>
            <th>Razão Social</th>
            <th>CNPJ</th>
            <th>Telefone</th>
            <th>Email</th>
            <th>Ações</th>
        </tr>
        <c:forEach items="${sessionScope.listaLojas}" var="loja">
        <tr>
            <td><c:out value="${loja.id}"></c:out></td>
            <td><c:out value="${loja.status eq true ? 'Ativo' : 'Inativo'}"></c:out></td>
            <td><c:out value="${loja.razaoSocial}"></c:out></td>
            <td class="cnpjs"><c:out value="${loja.cnpj}"></c:out></td>
            <td><c:out value="${loja.telefone}"></c:out></td>  
            <td><c:out value="${loja.email}"></c:out></td>
            <td>
                <a href="sistema?controller=Loja&action=editar&id=<c:out value="${loja.id}"></c:out>" class="btn btn-default" role="button" title="Editar">
                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                </a>
            </td>
        </tr>
        </c:forEach>
        <c:remove scope="session" var="listaLojas"></c:remove>
    </table>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>