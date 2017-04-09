<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Cadastrar Usuário</h2>
    <form action="#" method="post" class="col-md-4">
        <div class="form-group col-md-12">
            <input type="hidden" class="form-control" id="id" name="id">
        </div>
        <div class="form-group col-md-12">
            <label for="login">Login</label>
            <input type="text" class="form-control" id="login" placeholder="Login" name="login" maxlength="50" required>
        </div>
        <div class="form-group col-md-12">
            <label for="senha">Senha</label>
            <input type="password" class="form-control" id="senha" placeholder="Senha" name="senha" maxlength="8" required>
        </div>
        <div class="form-group col-md-12">
            <label for="nivel-usuario">Nível de Usuário</label>
            <select class="form-control" id="nivel-usuario" name="nivel-usuario">
                <option value="">---</option>
                <option value="1">Diretor</option>
                <option value="2">Gerente De Backoffice</option>
                <option value="3">Gerente De Ti</option>
                <option value="4">Gerente De Vendas</option>
                <option value="5">Retaguarda</option>
                <option value="6">Vendedor</option>
            </select>
        </div>
        <div class="form-group col-md-12">
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
    </form>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>