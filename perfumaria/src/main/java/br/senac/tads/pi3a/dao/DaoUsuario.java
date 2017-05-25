/*
 * The MIT License
 *
 * Copyright 2017 joao.mihamasaki.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.senac.tads.pi3a.dao;

import br.senac.tads.pi3a.ado.Criteria;
import br.senac.tads.pi3a.ado.Expression;
import br.senac.tads.pi3a.ado.Filter;
import br.senac.tads.pi3a.ado.SqlSelect;
import br.senac.tads.pi3a.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author joao.mihamasaki
 */
public class DaoUsuario extends AbstractDao{
    public DaoUsuario(Connection conn){
        super(conn);
    }
    
    public DaoUsuario(Connection conn, Usuario model){
        super(conn, model);
    }
    
    /**
     * Traz um usuário confirmando o login e a senha
     * 
     * @param login
     * @param senha
     * @return 
     */
    public Usuario confirmarUsuario(String login, String senha) {
        try {
            Usuario usuario = new Usuario();
            List list = this.findAll(usuario, "login", "=", login);
            
            // O login no banco de dados é único
            if (list.size() == 1) {
                usuario = (Usuario) list.get(0);
                
                if (BCrypt.checkpw(senha, usuario.getSenha())) {
                    return usuario;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return null;
    }
    
    /**
     * Verifica se o usuário tem permissão para acessar o controller e a action
     * 
     * @param controller
     * @param action
     * @param nivelAcesso
     * @return 
     */
    public boolean permissao(String controller, String action,
            String nivelAcesso) {
        try {
            PreparedStatement stmt;
            ResultSet resultSet;
            
            Criteria criteria = new Criteria();
            criteria.add(new Filter("UPPER(controller)", "=", "?"),
                    Expression.AND_OPERATOR);
            criteria.add(new Filter("UPPER(action)", "=", "?"),
                    Expression.AND_OPERATOR);
            criteria.add(new Filter("UPPER(nivel_acesso)", "=", "?"),
                    Expression.AND_OPERATOR);
            
            SqlSelect sql = new SqlSelect();
            sql.setEntity("permissoes");
            sql.addColumn("*");
            sql.setCriteria(criteria);
            
            stmt = this.getConnection().prepareStatement(sql.getInstruction());
            stmt.setString(1, controller.toUpperCase());
            stmt.setString(2, action.toUpperCase());
            stmt.setString(3, nivelAcesso.toUpperCase());
            
            resultSet = stmt.executeQuery();
            
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return false;
    }
}
