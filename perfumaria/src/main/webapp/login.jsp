<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/layout/header.jsp"/>
            <div class="row">
                <div class="col-md-12 content login"><br><br><br><br><br><br>
                    <div class="col-md-offset-4 col-md-4">
                        <jsp:include page="/WEB-INF/layout/message.jsp"/>
                    </div>
                    <form class="col-md-offset-4 col-md-4" action="sistema?controller=Usuario&action=login" method="post">
                        <div class="form-group col-md-12 <c:if test="${errorValidation.login eq true}">has-error</c:if>">
                            <label class="control-label" for="login">Login</label>
                            <input type="text" class="form-control" id="login" placeholder="Login" name="login" maxlength="50" required pattern="^\w+$">
                        </div>
                        <div class="form-group col-md-12 <c:if test="${errorValidation.senha eq true}">has-error</c:if>">
                            <label class="control-label" for="senha">Senha</label>
                            <input type="password" class="form-control" id="senha" placeholder="Senha" name="senha" maxlength="8" required pattern="^\w+$">
                        </div>
                        <div class="form-group col-md-12">
                            <button type="submit" class="btn btn-default">
                                Entrar&nbsp;&nbsp;<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                            </button>
                        </div>
                    </form>
                </div><!-- content -->
<jsp:include page="/WEB-INF/layout/footer.jsp"/>