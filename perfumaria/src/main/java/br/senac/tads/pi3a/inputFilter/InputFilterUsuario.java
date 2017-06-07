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
package br.senac.tads.pi3a.inputFilter;

import br.senac.tads.pi3a.model.Funcionario;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.NivelUsuario;
import br.senac.tads.pi3a.model.Usuario;
import br.senac.tads.pi3a.validation.ValidationAlphaNumerico;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.util.Map;

/**
 *
 * @author joao.mihamasaki
 */
public class InputFilterUsuario extends InputFilter {

    public InputFilterUsuario(Map<String, String[]> allMap) {
        super(allMap);
    }

    @Override
    public boolean isValid() {
        ValidationTamanho validationTamanho = new ValidationTamanho();
        // Garante que o id do formulário usuário está vazio ou que é um inteiro
        // maior que 0
        if (this.allMap.containsKey("id")) {
            if (!this.allMap.get("id")[0].isEmpty()) {
                ValidationInt validationInt = new ValidationInt();

                if (validationInt.isValid(this.allMap.get("id")[0])) {
                    if (Integer.valueOf(this.allMap.get("id")[0]) > 0) {
                        this.errorValidation.replace("id", false);
                    }
                }
            } else {
                this.errorValidation.replace("id", false);
            }
        }
        // Garante que o id do formulário funcionario está vazio ou que é um inteiro
        // maior que 0
        if (this.allMap.containsKey("funcionario")) {
            if (!this.allMap.get("funcionario")[0].isEmpty()) {
                ValidationInt validationInt = new ValidationInt();

                if (validationInt.isValid(this.allMap.get("funcionario")[0])) {
                    if (Integer.valueOf(this.allMap
                            .get("funcionario")[0]) > 0) {
                        this.errorValidation.replace("funcionario", false);
                    }
                }
            } else {
                this.errorValidation.replace("funcionario", false);
            }
        }
        // Verifica o nome do formulário usuario(login) validando o tamanho e 
        // deixando apenas caracteres válidos na string
        if (this.allMap.containsKey("login")) {
            ValidationAlphaNumerico validationAlphaNumerico
                    = new ValidationAlphaNumerico();

            validationTamanho.setTamanho(50);

            if (validationTamanho.isValid(this.allMap.get("login")[0])
                    && validationAlphaNumerico
                            .isValid(this.allMap.get("login")[0])) {
                this.errorValidation.replace("login", false);
            }
        }
        // Verifica o nome do formulário usuario(senha) validando o tamanho e 
        // deixando apenas caracteres válidos na string
        if (this.allMap.containsKey("senha")) {
            ValidationAlphaNumerico validationAlphaNumerico 
                    = new ValidationAlphaNumerico();

            validationTamanho.setTamanho(8);

            if (validationTamanho.isValid(this.allMap.get("senha")[0])
                    && validationAlphaNumerico
                            .isValid(this.allMap.get("senha")[0])) {
                this.errorValidation.replace("senha", false);
            }
        }
        //Verifica se algum campo está selecionado no campo Nível de Usuário
        //do formulário Usuario
        if (this.allMap.containsKey("nivel-usuario")) {
            String nivelUsuario = this.allMap.get("nivel-usuario")[0];

            if (!nivelUsuario.isEmpty()) {
                if (nivelUsuario.equals("1")
                        || nivelUsuario.equals("2")
                        || nivelUsuario.equals("3")
                        || nivelUsuario.equals("4")
                        || nivelUsuario.equals("5")
                        || nivelUsuario.equals("6")
                        || nivelUsuario.equals("7")
                        || nivelUsuario.equals("8")) {
                    this.errorValidation.replace("nivelUsuario", false);
                }
            }
        }
        return this.errorStatus();
    }

    @Override
    protected Model getModel() {
        Usuario usuario = new Usuario();

        try {
            if (!this.allMap.get("id")[0].isEmpty()) {
                usuario.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }
            Funcionario funcionario = new Funcionario();
            funcionario.setId(Integer.valueOf(this.allMap
                    .get("funcionario")[0]));
            usuario.setFuncionario(funcionario);

            NivelUsuario nivelUsuario = new NivelUsuario();
            nivelUsuario.setId(Integer.valueOf(this.allMap
                    .get("nivel-usuario")[0]));
            usuario.setNivelUsuario(nivelUsuario);

            usuario.setLogin(this.allMap.get("login")[0].trim());
            usuario.setSenha(this.allMap.get("senha")[0]);

        } catch (NumberFormatException e) {
            e.printStackTrace(System.err);
            usuario = null;
        }

        return usuario;
    }

}
