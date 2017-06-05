<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
    <json:property name="nome" value="${requestScope.itensLoja.produto.nome}"/>
    <json:property name="marca" value="${requestScope.itensLoja.produto.marca}"/>
    <json:property name="valorVenda" value="${requestScope.itensLoja.valorVenda}"/>
</json:object>