<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/layout/header.jsp"/>


<div class="col-md-10 content">
   
    
    
    
    <form action="#" method="post" class="col-md-4" id="formlogin">
        
        <div class="form-group col-md-12">
            <input type="hidden" class="form-control" id="id" name="id">
        </div>
        
        <div class="form-group col-md-12" id="loginuser">
            <h2>Login de Usuário</h2>
            <input type="text" class="form-control" id="login" placeholder="Informe seu login" name="login" maxlength="50" required>
        </div>
        <div class="form-group col-md-12" id="senhauser">
            <h2>Senha</h2>
            <input type="password" class="form-control" id="senha" placeholder="informe sua senha" name="senha" maxlength="8" required>
            
        </div><br>
        
        
        <div class="form-group col-md-12" id="">
            
            <input class="btn btn-default" type="reset" name="limpar" id="limparbtn" value="Limpar"/>
            <input class="btn btn-primary" type="submit" value="Entrar"><br><br>
            <span class="glyphicon glyphicon-log-out" aria-hidden="true"  ></span>&nbsp;Esqueceu sua senha?
            
            
                      
        </div>
    </form>
    
</div><!-- content -->

<jsp:include page="/WEB-INF/layout/footer.jsp"/>