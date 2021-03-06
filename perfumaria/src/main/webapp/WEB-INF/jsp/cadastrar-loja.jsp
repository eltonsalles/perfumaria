<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2><c:out value="${sessionScope.loja.id > 0 ? 'Alterar' : 'Cadastrar'}"></c:out> Loja</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form action="sistema?controller=Loja&action=<c:out value="${sessionScope.loja.id > 0 ? 'editar' : 'novo'}"></c:out>" method="post">
            <div class="form-group col-md-12">
                <input type="hidden" class="form-control" id="id" name="id" value="<c:out value="${sessionScope.loja.id > 0 ? sessionScope.loja.id : ''}"></c:out>">
            </div>
            <div class="form-group col-md-5 <c:if test="${errorValidation.razaoSocial eq true}">has-error</c:if>">
                <label class="control-label" for="razao-social">Raz�o Social</label>
                <input type="text" class="form-control" id="nome" placeholder="Raz�o social" name="razao-social" maxlength="150" required pattern="^([a-zA-Z�-��-�0-9])([a-zA-Z�-��-�0-9]|\.|\s)+$" value="<c:out value="${sessionScope.loja.razaoSocial}"></c:out>">
            </div>
            <div class="form-group col-md-3 <c:if test="${errorValidation.cnpj eq true}">has-error</c:if>">
                <label class="control-label" for="cnpj">CNPJ</label>
                <input type="text" class="form-control" id="cnpj" placeholder="xx.xxx.xxx/xxxx-xx" name="cnpj" maxlength="18" required pattern="[0-9]{2}\.?[0-9]{3}\.?[0-9]{3}\/?[0-9]{4}\-?[0-9]{2}$" value="<c:out value="${sessionScope.loja.cnpj}"></c:out>" <c:if test="${sessionScope.loja.id > 0}">readonly</c:if>>
            </div>
            <div class="form-group col-md-3 status">
                <label>Status</label><br>
                <label class="radio-inline">
                    <input type="radio" id="ativo" value="true" name="status" checked required>Ativo
                </label>
                <label class="radio-inline">
                    <input type="radio" id="inativo" value="false" name="status" required <c:if test="${sessionScope.loja.status eq false}">checked</c:if>>Inativo
                </label> 
            </div>
            <div class="form-group col-md-5 <c:if test="${errorValidation.nomeFantasia eq true}">has-error</c:if>">
                <label class="control-label" for="nome-fantasia">Nome Fantasia</label>
                <input type="text" class="form-control" id="nome-fantasia" placeholder="Nome Fantasia" name="nome-fantasia" maxlength="150" required pattern="^([a-zA-Z�-��-�0-9])([a-zA-Z�-��-�0-9]|\.|\s)+$" value="<c:out value="${sessionScope.loja.razaoSocial}"></c:out>">
            </div>
            <div class="form-group col-md-3 <c:if test="${errorValidation.telefone eq true}">has-error</c:if>">
                <label class="control-label" for="telefone">Telefone</label>
                <input type="tel" class="form-control" id="telefone" placeholder="(xx) xxxxx-xxxx" name="telefone" maxlength="15" required pattern="\(\d{2}\) \d{4,5}-\d{4}$" value="<c:out value="${sessionScope.loja.telefone}"></c:out>">
            </div>
            <div class="form-group col-md-5 <c:if test="${errorValidation.email eq true}">has-error</c:if>">
                <label class="control-label" for="email">E-mail</label>
                <input type="email" class="form-control" id="email" placeholder="email@exemplo.com.br" name="email" maxlength="150" required value="<c:out value="${sessionScope.loja.email}"></c:out>">
            </div>
            <div class="col-md-12">
            <div class="row">
                <div class="form-group col-md-2 <c:if test="${errorValidation.cep eq true}">has-error</c:if>">
                    <label class="control-label" for="cep">CEP</label>
                    <input type="text" class="form-control" id="cep" placeholder="xxxxx-xxx" name="cep" maxlength="9" required pattern="\d{5}-\d{3}$" value="<c:out value="${sessionScope.loja.cep}"></c:out>">
                </div>
                <div class="form-group col-md-5 <c:if test="${errorValidation.logradouro eq true}">has-error</c:if>">
                    <label class="control-label" for="logradouro">Logradouro</label>
                    <input type="text" class="form-control" id="logradouro" placeholder="Rua, Avenida..." name="logradouro" maxlength="150" required  pattern="^([a-zA-Z�-��-�0-9])([a-zA-Z�-��-�0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.loja.logradouro}"></c:out>">
                </div>
                <div class="form-group col-md-2 <c:if test="${errorValidation.numero eq true}">has-error</c:if>">
                    <label class="control-label" for="numero">N�mero</label>
                    <input type="text" class="form-control" id="numero" placeholder="xxx" name="numero" maxlength="10" required pattern="^\d+$" value="<c:out value="${sessionScope.loja.numero}"></c:out>">
                </div>
                <div class="form-group col-md-3 <c:if test="${errorValidation.complemento eq true}">has-error</c:if>">
                    <label class="control-label" for="complemento">Complemento</label>
                    <input type="text" class="form-control" id="complemento" placeholder="Bloco, sala..." name="complemento" maxlength="50" pattern="^([a-zA-Z�-��-�0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.loja.complemento}"></c:out>">
                </div>
                <div class="form-group col-md-4 <c:if test="${errorValidation.bairro eq true}">has-error</c:if>">
                    <label class="control-label" for="bairro">Bairro</label>
                    <input type="text" class="form-control" id="bairro" name="bairro" maxlength="50" required pattern="^([a-zA-Z�-��-�0-9])([a-zA-Z�-��-�0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.loja.bairro}"></c:out>">
                </div>
                <div class="form-group col-md-4 <c:if test="${errorValidation.cidade eq true}">has-error</c:if>">
                    <label class="control-label" for="cidade">Cidade</label>
                    <input type="text" class="form-control" id="cidade" name="cidade" maxlength="50" required pattern="^([a-zA-Z�-��-�])([a-zA-Z�-��-�]|\.|-|\s)+$" value="<c:out value="${sessionScope.loja.cidade}"></c:out>">
                </div>
                <div class="form-group col-md-2 <c:if test="${errorValidation.uf eq true}">has-error</c:if>">
                    <label class="control-label" for="uf">UF</label>
                    <select class="form-control" id="uf" name="uf" required>
                        <c:set var="uf" value="${sessionScope.loja.uf}"></c:set>
                        <option value="">---</option>
                        <option value="AC" <c:if test="${fn:containsIgnoreCase(uf, 'ac')}">selected</c:if>>AC</option>
                        <option value="AL" <c:if test="${fn:containsIgnoreCase(uf, 'al')}">selected</c:if>>AL</option>
                        <option value="AP" <c:if test="${fn:containsIgnoreCase(uf, 'ap')}">selected</c:if>>AP</option>
                        <option value="AM" <c:if test="${fn:containsIgnoreCase(uf, 'am')}">selected</c:if>>AM</option>
                        <option value="BA" <c:if test="${fn:containsIgnoreCase(uf, 'ba')}">selected</c:if>>BA</option>
                        <option value="CE" <c:if test="${fn:containsIgnoreCase(uf, 'ce')}">selected</c:if>>CE</option>
                        <option value="DF" <c:if test="${fn:containsIgnoreCase(uf, 'df')}">selected</c:if>>DF</option>
                        <option value="ES" <c:if test="${fn:containsIgnoreCase(uf, 'es')}">selected</c:if>>ES</option>
                        <option value="GO" <c:if test="${fn:containsIgnoreCase(uf, 'go')}">selected</c:if>>GO</option>
                        <option value="MA" <c:if test="${fn:containsIgnoreCase(uf, 'ma')}">selected</c:if>>MA</option>
                        <option value="MT" <c:if test="${fn:containsIgnoreCase(uf, 'mt')}">selected</c:if>>MT</option>
                        <option value="MS" <c:if test="${fn:containsIgnoreCase(uf, 'ms')}">selected</c:if>>MS</option>
                        <option value="MG" <c:if test="${fn:containsIgnoreCase(uf, 'mg')}">selected</c:if>>MG</option>
                        <option value="PR" <c:if test="${fn:containsIgnoreCase(uf, 'pr')}">selected</c:if>>PR</option>
                        <option value="PB" <c:if test="${fn:containsIgnoreCase(uf, 'pb')}">selected</c:if>>PB</option>
                        <option value="PA" <c:if test="${fn:containsIgnoreCase(uf, 'pa')}">selected</c:if>>PA</option>
                        <option value="PE" <c:if test="${fn:containsIgnoreCase(uf, 'pe')}">selected</c:if>>PE</option>
                        <option value="PI" <c:if test="${fn:containsIgnoreCase(uf, 'pi')}">selected</c:if>>PI</option>
                        <option value="RJ" <c:if test="${fn:containsIgnoreCase(uf, 'rj')}">selected</c:if>>RJ</option>
                        <option value="RN" <c:if test="${fn:containsIgnoreCase(uf, 'rn')}">selected</c:if>>RN</option>
                        <option value="RS" <c:if test="${fn:containsIgnoreCase(uf, 'rs')}">selected</c:if>>RS</option>
                        <option value="RO" <c:if test="${fn:containsIgnoreCase(uf, 'ro')}">selected</c:if>>RO</option>
                        <option value="RR" <c:if test="${fn:containsIgnoreCase(uf, 'rr')}">selected</c:if>>RR</option>
                        <option value="SC" <c:if test="${fn:containsIgnoreCase(uf, 'sc')}">selected</c:if>>SC</option>
                        <option value="SE" <c:if test="${fn:containsIgnoreCase(uf, 'se')}">selected</c:if>>SE</option>
                        <option value="SP" <c:if test="${fn:containsIgnoreCase(uf, 'sp')}">selected</c:if>>SP</option>
                        <option value="TO" <c:if test="${fn:containsIgnoreCase(uf, 'to')}">selected</c:if>>TO</option>
                    </select>
                </div>
            </div>
        </div>
            <div class="form-group col-md-offset-8 col-md-4">
            <c:if test="${sessionScope.loja.id > 0}">
                <a href="sistema?controller=Loja&action=excluir&id=<c:out value="${sessionScope.loja.id}"></c:out>" class="btn btn-default">
                        Excluir&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>
            </c:if>
            <c:if test="${empty sessionScope.loja.id || sessionScope.loja.id == 0}">    
                <button type="reset" class="btn btn-default">
                    Cancelar&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                </button>
            </c:if>    
            <button type="submit" class="btn btn-default">
                <c:out value="${sessionScope.loja.id > 0 ? 'Alterar' : 'Salvar'}"></c:out>&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
            </button>
            </div>
        </form>
        <c:remove scope="session" var="loja"></c:remove>
        <c:remove scope="session" var="errorValidation"></c:remove>        
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/> 