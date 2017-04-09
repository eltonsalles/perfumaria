<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Consultar Funcionário</h2>
    <form>
        <div class="form-group col-md-6">
            <label for="pesquisar">Pesquisar</label>
            <div class="input-group">
                <input type="text" class="form-control" id="pesquisar" placeholder="Código, CPF ou nome" name="pesquisar">
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
            <th>Nome</th>
            <th>Data de Nascimento</th>
            <th>CPF</th>
            <th>Ações</th>
        </tr>
        <tr>
            <td>1</td>
            <td>Lorem ipsum dolor sit amet adipiscing elit</td>
            <td>00/00/0000</td>
            <td>000.000.000-00</td>
            <td>
                <a href="#" class="btn btn-default" role="button" title="Editar">
                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                </a>
            </td>
        </tr>
    </table>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>