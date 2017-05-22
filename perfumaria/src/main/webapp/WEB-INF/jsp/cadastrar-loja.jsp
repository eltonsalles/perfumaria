<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Cadastrar Loja</h2>
    <form action="sistema?controller=Loja&action=novo" method="post">
        <div class="form-group col-md-12">
            <input type="hidden" class="form-control" id="id" name="id">
        </div>

        <div class="form-group col-md-5">
            <label for="razao-social">Razão Social</label>
            <input type="text" class="form-control" id="nome" placeholder="Razão social" name="razao-social" maxlength="150" required>
        </div>
        <div class="form-group col-md-3 <c:if test="${listaErro.cnpj eq true}">has-error</c:if>">
                <label class="control-label" for="cnpj">CNPJ</label>
                <input type="text" class="form-control" id="cnpj" placeholder="xx.xxx.xxx/xxxx-xx" name="cnpj" maxlength="18" required pattern="[0-9]{2}\.?[0-9]{3}\.?[0-9]{3}\/?[0-9]{4}\-?[0-9]{2}$">
            </div>
            <div class="form-group col-md-3 status">
                <label>Status</label><br>
                <label class="radio-inline">
                    <input type="radio" id="ativo" value="true" name="status" checked required>Ativo
                </label>
                <label class="radio-inline">
                    <input type="radio" id="inativo" value="false" name="status" required>Inativo
                </label>
            </div>
            <div class="form-group col-md-5">
                <label for="nome-fantasia">Nome Fantasia</label>
                <input type="text" class="form-control" id="nome-fantasia" placeholder="Nome Fantasia" name="nome-fantasia" maxlength="150" required pattern="[a-z\s]{1,150}">
            </div>
            <div class="form-group col-md-3">
                <label for="telefone">Telefone</label>
                <input type="tel" class="form-control" id="telefone" placeholder="(xx) xxxxx-xxxx" name="telefone" maxlength="15" required pattern="\([0-9]{2}\) [0-9]{4}-[0-9]{4}$">
            </div>
            <div class="form-group col-md-5">
                <label for="email">E-mail</label>
                <input type="email" class="form-control" id="email" placeholder="email@exemplo.com.br" name="email" maxlength="150" required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$">
            </div>
            <div class="form-group col-md-6">
                <label for="logradouro">Logradouro</label>
                <input type="text" class="form-control" id="logradouro" placeholder="Rua, Avenida ..." name="logradouro" maxlength="150" required  pattern="[a-z\s][0-9]{1,150}+$">
            </div>
            <div class="form-group col-md-2">
                <label for="numero">Número</label>
                <input type="text" class="form-control" id="numero" placeholder="xxx" name="numero" maxlength="10" required pattern="[0-9]{1,10}+$">
            </div>
            <div class="form-group col-md-4">
                <label for="complemento">Complemento</label>
                <input type="text" class="form-control" id="complemento" placeholder="Bloco, Sala ..." name="complemento" maxlength="50" pattern="[a-z\s][0-9]{1,50}+$">
            </div>
            <div class="form-group col-md-4">
                <label for="bairro">Bairro</label>
                <input type="text" class="form-control" id="bairro" name="bairro" maxlength="50" required pattern="[a-z\s][0-9]{1,50}+$">
            </div>
            <div class="form-group col-md-4">
                <label for="cidade">Cidade</label>
                <input type="text" class="form-control" id="cidade" name="cidade" maxlength="50" required pattern="[a-z\s][0-9]{1,50}+$">
            </div>
            <div class="form-group col-md-2">
                <label for="uf">UF</label>
                <select class="form-control" id="uf" name="uf">
                    <option value="">---</option>
                    <option value="AC">AC</option>
                    <option value="AL">AL</option>
                    <option value="AP">AP</option>
                    <option value="AM">AM</option>
                    <option value="BA">BA</option>
                    <option value="CE">CE</option>
                    <option value="DF">DF</option>
                    <option value="ES">ES</option>
                    <option value="GO">GO</option>
                    <option value="MA">MA</option>
                    <option value="MT">MT</option>
                    <option value="MS">MS</option>
                    <option value="MG">MG</option>
                    <option value="PR">PR</option>
                    <option value="PB">PB</option>
                    <option value="PA">PA</option>
                    <option value="PE">PE</option>
                    <option value="PI">PI</option>
                    <option value="RJ">RJ</option>
                    <option value="RN">RN</option>
                    <option value="RS">RS</option>
                    <option value="RO">RO</option>
                    <option value="RR">RR</option>
                    <option value="SC">SC</option>
                    <option value="SE">SE</option>
                    <option value="SP">SP</option>
                    <option value="TO">TO</option>
                </select>
            </div>
            <div class="form-group col-md-2">
                <label for="cep">CEP</label>
                <input type="text" class="form-control" id="cep" placeholder="xxxxx-xxx" name="cep" maxlength="9" required pattern="[0-9]{5}-[0-9]{3}$">
            </div>
            <div class="form-group col-md-offset-8 col-md-4">
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