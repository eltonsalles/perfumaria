<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Relatório de vendas</h2>
    <form>
        <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="data-nascimento">Data Inicial</label>
            <input type="date" class="form-control" id="data-inicial" name="data-inicial" required>
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="data-nascimento">Data Final</label>
            <input type="date" class="form-control" id="data-final" name="data-final" required>
        </div>
            <div class="form-group col-md-4">
            <a href="#" class="btn btn-default">
                Pesquisar&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </a>
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
                <th></th>
            </tr>
            <tr>
                <td>1</td>
                <td>Ativo</td>
                <td>exemplo</td>
                <td>00/00/0000</td>
                <td>R$: 32,00</td>
                <td>exemplo</td>
                <td>333</td>
                <td>
                    <a href="#" class="btn btn-default" role="button" title="Editar">
                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                    </a>
                </td>
            </tr>
        </table>
        <div class="form-group col-md-offset-10 col-md-2 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="vendasativas">Vendas Ativas</label>
            <input type="text" class="form-control" name="vendasativas">
        </div>
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>