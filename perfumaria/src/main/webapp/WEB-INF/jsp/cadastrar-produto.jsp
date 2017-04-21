<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Cadastrar Produtos</h2>
    <form action="#" method="post">
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <input class="control-label" type="hidden" class="form-control" id="id" name="id">
        </div>
        <div class="form-group col-md-6 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="nome">Categoria</label>
            <input type="text" class="form-control" id="categoria" placeholder="Categoria do produto" name="categoria" maxlength="100" required>
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="data-nascimento">Data do Registro</label>
            <input type="date" class="form-control" id="data-registro" name="data-registro" required>
        </div>
        <div class="form-group col-md-1 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="img">Select File</label>
            <input type="file" id="img" class="file">
        </div>
        <div class="form-group col-md-6 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="nome">Nome</label>
            <input type="text" class="form-control" id="nome-produto" placeholder="Nome do produto" name="nome-produto" maxlength="150" required>
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="marca">Marca</label>
            <input type="text" class="form-control" id="marca" placeholder="Marca" name="marca" maxlength="150" required>
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="quantidade">Quantidade</label>
            <input type="int" class="form-control" id="quantidade" placeholder="Quantidade" name="quantidade" maxlength="7" required>
        </div>
        <div class="form-group col-md-2 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="medida">Qtd. Medida</label>
            <input type="int" class="form-control" id="qtd-medida" placeholder="Quantidade" name="qtd-medida" maxlength="7"  required> 
        </div>
        <div style="padding: 0; "class="form-group col-md-1">
            <label class="control-label" for="medida">Tipo</label>
            <select class="form-control" id="uni-medida" name="uni-medida" required><c:if test="${errorValidation eq true}">has-error</c:if>">
                <option value="">---</option>
                <option value="ml">ml</option>
                <option value="g">g</option>
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
        <div class="form-group col-md-1 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="genero">Genero</label>
            <select class="form-control" id="genero" name="genero" required>
                <option value="">---</option>
                <option value="masculino">Masculino</option>
                <option value="feminino">Feminino</option>
            </select>
        </div>
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="descricao">Descrição</label>
            <textarea class="form-control" rows="3"></textarea>
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