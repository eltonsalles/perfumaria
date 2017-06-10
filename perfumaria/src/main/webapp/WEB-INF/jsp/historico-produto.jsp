<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Histórico de Produtos</h2>
     <jsp:include page="/WEB-INF/layout/message.jsp"/>
     <form id="form-relatorio-historico" action="sistema?controller=Produto&action=historico" method="post" class="form-inline">
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon">Selecione o produto</span>
                <select class="form-control" id="produto" name="pesquisar">
                    <option value></option>
                    <c:forEach items="${sessionScope.listaProduto}" var="produto">
                        <option value="<c:out value="${produto.id}"></c:out>"><c:out value="${produto.nome}"></c:out></option>
                    </c:forEach>
                </select>
            </div>
            <div class="input-group">
                <span class="input-group-addon">Selecione a Loja</span>
                <select class="form-control" id="loja" name="pesquisar">
                    <option value></option>
                    <c:forEach items="${sessionScope.listaLoja}" var="loja">
                        <option value="<c:out value="${loja.id}"></c:out>"><c:out value="${loja.razaoSocial}"></c:out></option>
                    </c:forEach>
                </select>
                <span class="input-group-btn">
                    <button type="submit" class="btn btn-default">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    </button>
                </span>
            </div>
        </div>
    </form>
    <br>
    <table class="table table-striped">
        <tr>
            <th>Nome do Produto</th>
            <th>Nome da Loja</th>
            <th>Justificativa</th>
            <th>Quantidade</th>
            <th>Data de Movimentação</th>
            <th>Observação</th>           
        </tr>
        <c:forEach items="${sessionScope.listaProdutos}" var="item">
            <tr>
                <td><c:out value="${item.produto.nome}"></c:out></td>
                <td><c:out value="${item.loja.razaoSocial}"></c:out></td>
                <td><c:out value="${item.tipoMovimentacao}"></c:out></td>
                <td><c:out value="${item.quantidade}"></c:out></td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.dataMovimentacao}"/></td>
                <td><c:out value="${item.descricao}"></c:out></td>
            </tr>
        </c:forEach>
    </table>
    <c:remove scope="session" var="listaLoja"></c:remove>
    <c:remove scope="session" var="listaProduto"></c:remove>
    <c:remove scope="session" var="listaProdutos"></c:remove>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>