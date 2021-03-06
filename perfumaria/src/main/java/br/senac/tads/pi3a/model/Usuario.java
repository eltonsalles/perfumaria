/*
 * The MIT License
 *
 * Copyright 2017 Daniel Freitas.
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
package br.senac.tads.pi3a.model;

import br.senac.tads.pi3a.annotation.Columm;
import br.senac.tads.pi3a.annotation.ForeignKey;
import br.senac.tads.pi3a.annotation.Table;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Douglas Oliveira
 */
@Table(name = "usuario")
public class Usuario extends Model {
    @Columm(name = "login")
    private String login;

    @Columm(name = "senha")
    private String senha;

    @ForeignKey(name = "funcionario_id", referenced = "Funcionario", referencedName = "id")
    private Funcionario funcionario;
    
    @ForeignKey(name = "nivel_usuario_id", referenced = "NivelUsuario", referencedName = "id")
    private NivelUsuario nivelUsuario;

   
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        // Se a senha for menor ou igual a 8 significa que está descriptograda
        if (senha.length() <= 8) {
            this.senha = BCrypt.hashpw(senha, BCrypt.gensalt(4));
        } else {
            this.senha = senha;
        }
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public NivelUsuario getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(NivelUsuario nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }
}
