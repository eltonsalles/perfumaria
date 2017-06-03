<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Histórico de Produtos</h2>
     <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Produto&action=historico" method="post">
         <div class="form-group col-md-6 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label for="pesquisar">Pesquisar</label>
            <div class="input-group">
                <input type="text" class="form-control" id="pesquisar" placeholder="Digite o código ou o nome do produto" name="pesquisar" maxlength="150" pattern="^([a-zA-Zà-úÀ-Ú0-9])([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$">
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
            <th>Nome do Produto</th>
            <th>Nome da Loja</th>
            <th>Justificativa</th>
            <th>Quantidade</th>
            <th>Data de Movimentação</th>
            <th>Observação</th>           
        </tr>
        <c:forEach items="${sessionScope.listaProdutos}" var="item">
            <tr>
                <td><c:out value="${item.historicoProduto.id}"></c:out></td>
                <td><c:out value="${item.produto.nome}"></c:out></td>
                <td><c:out value="${item.loja.nome}"></c:out>"</td>
                <td><c:out value="${item.historicoProduto.tipoMovimentacao}"></c:out></td>
                <td><c:out value="${item.historicoProduto.quantidade}"></c:out></td>
                <td><c:out value="${item.historicoProduto.dataMovimentacao}"></c:out>"</td>
                <td><c:out value="${item.historiaProduto.descricao}"></c:out></td>
            </tr>
        </c:forEach>
    </table>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>