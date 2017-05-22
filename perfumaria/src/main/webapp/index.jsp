<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty sessionScope.usuarioLogado}">
    <c:redirect url="login.jsp"></c:redirect>
</c:if>
<jsp:include page="/WEB-INF/jsp/tela-inicio.jsp"/>
