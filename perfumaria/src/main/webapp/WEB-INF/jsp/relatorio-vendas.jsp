<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Relatório de Vendas</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Venda&action=relatorio" method="post" class="form-inline">
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
            <div class="form-group col-md-offset-12 col-md-2">
                <label class="control-label" for="vendas-ativas">Total Vendido</label>
                <input type="text" value="" class="form-control" name="vendas-ativas" readonly="readonly">
            </div>
        </div>
    </form>
    <br>
    <table class="table table-striped">
        <tr>
            <th>Código</th>
            <th>Status</th>
            <th>Cliente</th>
            <th>Data</th>
            <th>Produtos</th>
            <th>Quantidade</th>
            <th>Valor da Venda</th>
            <th>Ações</th>
        </tr>
    <c:set var="valorTotal" value="${0}"></c:set>
    <c:forEach items="${sessionScope.listaVendas}" var="venda">
        <tr>
            <td><c:out value="${venda.id}"></c:out></td>
            <td><c:out value="${venda.status eq true ? 'Ativa' : 'Cancelada'}"></c:out></td>
            <td><c:out value="${venda.cliente.nome}"></c:out></td>
            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${venda.data}"/></td>
            <td>
            <c:forEach items="${venda.listaItensVenda}" var="itens">
                <c:out value="${itens.itens.produto.nome}"></c:out><br>
            </c:forEach>
            </td>
            <td>
            <c:forEach items="${venda.listaItensVenda}" var="itens">
                <c:out value="${itens.quantidade}"></c:out><br>
            </c:forEach>
            </td>
            <fmt:setLocale value="pt_BR"></fmt:setLocale>
            <td class="dinheiro"><fmt:formatNumber value="${venda.valorVenda}" type="number" pattern="#,##0.00" /></td>
            <td>
                <a href="sistema?controller=Venda&action=editar&id=<c:out value="${venda.id}"></c:out>" class="btn btn-default" role="button" title="Editar">
                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                </a>
            </td>
        </tr>
    </c:forEach>

    <c:remove scope="session" var="listaVendas"></c:remove>
    </table>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>