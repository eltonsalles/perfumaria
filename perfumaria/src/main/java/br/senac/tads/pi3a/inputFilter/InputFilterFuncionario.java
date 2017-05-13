/*
 * The MIT License
 *
 * Copyright 2017 fillipe.poliveira.
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

import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.validation.ValidationAlpha;
import br.senac.tads.pi3a.validation.ValidationBoolean;
import br.senac.tads.pi3a.validation.ValidationCpf;
import br.senac.tads.pi3a.validation.ValidationDate;
import br.senac.tads.pi3a.validation.ValidationEmail;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import com.sun.org.apache.xerces.internal.impl.validation.ValidationState;
import java.util.Map;

/**
 *
 * @author Fillipe
 */
public class InputFilterFuncionario extends InputFilter {

    public InputFilterFuncionario(Map<String, String[]> allMap) {
        super(allMap);
    }

    @Override
    public boolean isValid() {
        ValidationTamanho validationTamanho = new ValidationTamanho();
        // Garante que o id do formulário cliente está vazio ou que é um inteiro
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

        // Verifica o nome do formulário funcionário validando o tamanho e 
        // deixando apenas caracteres válidos na string
        if (this.allMap.containsKey("nome")) {
            ValidationAlpha validationAlpha = new ValidationAlpha();

            validationTamanho.setTamanho(150);

            if (validationTamanho.isValid(this.allMap.get("nome")[0])
                    && validationAlpha.isValid(this.allMap.get("nome")[0])) {
                this.errorValidation.replace("nome", false);
            }

        }

        // Verifica se a data de nascimento informada no formulário funcionário
        // é uma data válida
        if (this.allMap.containsKey("data-nascimento")) {
            ValidationDate validationDate = new ValidationDate();
            if (validationDate.isValid(this.allMap.get("data-nascimento")[0])) {
                this.errorValidation.replace("data-nascimento", false);
            }
        }

        // Verifica se o cpf informado no formulário funcionário é válido
        if (this.allMap.containsKey("cpf")) {
            // Deixa apenas digítos
            String cpf = this.allMap.get("cpf")[0].replaceAll("\\D", "");
            ValidationCpf validationCpf = new ValidationCpf();
            if (validationCpf.isValid(cpf)) {
                this.errorValidation.replace("cpf", false);
                this.allMap.replace("cpf", new String[]{cpf});
            }
        }

        // Verifica se o valor de status do formulário funcionário é um boolean
        if (this.allMap.containsKey("status")) {
            ValidationBoolean validationBoolean = new ValidationBoolean();
            if (validationBoolean.isValid(this.allMap.get("status")[0])) {
                this.errorValidation.replace("status", false);
            }
        }

        // Verifica o email do formulário funcionário e garante que email seja
        // em um padrão válido
        if (this.allMap.containsKey("email")) {
            ValidationEmail validationEmail = new ValidationEmail();
            validationTamanho.setTamanho(150);
            if (validationTamanho.isValid(this.allMap.get("email")[0])
                    && validationEmail.isValid(this.allMap.get("email")[0])) {
                this.errorValidation.replace("email", false);
            }
        }

        // Garante que algo esteja selecionado no campo estado civil
        // do formulário funcionário
        if (this.allMap.containsKey("estado-civil")) {
            String estadoCivil = this.allMap.get("estado-civil")[0];

            if (!estadoCivil.isEmpty()) {
                if (estadoCivil.equalsIgnoreCase("solteiro")
                        || estadoCivil.equalsIgnoreCase("casado")
                        || estadoCivil.equalsIgnoreCase("divorciado")
                        || estadoCivil.equalsIgnoreCase("viuvo")) {
                    this.errorValidation.replace("estado-civil", false);
                }
            }
        }

        // Garante que o valor do campo sexo do formulário funcionário
        // seja F ou M
        if (this.allMap.containsKey("sexo")) {
            if (this.allMap.get("sexo")[0].equalsIgnoreCase("F")
                    || this.allMap.get("sexo")[0].equalsIgnoreCase("M")) {
                this.errorValidation.replace("sexo", false);
            }
        }

        // Verifica o valor e o tamanho de celular no formulário funcionário
        if (this.allMap.containsKey("celular")) {
            //Deixa apenas digítos
            String celular = this.allMap.get("celular")[0].replaceAll("\\D", "");
            validationTamanho.setTamanho(11);
            if(validationTamanho.isValid(celular)){
                this.errorValidation.replace("celular", false);
                this.allMap.replace("celular", new String[]{celular});
            }
        }
        
        // Verifica o valor e o tamanho de telefone no formulário funcionário
        if(this.allMap.containsKey("telefone")){
            //Deixa apenas digítos
            String telefone = this.allMap.get("telefone")[0].replaceAll("\\D", "");
            validationTamanho.setTamanho(11);
            if(validationTamanho.isValid(telefone)){
                this.errorValidation.replace("telefone", false);
                this.allMap.replace("celular", new String[] {telefone});
            }
        }
        
        // Verifica o logradouro do formulário funcionário validando o tamanho e 
        // deixando apenas caracteres válidos na string
        
        
    }

    @Override
    protected Model getModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
