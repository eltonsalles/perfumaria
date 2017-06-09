<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
    <json:property name="nome" value="${requestScope.cliente.nome}"/>
    <json:property name="id" value="${requestScope.cliente.id}"/>
    <json:property name="cpf" value="${requestScope.cliente.cpf}"/>
</json:object>