<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty sessionScope.alert}">
    <div class="col-md-12">
        <div class="alert <c:out value="${sessionScope.alert}"></c:out>" role="alert">
            <c:out value="${sessionScope.alertMessage}"></c:out>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </div>
</c:if>
<c:remove scope="session" var="alert"></c:remove>
<c:remove scope="session" var="alertMessage"></c:remove>