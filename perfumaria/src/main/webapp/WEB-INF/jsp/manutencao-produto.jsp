<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Manutenção de Produto</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form id="form-produtos" action="sistema?controller=Produto&action=movimentar" method="post">
        <div class="form-group col-md-6 <c:if test="${errorValidation.nome eq true}">has-error</c:if>">
                <label class="control-label" for="nome">Nome do Produto</label>
                <select class="form-control" id="produto" name="produto" required>
                    <option value="">---</option>
                <c:forEach items="${sessionScope.listaProdutos}" var="produto">
                    <option value="<c:out value="${produto.id}"></c:out>"><c:out value="${produto.nome}"></c:out></option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group col-md-6 <c:if test="${errorValidation.loja eq true}">has-error</c:if>">
                <label class="control-label" for="loja">Lista de Lojas</label>
                <select class="form-control" id="produto" name="loja" required>
                    <option value="">---</option>
                <c:forEach items="${sessionScope.listaLoja}" var="loja">
                    <option value="<c:out value="${loja.id}"></c:out>"><c:out value="${loja.razaoSocial}"></c:out></option>
                </c:forEach>
            </select> 
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.justificativa eq true}">has-error</c:if>">
                <label class="control-label" for="justificativa">Justificativa</label>
                <select class="form-control" id="justificativa" name="justificativa" required>
                <c:set var="justificativa" value="${sessionScope.historicoProduto.tipoMovimentacao}"></c:set>
                    <option value="">---</option>
                    <option value="entrada"<c:if test="${fn:containsIgnoreCase(justificativa, 'entrada')}">selected</c:if>>Entrada</option>
                <option value="saida"<c:if test="${fn:containsIgnoreCase(justificativa, 'saida')}">selected</c:if>>Saida</option>
                <option value="fora de linha"<c:if test="${fn:containsIgnoreCase(justificativa, 'fora de linha')}">selected</c:if>>Fora de linha</option>
                <option value="quebra"<c:if test="${fn:containsIgnoreCase(justificativa, 'quebra')}">selected</c:if>>Quebra</option>
                </select>
            </div>    
            <div class="form-group col-md-2">
                <input type="hidden" class="form-control" id="id" name="id">
            </div>
            <div class="form-group col-md-5 <c:if test="${errorValidation.quantidade eq true}">has-error</c:if>">
                <label class="control-label" for="quantidade">Quantidade</label>
                <input type="number" class="form-control" id="manutencao" placeholder="Quantidade" name="quantidade" min="0" required pattern="^\d+$" value="<c:out value="${sessionScope.historicoProduto.quantidade}"></c:out>">
            </div>
            <div class="form-group col-md-12 <c:if test="${errorValidation.descricao eq true}">has-error</c:if>">
            <label class="control-label" for="descricao" name="descricao" pattern="^([a-zA-Zà-úÀ-Ú0-9])([a-zA-Zà-úÀ-Ú0-9]|,|\.|-|\s)+$"><c:out value="${sessionScope.historicoProduto.descricao}"></c:out>Observação</label>
                <textarea class="form-control" rows="3"></textarea>
            </div>
            <div class="form-group col-md-offset-8 col-md-4">
                <button type="reset" class="btn btn-default">
                    Cancelar&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                </button>
                <button type="submit" class="btn btn-default">
                    Salvar&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
                </button>

            </div>  
        <c:remove scope="session" var="historicoProduto"></c:remove>
        <c:remove scope="session" var="errorValidation"></c:remove>
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>