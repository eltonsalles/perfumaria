<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Relatório de vendas</h2>
    <form>
        <div class="form-group col-md-6 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <h3>Pesquisar por data</h3><br>
            <label for="relat-estoque">Data Inicial:</label>
            <input type="date" id="datai" name="datainicial"/>
        </div>
        <div class="form-group col-md-6 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="relat-estoque">Data Final:</label>
            <input type="date" id="dataf" name="datafinal"/>
        </div>
            <button type="submit" class="btn btn-primary">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
            </button>
        
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