<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Relatório de vendas</h2>
    <form>
        <div class="form-group col-md-6">
            <label for="pesquisar">Pesquisar por data</label><br>
            <label for="relat-estoque">Data Inicial:</label>
            <input type="date" id="datai" name="datainicial"/>
            <label for="relat-estoque">Data Final:</label>
            <input type="date" id="dataf" name="datafinal"/>
            <button type="submit" class="btn btn-primary">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
            </button>
        </div>
    </form>
    <table class="table table-striped">
        <tr>
            <th>Código</th>
            <th>Status</th>
            <th>Cliente</th>
            <th>Data</th>
            <th>Valor da Venda</th>
            <th>Produtos</th>
            <th>Qtd</th>
        </tr>
        <tr>
            <td>1</td>
            <td>Exemplo Exemplo Exemplo Exemplo</td>
            <td>00/00/0000</td>
            <td>000.000.000-00</td>
            <td>
                <a href="#" class="btn btn-default" role="button" title="Editar">
                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                </a>
            </td>
        </tr>
    </table>
    <label for="vendasativas">Vendas Ativas</label>
    <input type="text" class="form-control" name="vendasativas" size="35">
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>