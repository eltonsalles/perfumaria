<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
     <h2><c:out value="${sessionScope.produto.id > 0 ? 'Alterar' : 'Cadastrar'}"></c:out> Cadastrar Produtos</h2>
     <jsp:include page="/WEB-INF/layout/message.jsp"/>
     <form action="sistema?controller=Cliente&action=<c:out value="${sessionScope.cliente.id > 0?'editar':'novo'}"></c:out>" method="post">
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            
                <input class="control-label" type="hidden"id="id" name="id"value="<c:out value="${sessionScope.produto.id > 0 ? sessionScope.cliente.id : ''}"></c:out>">
            </div>
            <div class="form-group col-md-5 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="nome">Nome</label>
                <input type="text" class="form-control" id="nome-produto" placeholder="Nome do produto" name="nome-produto" maxlength="150" required pattern="^([a-zA-Zà-úÀ-Ú]|\.|\s)+$" value="<c:out value="${sessionScope.produto.nome}"></c:out>">
        </div>
            
            <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="data-registro">Data do Registro</label>
                <input type="date" class="form-control" id="data-registro" name="data-registro" required min="1900-01-01" max="2100-12-31" value="<fmt:formatDate pattern ="yyy-MM-dd" value="${produto.dataRegistro}" />">
            </div>
            <div class="form-group col-md-2 status">
                <label>Status</label><br>
                <label class="radio-inline">
                    <input type="radio" id="ativo" value="true" name="status" checked  <c:if test="${sessionScope.itensLoja.status == 'a'.charAt(0)}">checked</c:if>>Ativo
                </label>
                <label class="radio-inline">
                    <input type="radio" id="inativo" value="false" name="status"  <c:if test="${sessionScope.itensLoja.status == 'i'.charAt(0)}">checked</c:if>>Inativo
                </label>
            </div>
            <div class="form-group col-md-6 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="nome">Categoria</label>
                <input type="text" class="form-control" id="categoria" placeholder="Categoria do produto" name="categoria" maxlength="100" pattern="^([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.itensLoja.estoque}"></c:out>">
            </div>

            <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="marca">Marca</label>
                <input type="text" class="form-control" id="marca" placeholder="Marca" name="marca" maxlength="150" pattern="^([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.produto.marca}"></c:out>">
            </div>
            <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="quantidade">Quantidade</label>
                <input type="int" class="form-control" id="quantidade" placeholder="Quantidade" name="quantidade" maxlength="7" required pattern="^\d+$" value="<c:out value="${sessionScope.itensVenda.quantidadeItem}"></c:out>">
            </div>
            <div class="form-group col-md-2 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="qtd-medida">Qtd. Medida</label>
                <input type="number" class="form-control" id="qtd-medida" placeholder="Quantidade" name="qtd-medida" maxlength="7"   required pattern="^\d+$" value="<c:out value="${sessionScope.produto.valorUnidadeMedida}"></c:out>">
            </div>
            <div class="form-group col-md-1" <c:if test="${errorValidation eq true}">has-error</c:if>">
                 <label class="control-label" for="uni-medida">Tipo</label>
                <select class="form-control" id="uni-medida" name="uni-medida" required><c:if test="${errorValidation eq true}">has-error</c:if>">
                   <c:set var="uniMedida" value="${sessionScope.produto.uniMedida}"></c:set>
                        <option value="">---</option>
                    <option value="ml"<c:if test="${fn:containsIgnoreCase(uniMedida, 'ml')}">selected</c:if>>ml</option>
                    <option value="g" <c:if test="${fn:containsIgnoreCase(uniMedida, 'g')}">selected</c:if>>g</option>
                </select>
            </div>
            <div class="form-group col-md-1 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <input type="hidden" class="form-control" id="id" name="id">
            </div>
            <div class="form-group col-md-2 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="compra">Preço compra</label>
                <input type="int" class="form-control" id="compra" placeholder="R$" name="compra" maxlength="7" required>
            </div>
            <div class="form-group col-md-2 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="venda">Preço venda</label>
                <input type="int" class="form-control" id="venda" placeholder="R$" name="venda" maxlength="7" required>
            </div>
            <div class="form-group col-md-2 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="genero">Genero</label>
                <select class="form-control" id="genero" name="genero" required>
                    <c:set var="genero" value="${sessionScope.produto.genero}"></c:set>
                    <option value="">---</option>
                    <option value="masculino"<c:if test="${fn:containsIgnoreCase(genero, 'masculino')}">selected</c:if>>Masculino</option>
                    <option value="feminino"<c:if test="${fn:containsIgnoreCase(genero, 'masculino')}">selected</c:if>>Feminino</option>
                </select>
            </div>
            <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="descricao">Descrição</label>
                <textarea class="form-control" rows="3" pattern="^([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.produto.descricao}"></c:out>"></textarea>
            </div>
            <div class="form-group col-md-offset-10 col-md-2">
                <a href="#" class="btn btn-default">
                    Excluir&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                </a>
                <button type="submit" class="btn btn-default">
                    Salvar&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
                </button>
            </div>
        </form>
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>