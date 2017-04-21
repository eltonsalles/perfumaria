<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Manutenção de Produto</h2>
    <form>
        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="nome">Nome do Produto</label>
            <input type="text" class="form-control" id="nome-produto" name="nome-produto" maxlength="150" required>
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="cargo">Justificativa</label>
            <select class="form-control" id="justificativa" name="justificativa" required>
                <option value="">---</option>
                <option value="entrada">Entrada</option>
                <option value="saida">Saida</option>
                <option value="fora de linha">Fora de linha</option>
                <option value="quebra">Quebra</option>
            </select>
        </div>    
        <div class="form-group col-md-2">
            <input type="hidden" class="form-control" id="id" name="id">
        </div>
        <div class="form-group col-md-5 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="nome">Nome do Produto</label>
            <input type="text" class="form-control" id="nome-produto" name="nome-produto" maxlength="150" required>
        </div>

        <div class="form-group col-md-12 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="descricao">Observação</label>
            <textarea class="form-control" rows="3"></textarea>
        </div>

        <div class="form-group col-md-11">
            <div class="form-group col-md-10">
                <input type="hidden" class="form-control" id="id" name="id">
            </div>
            <a href="#" class="btn btn-default">
                Excluir&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </a>
            <button type="reset" class="btn btn-default">
                Cancelar&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
            <button type="submit" class="btn btn-default">
                Salvar&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
            </button>
        </div>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>