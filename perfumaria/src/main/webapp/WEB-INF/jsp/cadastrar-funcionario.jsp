<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Cadastrar Funcionário</h2>
    <form action="sistema?controller=Funcionario&action=novo" method="post">
        <div class="form-group col-md-12">
            <input type="hidden" class="form-control" id="id" name="id">
        </div>
        <div class="form-group col-md-5 <c:if test="${errorValidation.nome eq true}">has-error</c:if>">
                <label class="control-label" for="nome">Nome</label>
                <input type="text" class="form-control" id="nome" placeholder="Nome completo" name="nome" maxlength="150" required pattern="[a-zA-Zà-úÀ-Ú ]{1,150}">
            </div>
            <div class="form-group col-md-3 <c:if test="${errorValidation.dataNascimento eq true}">has-error</c:if>">
                <label class="control-label" for="data-nascimento">Data de Nascimento</label>
                <input type="date" class="form-control" id="data-nascimento" name="data-nascimento" required pattern="[0-9]{2}\/[0-9]{2}\/[0-9]{4}$" min="1900-01-01" max="2100-12-31">
            </div>
            <div class="form-group col-md-2 <c:if test="${errorValidation.cpf eq true}">has-error</c:if>">
                <label class="control-label" for="cpf">CPF</label>
                <input type="text" class="form-control" id="cpf" placeholder="xxx.xxx.xxx-xx" name="cpf" maxlength="14" required pattern="^\d{3}.\d{3}.\d{3}-\d{2}$">
            </div>
            <div class="form-group col-md-2 status">
                <label>Status</label><br>
                <label class="radio-inline">
                    <input type="radio" id="ativo" value="true" name="status" checked required>Ativo
                </label>
                <label class="radio-inline">
                    <input type="radio" id="inativo" value="false" name="status" required>Inativo
                </label>
            </div>
            <div class="form-group col-md-3 <c:if test="${errorValidation.dataAdmissao eq true}">has-error</c:if>">
                <label class="control-label" for="data-admissao">Data de Admissão</label>
                <input type="date" class="form-control" id="data-admissao" name="data-admissao" required pattern="[0-9]{2}\/[0-9]{2}\/[0-9]{4}$" min="1900-01-01" max="2100-12-31">
            </div>
            <div class="form-group col-md-3 <c:if test="${errorValidation.cargo eq true}">has-error</c:if>">
                <label class="control-label" for="cargo">Cargo</label>
                <select class="form-control" id="cargo" name="cargo" required>
                    <option value="">---</option>
                    <option value="diretor">Diretor</option>
                    <option value="gerente de backoffice">Gerente De Backoffice</option>
                    <option value="gerente de ti">Gerente De Ti</option>
                    <option value="gerente de vendas">Gerente De Vendas</option>
                    <option value="retaguarda">Retaguarda</option>
                    <option value="vendedor">Vendedor</option>
                </select>
            </div>
            <div class="form-group col-md-3 <c:if test="${errorValidation.estadoCivil eq true}">has-error</c:if>">
                <label class="control-label" for="estado-civil">Estado Civil</label>
                <select class="form-control" id="estado-civil" name="estado-civil" required>
                    <option value="">---</option>
                    <option value="solteiro">Solteiro</option>
                    <option value="casado">Casado</option>
                    <option value="divorciado">Divorciado</option>
                    <option value="viúvo">Viúvo</option>
                </select>
            </div>
            <div class="form-group col-md-3 sexo <c:if test="${errorValidation.sexo eq true}">has-error</c:if>">
                <label>Sexo</label><br>
                <label class="radio-inline">
                    <input type="radio" id="feminino" value="F" name="sexo" required>Feminino
                </label>
                <label class="radio-inline">
                    <input type="radio" id="masculino" value="M" name="sexo" required>Masculino
                </label>
            </div>
            <div class="form-group col-md-3 <c:if test="${errorValidation.celular eq true}">has-error</c:if>">
                <label class="control-label" for="celular">Celular</label>
                <input type="tel" class="form-control" id="celular" placeholder="(xx) xxxxx-xxxx" name="celular" maxlength="15" required pattern="\([0-9]{2}\) [0-9]{4,5}-[0-9]{4}$">
            </div>
            <div class="form-group col-md-3 <c:if test="${errorValidation.telefone eq true}">has-error</c:if>">
                <label class="control-label" for="telefone">Telefone</label>
                <input type="tel" class="form-control" id="telefone" placeholder="(xx) xxxxx-xxxx" name="telefone" maxlength="14" required pattern="\([0-9]{2}\) [0-9]{4}-[0-9]{4}$">
            </div>
            <div class="form-group col-md-6 <c:if test="${errorValidation.email eq true}">has-error</c:if>">
                <label class="control-label" for="email">E-mail</label>
                <input type="email" class="form-control" id="email" placeholder="email@exemplo.com.br" name="email" maxlength="150" required  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$">
            </div>
            <div class="form-group col-md-6 <c:if test="${errorValidation.logradouro eq true}">has-error</c:if>">
                <label class="control-label" for="logradouro">Logradouro</label>
                <input type="text" class="form-control" id="logradouro" placeholder="Rua, Avenida ..." name="logradouro" maxlength="150" required pattern="[a-z\s][0-9]{1,150}+$">
            </div>
            <div class="form-group col-md-2 <c:if test="${errorValidation.numero eq true}">has-error</c:if>">
                <label class="control-label" for="numero">Número</label>
                <input type="text" class="form-control" id="numero" placeholder="xxx" name="numero" maxlength="10" required pattern="[0-9]{1,10}+$">
            </div>
            <div class="form-group col-md-4">
                <label class="control-label" for="complemento">Complemento</label>
                <input type="text" class="form-control" id="complemento" placeholder="Bloco, Sala ..." name="complemento" maxlength="50">
            </div>
            <div class="form-group col-md-4 <c:if test="${errorValidation.bairro eq true}">has-error</c:if>">
                <label class="control-label" for="bairro">Bairro</label>
                <input type="text" class="form-control" id="bairro" name="bairro" maxlength="50" maxlength="50" required pattern="[a-z\s][0-9]{1,50}+$">
            </div>
            <div class="form-group col-md-4 <c:if test="${errorValidation.cidade eq true}">has-error</c:if>">
                <label class="control-label" for="cidade">Cidade</label>
                <input type="text" class="form-control" id="cidade" name="cidade" maxlength="50" required pattern="[a-z\s]{1,50}+$">
            </div>
            <div class="form-group col-md-2 <c:if test="${errorValidation.uf eq true}">has-error</c:if>">
                <label class="control-label" for="uf">UF</label>
                <select class="form-control" id="uf" name="uf" required>
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
            <div class="form-group col-md-2 <c:if test="${errorValidation.cep eq true}">has-error</c:if>">
                <label class="control-label" for="cep">CEP</label>
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
