<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Histórico de Produtos</h2>
    <form>
         <div class="form-group col-md-6">
            <label for="pesquisar">Pesquisar</label>
            <div class="input-group">
                <input type="text" class="form-control" id="pesquisar" placeholder="Id ou nome (Em branco para pesquisa completa)" name="pesquisar">
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
            <th>Nome do Produto</th>
            <th>Data de Movimentação</th>
            <th>Tipo de Movimentação</th>
            <th>Valor de Compra</th>
            <th>Valor de Venda</th>
            <th></th>
        </tr>
        <tr>
            <td>1</td>
            <td>Ativo</td>
            <td>Exemplo</td>
            <td>11/11/1111</td>
            <td>XXXXXXXXXX</td>
            <td>R$: 99,99</td>
            <td>R$: 299,99</td>
            <td>
                <a href="#" class="btn btn-default" role="button" title="Editar">
                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                </a>
            </td>
        </tr>
    </table>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>