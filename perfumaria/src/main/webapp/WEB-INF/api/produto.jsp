<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
    <json:property name="id" value="${requestScope.itensLoja.produto.id}"/>
    <json:property name="nome" value="${requestScope.itensLoja.produto.nome}"/>
    <json:property name="marca" value="${requestScope.itensLoja.produto.marca}"/>
    <json:property name="valorVenda" value="${requestScope.itensLoja.valorVenda}"/>
    <json:property name="quantidade" value="${requestScope.itensLoja.estoque}"/>
</json:object>