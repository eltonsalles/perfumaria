<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Relatório de Estoque</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Produto&action=relatorio" method="post" class="form-inline">
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon">Data Inicial</span>
                <input type="date" class="form-control" id="data-inicial" name="data-inicial" required min="1900-01-01" max="2100-12-31" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${param.dataInicial}" />">
            </div>
            <div class="input-group">
                <span class="input-group-addon">Data Final</span>
                <input type="date" class="form-control" id="data-final" name="data-final" required min="1900-01-01" max="2100-12-31" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${param.dataFinal}" />">
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
            <th>Código</th>
            <th>Nome</th>
            <th>Categoria</th>
            <th>Valor de Custo</th>
            <th>Valor de Venda</th>
            <th>Estoque</th>
            <th>Loja</th>
        </tr>
        <c:forEach items="${sessionScope.listaProdutos}" var="item">
            <tr>
                <td><c:out value="${item.produto.id}"></c:out></td>
                <td><c:out value="${item.produto.nome}"></c:out></td>
                <td><c:out value="${item.produto.categoria}"></c:out></td>
                <fmt:setLocale value="pt_BR"></fmt:setLocale>
                <td class="dinheiro"><fmt:formatNumber value="${item.valorCompra}" type="number" pattern="#,##0.00" /></td>
                <td class="dinheiro"><fmt:formatNumber value="${item.valorVenda}" type="number" pattern="#,##0.00" /></td>
                <td><c:out value="${item.estoque}"></c:out></td>
                <td><c:out value="${item.loja.razaoSocial}"></c:out></td>
            </tr>
        </c:forEach>
        <c:remove scope="session" var="listaProdutos"></c:remove>
        </table>
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>