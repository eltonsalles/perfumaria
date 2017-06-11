<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:array name="lojas" var="loja" items="${requestScope.lojas}">
    <json:object>
        <json:property name="id" value="${loja[0]}"/>
        <json:property name="razaoSocial" value="${loja[1]}"/>
    </json:object>
</json:array>