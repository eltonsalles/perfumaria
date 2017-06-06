<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Consultar Venda</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Venda&action=pesquisar" method="post">
        <div class="form-group col-md-6">
            <label class="control-label" for="pesquisar">Pesquisar</label>
            <div class="input-group">
                <input type="text" class="form-control" id="pesquisar" placeholder="Digite o código da venda" name="pesquisar" maxlength="150" pattern="^([a-zA-Zà-úÀ-Ú0-9]{1,})|([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$">
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
            <th>Cliente</th>
            <th>Data</th>
            <th>Produto</th>
            <th>Qtde.</th>
            <th>Preço Unidade</th>
            <th>Sub-Total</th>
            <th>Valor da Venda</th>
            <th>Ações</th>
        </tr>
        <c:if test="${not empty sessionScope.data.venda[0]}">
        <tr>
            <td><c:out value="${sessionScope.data.venda[0]}"></c:out></td>
            <td><c:out value="${sessionScope.data.status[0] eq true ? 'Ativa' : 'Cancelada'}"></c:out></td>
            <td><c:out value="${sessionScope.data.nome[0]}"></c:out></td>
            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${sessionScope.data.dataCadastro[0]}" /></td>
            <td>
            <c:set var="i" value="0"></c:set>
            <c:forEach begin="0" end="${sessionScope.data.cont[0]}" varStatus="loop">
                <c:out value="${sessionScope.data.produto[i]}"></c:out><br>
            <c:set var="i" value="${i + 1}"></c:set>
            </c:forEach>
            </td>
            <td>
            <c:set var="i" value="0"></c:set>
            <c:forEach begin="0" end="${sessionScope.data.cont[0]}" varStatus="loop">
                <c:out value="${sessionScope.data.quantidade[i]}"></c:out><br>
            <c:set var="i" value="${i + 1}"></c:set>
            </c:forEach>
            </td>
            <fmt:setLocale value="pt_BR"></fmt:setLocale>
            <td>
            <c:set var="i" value="0"></c:set>
            <c:forEach begin="0" end="${sessionScope.data.cont[0]}" varStatus="loop">
                <fmt:formatNumber value="${sessionScope.data.precoUnidade[i]}" type="currency" /><br>
            <c:set var="i" value="${i + 1}"></c:set>
            </c:forEach>
            </td>
            <td>
            <c:set var="i" value="0"></c:set>
            <c:forEach begin="0" end="${sessionScope.data.cont[0]}" varStatus="loop">
                <fmt:formatNumber value="${sessionScope.data.precoTotal[i]}" type="currency" /><br>
            <c:set var="i" value="${i + 1}"></c:set>
            </c:forEach>
            </td>
            <td><fmt:formatNumber value="${sessionScope.data.total[0]}" type="currency" /></td>
            <td>
                <a href="sistema?controller=Venda&action=excluir&id=<c:out value="${sessionScope.data.venda[0]}"></c:out>" class="btn btn-default" role="button" title="Cancelar">
                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                </a>
            </td>
        </tr>
        </c:if>
        <c:remove scope="session" var="data"></c:remove>
    </table>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>