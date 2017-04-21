<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>
<div class="col-md-10 content">
    <h2>Relatório de Estoque</h2>
    <form action="#" method="post" class="col-md-12">
       <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="data-nascimento">Data Inicial</label>
            <input type="date" class="form-control" id="data-inicial" name="data-inicial" required>
        </div>
        <div class="form-group col-md-3 <c:if test="${errorValidation eq true}">has-error</c:if>">
            <label class="control-label" for="data-nascimento">Data Final</label>
            <input type="date" class="form-control" id="data-final" name="data-final" required>
        </div>
            <div class="form-group col-md-4">
                <button type="submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </button>
            </div>
        </form>            
        <table class="table table-striped">
            <tr>
                <th>Codigo</th>
                <th>Nome</th>
                <th>Categoria</th>
                <th>Valor de Custo</th>
                <th>Valor de venda</th>
                <th>Estoque</th>
            </tr>
            </thead>
            <tbody>
                <tr><td>012345</td>
                    <td>Jose</td>
                    <td>A</td>
                    <td >25,00</td>
                    <td >36,00</td>
                    <td >3</td>
                </tr>
                <tr><td>654123</td>
                    <td>Luiz</td>
                    <td>B</td>
                    <td>39,00</td>
                    <td >45,00</td>
                    <td >30</td>
                </tr>
                <tr><td>985647</td>
                    <td>Carlos</td>
                    <td>C</td>
                    <td>69,00</td>
                    <td >72,00</td>
                    <td >40</td>
                </tr>
                <tr><td>012345</td>
                    <td>Jose</td>
                    <td>D</td>
                    <td >25,00</td>
                    <td >36,00</td>
                    <td >32</td>
                </tr>
                <tr><td>012345</td>
                    <td>Jose</td>
                    <td>E</td>
                    <td >25,00</td>
                    <td >36,00</td>
                    <td >89</td>
                </tr>
                <tr><td>986354</td>
                    <td>Jose</td>
                    <td>G</td>
                    <td >25,00</td>
                    <td >36,00</td>
                    <td >12</td>
                </tr>
                <tr><td>652318</td>
                    <td>Jose</td>
                    <td>H</td>
                    <td >25,00</td>
                    <td >36,00</td>
                    <td >8</td>
                </tr>
                <tr><td>874512</td>
                    <td>Jose</td>
                    <td>I</td>
                    <td >25,00</td>
                    <td >36,00</td>
                    <td >125</td>                          
            </tbody>
        </table>
    </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>