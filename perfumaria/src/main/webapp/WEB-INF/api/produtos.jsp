<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:array name="produtos" var="produto" items="${sessionScope.produtos}">
    <json:object>
        <json:property name="nome" value="${produto.nome}"/>
        <json:property name="marca" value="${produto.marca}"/>
        <json:property name="categoria" value="${produto.categoria}"/>
        <json:property name="valorUnidadeMedida" value="${produto.valorUnidadeMedida}"/>
        <json:property name="genero" value="${produto.genero}"/>
        <json:property name="descricao" value="${produto.descricao}"/>
    </json:object>
</json:array>