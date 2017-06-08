<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2><c:out value="${sessionScope.funcionario.id > 0 ? 'Alterar' : 'Cadastrar'}"></c:out> Funcionário</h2>
    <jsp:include page="/WEB-INF/layout/message.jsp"/>
    <form id="form-funcionarios" action="sistema?controller=Funcionario&action=<c:out value="${sessionScope.funcionario.id > 0 ? 'editar' : 'novo'}"></c:out>" method="post">
        <div class="col-md-12">
            <div class="row">
            <div class="form-group col-md-3 <c:if test="${errorValidation.loja eq true}">has-error</c:if>">
            <label class="control-label" for="loja">Nome da Loja</label>
            <select class="form-control" id="loja" name="loja" readonly="readonly">
                <option value></option>
                <c:forEach items="${sessionScope.listaLoja}" var="loja">
                    <option value="<c:out value="${loja.id}"></c:out>" <c:if test="${sessionScope.funcionario.loja.id == loja.id}">selected</c:if>><c:out value="${loja.razaoSocial}"></c:out></option>
                </c:forEach>
            </select> 
        </div>
            <div class="form-group col-md-offset-10 status">
            <label>Status</label><br>
            <label class="radio-inline">
                <input type="radio" id="ativo" value="true" name="status" checked required>Ativo
            </label>
            <label class="radio-inline">
                <input type="radio" id="inativo" value="false" name="status" required <c:if test="${sessionScope.funcionario.status eq false}">checked</c:if>>Inativo
            </label>
        </div>
        </div>
        </div>
        <div class="form-group col-md-12">
            <input type="hidden" class="form-control" id="id" name="id" value="<c:out value="${sessionScope.funcionario.id > 0 ? sessionScope.funcionario.id : ''}"></c:out>">
        </div>
        <div class="form-group col-md-6 <c:if test="${errorValidation.nome eq true}">has-error</c:if>">
            <label class="control-label" for="nome">Nome</label>
            <input type="text" class="form-control" id="nome" placeholder="Nome completo" name="nome" maxlength="150" required pattern="^([a-zA-Zà-úÀ-Ú])([a-zA-Zà-úÀ-Ú]|\.|\s)+$" value="<c:out value="${sessionScope.funcionario.nome}"></c:out>">
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.dataNascimento eq true}">has-error</c:if>"> 
            <label class="control-label" for="data-nascimento">Data de Nascimento</label>
            <input type="date" class="form-control" id="data-nascimento" name="data-nascimento" required min="1900-01-01" max="2100-12-31" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${funcionario.dataNascimento}" />">
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.cpf eq true}">has-error</c:if>">
            <label class="control-label" for="cpf">CPF</label>
            <input type="text" class="form-control" id="cpf" placeholder="xxx.xxx.xxx-xx" name="cpf" maxlength="14" required pattern="^\d{3}.\d{3}.\d{3}-\d{2}$" value="<c:out value="${sessionScope.funcionario.cpf}"></c:out>" <c:if test="${sessionScope.funcionario.id > 0}">readonly</c:if>>
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.dataAdmissao eq true}">has-error</c:if>">
            <label class="control-label" for="data-admissao">Data de Admissão</label>
            <input type="date" class="form-control" id="data-admissao" name="data-admissao" required min="1900-01-01" max="2100-12-31" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${funcionario.dataAdmissao}"/>">
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.cargo eq true}">has-error</c:if>">
            <label class="control-label" for="cargo">Cargo</label>
            <select class="form-control" id="cargo" name="cargo" required>
                <c:set var="cargo" value="${sessionScope.funcionario.cargo}"></c:set>
                <option value="">---</option>
                <option value="diretor"<c:if test="${fn:containsIgnoreCase(cargo, 'diretor')}">selected</c:if>>Diretor</option>
                <option value="gerente de backoffice"<c:if test="${fn:containsIgnoreCase(cargo, 'gerente de backoffice')}">selected</c:if>>Gerente de Backoffice</option>
                <option value="gerente de ti"<c:if test="${fn:containsIgnoreCase(cargo, 'gerente de ti')}">selected</c:if>>Gerente de TI</option>
                <option value="gerente de vendas"<c:if test="${fn:containsIgnoreCase(cargo, 'gerente de vendas')}">selected</c:if>>Gerente de Vendas</option>
                <option value="retaguarda"<c:if test="${fn:containsIgnoreCase(cargo, 'retaguarda')}">selected</c:if>>Retaguarda</option>
                <option value="vendedor"<c:if test="${fn:containsIgnoreCase(cargo, 'vendedor')}">selected</c:if>>Vendedor</option>
            </select>
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.estadoCivil eq true}">has-error</c:if>">
            <label class="control-label" for="estado-civil">Estado civil</label>
            <select class="form-control" id="estado-civil" name="estado-civil" required>
            <c:set var="estadoCivil" value="${sessionScope.funcionario.estadoCivil}"></c:set>
                <option value="">---</option>
                <option value="solteiro" <c:if test="${fn:containsIgnoreCase(estadoCivil, 'solteiro')}">selected</c:if>>Solteiro</option>
                <option value="casado" <c:if test="${fn:containsIgnoreCase(estadoCivil, 'casado')}">selected</c:if>>Casado</option>
                <option value="divorciado" <c:if test="${fn:containsIgnoreCase(estadoCivil, 'divorciado')}">selected</c:if>>Divorciado</option>
                <option value="viuvo" <c:if test="${fn:containsIgnoreCase(estadoCivil, 'viúvo')}">selected</c:if>>ViÃºvo</option>
            </select>
        </div>
        <div class="form-group col-md-3 sexo <c:if test="${errorValidation.sexo eq true}">has-error</c:if>">
            <label class="control-label">Sexo</label><br>
            <label class="radio-inline">
                <input type="radio" id="feminino" value="F" name="sexo" required <c:if test="${sessionScope.funcionario.sexo == 'F'.charAt(0)}">checked</c:if>>Feminino
            </label>
            <label class="radio-inline">
                <input type="radio" id="masculino" value="M" name="sexo" required <c:if test="${sessionScope.funcionario.sexo == 'M'.charAt(0)}">checked</c:if>>Masculino
            </label>
        </div>


        <div class="form-group col-md-6 <c:if test="${errorValidation.email eq true}">has-error</c:if>">
            <label class="control-label" for="email">E-mail</label>
            <input type="email" class="form-control" id="email" placeholder="email@exemplo.com.br" name="email" maxlength="150" required value="<c:out value="${sessionScope.funcionario.email}"></c:out>">
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation.celular eq true}">has-error</c:if>">
            <label class="control-label" for="celular">Celular</label>
            <input type="tel" class="form-control" id="celular" placeholder="(xx) xxxxx-xxxx" name="celular" maxlength="15" required pattern="\(\d{2}\) \d{4,5}-\d{4}$" value="<c:out value="${sessionScope.funcionario.celular}"></c:out>">
        </div>                                               
        <div class="form-group col-md-3 <c:if test="${errorValidation.telefone eq true}">has-error</c:if>">
            <label class="control-label" for="telefone">Telefone</label>
            <input type="tel" class="form-control" id="telefone" placeholder="(xx) xxxx-xxxx" name="telefone" maxlength="14" required pattern="\(\d{2}\) \d{4,5}-\d{4}$" value="<c:out value="${sessionScope.funcionario.telefone}"></c:out>">
        </div>
        <div class="col-md-12">
            <div class="row">
                <div class="form-group col-md-2 <c:if test="${errorValidation.cep eq true}">has-error</c:if>">
                    <label class="control-label" for="cep">CEP</label>
                    <input type="text" class="form-control" id="cep" placeholder="xxxxx-xxx" name="cep" maxlength="9" required pattern="\d{5}-\d{3}$" value="<c:out value="${sessionScope.funcionario.cep}"></c:out>">
                </div>
                <div class="form-group col-md-4 <c:if test="${errorValidation.logradouro eq true}">has-error</c:if>">
                    <label class="control-label" for="logradouro">Logradouro</label>
                    <input type="text" class="form-control" id="logradouro" placeholder="Rua, Avenida..." name="logradouro" maxlength="150" required  pattern="^([a-zA-Zà-úÀ-Ú0-9])([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.funcionario.logradouro}"></c:out>">
                </div>
                <div class="form-group col-md-2 <c:if test="${errorValidation.numero eq true}">has-error</c:if>">
                    <label class="control-label" for="numero">Número</label>
                    <input type="text" class="form-control" id="numero" placeholder="xxx" name="numero" maxlength="10" required pattern="^\d+$" value="<c:out value="${sessionScope.funcionario.numero}"></c:out>">
                </div>
                <div class="form-group col-md-4 <c:if test="${errorValidation.complemento eq true}">has-error</c:if>">
                    <label for="complemento">Complemento</label>
                    <input type="text" class="form-control" id="complemento" placeholder="Bloco, sala..." name="complemento" maxlength="50" pattern="^([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.funcionario.complemento}"></c:out>">
                </div>
                <div class="form-group col-md-4 <c:if test="${errorValidation.bairro eq true}">has-error</c:if>">
                    <label class="control-label" for="bairro">Bairro</label>
                    <input type="text" class="form-control" id="bairro" name="bairro" maxlength="50" required pattern="^([a-zA-Zà-úÀ-Ú0-9])([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$" value="<c:out value="${sessionScope.funcionario.bairro}"></c:out>">
                </div>
                <div class="form-group col-md-4 <c:if test="${errorValidation.cidade eq true}">has-error</c:if>">
                    <label class="control-label" for="cidade">Cidade</label>
                    <input type="text" class="form-control" id="cidade" name="cidade" maxlength="50" required pattern="^([a-zA-Zà-úÀ-Ú])([a-zA-Zà-úÀ-Ú]|\.|-|\s)+$" value="<c:out value="${sessionScope.funcionario.cidade}"></c:out>">
                </div>
                <div class="form-group col-md-2 <c:if test="${errorValidation.uf eq true}">has-error</c:if>">
                    <label class="control-label" for="uf">UF</label>
                    <select class="form-control" id="uf" name="uf" required>
                        <c:set var="uf" value="${sessionScope.funcionario.uf}"></c:set>
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
            <c:if test="${sessionScope.funcionario.id > 0}">
            <a href="sistema?controller=Funcionario&action=excluir&id=<c:out value="${sessionScope.funcionario.id}"></c:out>" class="btn btn-default">
                    Excluir&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </a>
            </c:if>
            <c:if test="${empty sessionScope.funcionario.id || sessionScope.funcionario.id == 0}">
            <button type="reset" class="btn btn-default">
                Cancelar&nbsp;&nbsp;<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
            </c:if>
            <button type="submit" class="btn btn-default">
            <c:out value="${sessionScope.funcionario.id > 0 ? 'Alterar' : 'Salvar'}"></c:out>&nbsp;&nbsp;<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
            </button>
        </div>        
    </form>
    <c:remove scope="session" var="funcionario"></c:remove>
    <c:remove scope="session" var="errorValidation"></c:remove>
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>