<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Cadastrar Venda</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form id="form-venda" action="sistema?controller=Venda&action=novo" method="post">
        <div class="form-group col-md-12">
            <div class="form-group col-md-2 <c:if test="${errorValidation.cpf eq true}">has-error</c:if>">
                <label class="control-label" for="cpf">CPF</label>
                <input type="text" class="form-control" id="cpf" placeholder="xxx.xxx.xxx-xx" name="cpf" maxlength="14" required pattern="^\d{3}.\d{3}.\d{3}-\d{2}$" value="<c:out value="${sessionScope.data.cpf[0]}"></c:out>">
            </div>
            <div class="form-group col-md-6">
                <label class="control-label" for="nome">Nome</label>
                <input type="text" class="form-control" id="nome" placeholder="Nome completo" name="nome" maxlength="150" required pattern="^([a-zA-Zà-úÀ-Ú])([a-zA-Zà-úÀ-Ú]|\.|\s)+$" value="<c:out value="${sessionScope.data.nome[0]}"></c:out>" readonly="readonly">
            </div>
            <div class="form-group col-md-1">
                <input type="hidden" class="form-control" id="id-cliente" name="id-cliente" value="<c:out value="${sessionScope.data.idCliente[0]}"></c:out>">
            </div>
        </div>
        <div class="form-group col-md-3">
            <label class="control-label" for="total">Total</label>
            <div class="input-group">
                <div class="input-group-addon">R$</div>
                <input type="text" class="form-control" id="total" placeholder="x.xxx,xx" name="total" maxLength="12" required readonly="readonly" pattern="(\d{1,3}\.)?\d{1,3},\d{2}$" value="<c:out value="${sessionScope.data.total[0] > 0 ? sessionScope.data.total[0] : '0,00'}"></c:out>">
            </div>
        </div>
        <div class="form-group col-md-offset-7 col-md-2">
            <button type="button" id="inserir-produto" class="btn btn-default">
                Inserir Produto&nbsp;&nbsp;<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </button>
        </div>
        <table id="lista-itens-venda" class="table table-striped">
            <tr>
                <th>Código</th>
                <th>Produto</th>
                <th>Marca</th>
                <th>Quantidade</th>
                <th>Preço Unitário</th>
                <th>Sub-Total</th>
                <th>Ação</th>
            </tr>
            <tr>
                <td>
                    <div class="form-grupo <c:if test="${errorValidation.codigo eq true}">has-error</c:if>">
                        <input type="text" class="form-control codigo" name="codigo" maxlength="10" placeholder="ID do produto" required pattern="^\d+$" value="<c:out value="${sessionScope.data.codigo[0]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group <c:if test="${errorValidation.produto eq true}">has-error</c:if>">
                        <input type="text" class="form-control produto" name="produto" maxlength="150" placeholder="Nome do produto" required pattern="^([a-zA-Zà-úÀ-Ú0-9])([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.data.produto[0]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" class="form-control marca" name="marca" maxlength="150" placeholder="Marca" required readonly="readonly" value="<c:out value="${sessionScope.data.marca[0]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group <c:if test="${errorValidation.quantidade eq true}">has-error</c:if>">
                        <input type="number" class="form-control quantidade" name="quantidade" maxlength="10" placeholder="Quantidade" min="0" required pattern="^\d+$" value="<c:out value="${sessionScope.data.quantidade[0]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" class="form-control preco-unidade" name="preco-unidade" maxlength="12" placeholder="Preço Unidade" pattern="(\d{1,3}\.)?\d{1,3},\d{2}$" required readonly="readonly" value="<c:out value="${sessionScope.data.precoUnidade[0]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" class="form-control preco-total" name="preco-total" maxlength="12" placeholder="Sub-Total" pattern="(\d{1,3}\.)?\d{1,3},\d{2}$" required readonly="readonly" value="<c:out value="${sessionScope.data.precoTotal[0]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <button type="button" class="btn-excluir btn btn-default">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </button>
                </td>
            </tr>
            <c:set var="i" value="1"></c:set>
            <c:forEach begin="1" end="${sessionScope.data.cont[0]}" varStatus="loop">
            <tr>
                <td>
                    <div class="form-grupo <c:if test="${errorValidation.codigo eq true}">has-error</c:if>">
                        <input type="text" class="form-control codigo" name="codigo" maxlength="10" placeholder="ID do produto" required pattern="^\d+$" value="<c:out value="${sessionScope.data.codigo[i]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group <c:if test="${errorValidation.produto eq true}">has-error</c:if>">
                        <input type="text" class="form-control produto" name="produto" maxlength="150" placeholder="Nome do produto" required pattern="^([a-zA-Zà-úÀ-Ú0-9])([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.data.produto[i]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" class="form-control marca" name="marca" maxlength="150" placeholder="Marca" required readonly="readonly" value="<c:out value="${sessionScope.data.marca[i]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group <c:if test="${errorValidation.quantidade eq true}">has-error</c:if>">
                        <input type="number" class="form-control quantidade" name="quantidade" maxlength="10" placeholder="Quantidade" min="0" required pattern="^\d+$" value="<c:out value="${sessionScope.data.quantidade[i]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" class="form-control preco-unidade" name="preco-unidade" maxlength="12" placeholder="Preço Unidade" pattern="(\d{1,3}\.)?\d{1,3},\d{2}$" required readonly="readonly" value="<c:out value="${sessionScope.data.precoUnidade[i]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" class="form-control preco-total" name="preco-total" maxlength="12" placeholder="Sub-Total" pattern="(\d{1,3}\.)?\d{1,3},\d{2}$" required readonly="readonly" value="<c:out value="${sessionScope.data.precoTotal[i]}"></c:out>"/>
                    </div>
                </td>
                <td>
                    <button type="button" class="btn-excluir btn btn-default">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </button>
                </td>
            </tr>
            <c:set var="i" value="${i + 1}"></c:set>
            </c:forEach>
        <c:remove scope="session" var="data"></c:remove>
        </table>
        <div class="form-group col-md-offset-9 col-md-3">
            <c:if test="${sessionScope.venda.id > 0}">
            <a href="sistema?controller=Venda&action=excluir&id=<c:out value="${sessionScope.venda.id}"></c:out>" class="btn btn-default">
                Excluir&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </a>
            </c:if>
            <c:if test="${empty sessionScope.venda.id || sessionScope.venda.id == 0}">
            <button type="reset" class="btn btn-default">
                Cancelar&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
            </c:if>
            <button type="submit" class="btn btn-default">
                Salvar&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
            </button>
        </div>
    </form>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>