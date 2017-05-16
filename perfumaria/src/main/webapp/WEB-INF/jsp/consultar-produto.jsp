<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Consultar Produto</h2>
    <form action="sistema?controller=Produto&action=pesquisar" method="post">
        <div class="form-group col-md-6 <c:if test="${errorValidation.pesquisar eq true}">has-error</c:if>">
            <label class="control-label" for="pesquisar">Pesquisar</label>
            <div class="input-group">
                <input type="text" class="form-control" id="pesquisar" placeholder="Digite o código ou nome" name="pesquisar" maxlength="150" pattern="[0-9]{1,150}+$">
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
            <th>Nome</th>
            <th>Data de Nascimento</th>
            <th>CPF</th>
            <th>Ações</th>
        </tr>
        <c:forEach items="${sessionScope.listaItensLoja}" var="itemLoja">
            <tr>
                <td><c:out value="${itemLoja.produto.id}"></c:out></td>
                <td>
                    <a href="sistema?controller=Produto&action=editar&id=<c:out value="${itemLoja.produto.id}"></c:out>" class="btn btn-default" role="button" title="Editar">
                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
        <c:remove scope="session" var="listaItensLoja"></c:remove>
    </table>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>