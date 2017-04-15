<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Manutenção de Produto</h2>
    <form>
        <div class="form-group col-md-12">
            <label for="nome">Nome do Produto</label>
            <input type="text" class="form-control" id="nome-produto" name="nome-produto" maxlength="150" required>
        </div>

        <div style="clear: left;"class="form-group col-md-5">
            <label for="justificativa">Justificativa</label>
            <input type="text" class="form-control" id="justificativa" name="justificativa" maxlength="150" required>
        </div>
        <div class="form-group col-md-2">
            <input type="hidden" class="form-control" id="id" name="id">
        </div>
        <div class="form-group col-md-5">
            <label for="nome">Nome do Produto</label>
            <input type="text" class="form-control" id="nome-produto" name="nome-produto" maxlength="150" required>
        </div>

        <div class="form-group col-md-12">
            <label for="descricao">Observação</label>
            <textarea class="form-control" rows="3"></textarea>
        </div>

        <div class="form-group col-md-11">
            <div class="form-group col-md-10">
                <input type="hidden" class="form-control" id="id" name="id">
            </div>
            <button type="submit" class="btn btn-default">
                Salvar&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
            </button>
        </div>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>