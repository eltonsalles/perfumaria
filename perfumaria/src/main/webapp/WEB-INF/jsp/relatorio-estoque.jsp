<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
<jsp:include page="/WEB-INF/layout/menu.jsp"/>

<div class="col-md-10 content">
   <h2>Relatório de Estoque</h2><br> 
    <label for="relat-estoque">Data Inicial:</label>
    <input type="date" id="datai" name="datainicial"/>&nbsp;&nbsp;
    <label for="relat-estoque">Data Final:</label>
    <input type="date" id="dataf" name="datafinal"/>&nbsp;
    <button type="submit" class="btn btn-primary">
    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
    </button><br><br>
    <table class="table table-hover" id="tblestoque">
            <thead id="tblHead">
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
    <label for="vativa">Vendas ativas</label> 
    <output type="numer" for="" name="vendaativa" size="20" value="454554"/></output>
    
</div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>