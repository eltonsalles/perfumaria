<%-- 
    Document   : cadastrar-venda
    Created on : 13/04/2017, 10:32:52
    Author     : Fabiano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Efetuar Venda</h2>
    <form>
        <div class="form-group col-md-6">
            <label for="pesquisar">Pesquisar</label>
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
        </tr>
    </table>
    
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