<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Cadastrar Venda</h2>
    <form>
        <div class="form-group col-md-6 <c:if test="${errorValidation eq true}">has-error</c:if>">
                <label class="control-label" for="pesquisar">Pesquisar</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="pesquisar" placeholder="Digite nome ou código" name="pesquisar">
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        </button>
                    </span>
                </div>
            </div>
        </form>
        <div class="form-group col-md-5">
            <label for="nome">Nome</label>
            <input type="text" class="form-control" id="nome" name="nome">
        </div>
        <div class="form-group col-md-2 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="cpf">CPF</label>
            <input type="text" class="form-control" id="cpf" name="cpf">
        </div>
        <table class="table table-striped">
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Quantidade</th>
                <th>Preço Unitário</th>
                <th>Sub-Total</th>
            </tr>
            <tr>
                <td>1</td>
                <td>Exemplo </td>
                <td>00000</td>
                <td>R$ 00,00</td>
                <td>
                    R$: 00,00
                </td>
                <td>
                    <a href="#" class="btn btn-default" role="button" title="Editar">
                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                    </a>
                </td>
            </tr>
        </table>
        <div class="input-group">
            <label for="Total">Total</label>
            <input type="text" class="form-control" id="total"  name="Total">
        </div>
        <div class="form-group col-md-offset-7 col-md-5">
            <a href="#" class="btn btn-default">
                Excluir&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </a>
            <button type="reset" class="btn btn-default">
                Cancelar&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
            <button type="reset" class="btn btn-default">
                Carrinho&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
            <button type="submit" class="btn btn-default">
                Salvar&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
            </button>
        </div>
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>