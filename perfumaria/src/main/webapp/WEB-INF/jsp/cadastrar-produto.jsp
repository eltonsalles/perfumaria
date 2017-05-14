<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2><c:out value="${sessionScope.itensLoja.produto.id > 0 ? 'Alterar' : 'Cadastrar'}"></c:out> Produtos</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Produto&action=<c:out value="${sessionScope.itensLoja.produto.id > 0 ? 'editar' : 'novo'}"></c:out>" method="post">
        <div class="form-group col-md-12">
            <input class="control-label" type="hidden" id="id" name="id" value="<c:out value="${sessionScope.itensLoja.produto.id > 0 ? sessionScope.itensLoja.produto.id : ''}"></c:out>">
        </div>
        <div class="form-group col-md-5 <c:if test="${errorValidation.nome eq true}">has-error</c:if>">
            <label class="control-label" for="nome">Nome</label>
            <input type="text" class="form-control" id="nome" placeholder="Nome do produto" name="nome" maxlength="150" required pattern="^([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.itensLoja.produto.nome}"></c:out>">
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.dataCadastro eq true}">has-error</c:if>">
            <label class="control-label" for="data-cadastro">Data do Cadastro</label>
            <input type="date" class="form-control" id="data-cadastro" name="data-cadastro" required min="1900-01-01" max="2100-12-31" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${sessionScope.itensLoja.dataCadastro}" />">
        </div>
        <div class="form-group col-md-2 status">
            <label>Status</label><br>
            <label class="radio-inline">
                <input type="radio" id="ativo" value="true" name="status" checked required>Ativo
            </label>
            <label class="radio-inline">
                <input type="radio" id="inativo" value="false" name="status" required <c:if test="${sessionScope.produto.status eq false}">checked</c:if>>Inativo
            </label>
        </div>
        <div class="form-group col-md-6 <c:if test="${errorValidation.categoria eq true}">has-error</c:if>">
            <label class="control-label" for="categoria">Categoria</label>
            <input type="text" class="form-control" id="categoria" placeholder="Categoria do produto" name="categoria" maxlength="100" pattern="^([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.itensLoja.produto.categoria}"></c:out>">
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.marca eq true}">has-error</c:if>">
            <label class="control-label" for="marca">Marca</label>
            <input type="text" class="form-control" id="marca" placeholder="Marca" name="marca" maxlength="150" pattern="^([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.itensLoja.produto.marca}"></c:out>">
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.estoque eq true}">has-error</c:if>">
            <label class="control-label" for="estoque">Quantidade</label>
            <input type="number" class="form-control" id="estoque" placeholder="Quantidade" name="estoque" min="0" required pattern="^\d+$" value="<c:out value="${sessionScope.itensLoja.estoque}"></c:out>">
        </div>
        <div class="form-group col-md-2 <c:if test="${errorValidation.valorUnidadeMedida eq true}">has-error</c:if>">
            <label class="control-label" for="valor-unidade-medida">Valor Unid. Medida</label>
            <input type="text" class="form-control" id="valor-unidade-medida" placeholder="Vlr. Unid. de Med." name="valor-unidade-medida" maxlength="7" required pattern="^\d+$" value="<c:out value="${sessionScope.itensLoja.produto.valorUnidadeMedida}"></c:out>">
        </div>
        <div class="form-group col-md-2 <c:if test="${errorValidation.unidadeMedida eq true}">has-error</c:if>">
            <label class="control-label" for="unidade-medida">Unid. Medida</label>
            <select class="form-control" id="unidade-medida" name="unidade-medida" required>
                <c:set var="unidadeMedida" value="${sessionScope.itensLoja.produto.unidadeMedida}"></c:set>
                <option value="">---</option>
                <option value="ml"<c:if test="${fn:containsIgnoreCase(unidadeMedida, 'ml')}">selected</c:if>>ml</option>
                <option value="g" <c:if test="${fn:containsIgnoreCase(unidadeMedida, 'g')}">selected</c:if>>g</option>
            </select>
        </div>
        <fmt:setLocale value="pt_BR"></fmt:setLocale>
        <div class="form-group col-md-2 <c:if test="${errorValidation.valorCompra eq true}">has-error</c:if>">
            <label class="control-label" for="valor-compra">Preço de compra</label>
            <input type="text" class="form-control" id="valor-compra" placeholder="R$" name="valor-compra" maxlength="7" required value="<fmt:formatNumber value="${sessionScope.itensLoja.valorCompra}" type="currency" />">
        </div>
        <div class="form-group col-md-2 <c:if test="${errorValidation.valorVenda eq true}">has-error</c:if>">
            <label class="control-label" for="valor-venda">Preço de venda</label>
            <input type="text" class="form-control" id="valor-venda" placeholder="R$" name="valor-venda" maxlength="7" required value="<fmt:formatNumber value="${sessionScope.itensLoja.valorVenda}" type="currency" />">
        </div>
        <div class="form-group col-md-2 <c:if test="${errorValidation.genero eq true}">has-error</c:if>">
            <label class="control-label" for="genero">Gênero</label>
            <select class="form-control" id="genero" name="genero" required>
                <c:set var="genero" value="${sessionScope.itensLoja.produto.genero}"></c:set>
                <option value="">---</option>
                <option value="masculino"<c:if test="${fn:containsIgnoreCase(genero, 'masculino')}">selected</c:if>>Masculino</option>
                <option value="feminino"<c:if test="${fn:containsIgnoreCase(genero, 'feminino')}">selected</c:if>>Feminino</option>
            </select>
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation.descricao eq true}">has-error</c:if>">
            <label class="control-label" for="descricao">Descrição</label>
            <textarea class="form-control" rows="3" name="descricao" pattern="^([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$"><c:out value="${sessionScope.itensLoja.produto.descricao}"></c:out></textarea>
        </div>
        <div class="form-group col-md-offset-8 col-md-4">
            <c:if test="${sessionScope.itensLoja.produto.id > 0}">
            <a href="sistema?controller=Produto&action=excluir&id=<c:out value="${sessionScope.itensLoja.produto.id}"></c:out>" class="btn btn-default">
                Excluir&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </a>
            </c:if>
            <c:if test="${empty sessionScope.itensLoja.produto.id || sessionScope.itensLoja.produto.id == 0}">
            <button type="reset" class="btn btn-default">
                Cancelar&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
            </c:if>
            <button type="submit" class="btn btn-default">
            <c:out value="${sessionScope.itensLoja.produto.id > 0 ? 'Alterar' : 'Salvar'}"></c:out>&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
            </button>
        </div>
    </form>
    <c:remove scope="session" var="itensLoja"></c:remove>
    <c:remove scope="session" var="errorValidation"></c:remove>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>