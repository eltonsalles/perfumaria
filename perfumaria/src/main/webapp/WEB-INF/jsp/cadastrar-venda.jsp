<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>

<div class="col-md-10 content">

    <h2>Cadastrar Venda</h2>
    <form>
        <div class="form-group col-sm-9<c:if test="${errorValidation.pesquisar eq true}">has-error</c:if>">



                <div class="form-group col-md-2">
                    <label class="control-label" for="cpf">CPF</label>
                    <input type="text" placeholder="xxx.xxx.xxx-xx" pattern="^\d{3}.\d{3}.\d{3}-\d{2}$" class="form-control" id="cpf" name="cpf" maxlength="11" required/>
                </div>
                <div class="form-group col-xs-8 col-sm-4">
                    <label for="nome">Nome</label>
                    <input type="text" value="${requestScope.nomeDaVariavel}" class="form-control" id="nome" name="nome" disabled="true"/>
            </div>


        </div>

        <table id="tblvenda" class="table table-striped">
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Marca</th>
                <th>Quantidade</th>
                <th>Preço Unitário</th>
                <th>Sub-Total</th>
                <th>Ação</th>
            </tr>

            <tr id="a1">
                <td><input type="text" style="border:0;" name="codigo" maxlength="150" placeholder="ID do produto"pattern="[0-9]+$"required/></td>
                <td><input type="text" style="border:0;" name="produto" maxlength="150" placeholder="Nome do produto"required/></td>
                <td><input type="text" style="border:0;" name="marca" maxlength="50" placeholder="Marca"required/></td>
                <td><input type="number" style="border:0;" name="quantidade" maxlength="3" placeholder="Quantidade" pattern="[0-9]+$"required/></td>
                <td><input type="text" style="border:0;" name="preco-unidade" maxlength="15" placeholder="Preço Unidade" pattern="([0-9]{1,3}\.)?[0-9]{1,3},[0-9]{2}$"required/></td>
                <td><input type="text" style="border:0;" name="preco-total" maxlength="15" placeholder="Sub-Total" pattern="([0-9]{1,3}\.)?[0-9]{1,3},[0-9]{2}$"required/></td>
                <td>
                    <a href="#" class="btn-excluir btn btn-default" role="button" title="Remover">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </a>
                </td>
            </tr>
            
            

        </table>        



        <div class="form-group col-md-offset-7 col-md-2">
            <label for="Total">Total</label>
            <input type="text" class="form-control" id="total" name="Total" disabled="true">

        </div>
        <div class="form-group col-md-offset-7 col-md-5">
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
            <input id="add-linha" type="button" value="Inserir linha"class="btn btn-default col-md-2" />
    </form>
    
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>