/*
 * The MIT License
 *
 * Copyright 2017 glebson.lsilva1.
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

import br.senac.tads.pi3a.model.Loja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.validation.ValidationAlpha;
import br.senac.tads.pi3a.validation.ValidationAlphaNumerico;
import br.senac.tads.pi3a.validation.ValidationBoolean;
import br.senac.tads.pi3a.validation.ValidationCnpj;
import br.senac.tads.pi3a.validation.ValidationEmail;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author glebson.lsilva1
 */
public class InputFilterLoja extends InputFilter {

    public InputFilterLoja(Map<String, String[]> allMap) {

        super(allMap);

    }

    /**
     * Faz a validação do formulário cadastrar loja
     *
     * @return
     */
    @Override
    public boolean isValid() {

        ValidationAlphaNumerico validatioAlphaNumerico = new ValidationAlphaNumerico();
        ValidationAlpha validationAlpha = new ValidationAlpha();
        ValidationTamanho validationTamanho = new ValidationTamanho();
        ValidationString validationString = new ValidationString();

        // Garante que o id do formulário Loja está vazio ou que é um inteiro
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
        //Valida o campo texto razao-social para que possa conter letras e
        //numeros
        if (this.allMap.containsKey("razao-social")) {

            validationTamanho.setTamanho(150);

            if (validationTamanho.isValid(this.allMap.get("razao-social")[0])
                    && validatioAlphaNumerico.isValid(this.allMap.get("razao-social")[0])) {

                this.errorValidation.replace("razao-social", false);

            }

        }
        //Valida o campo CNPJ da Loja
        if (this.allMap.containsKey("cnpj")) {

            String cnpj = this.allMap.get("cnpj")[0].replaceAll("\\D", "");

            ValidationCnpj validationCnpj = new ValidationCnpj();

            if (validationCnpj.isValid(cnpj)) {

                this.errorValidation.replace("cnpj", false);
                this.allMap.replace("cnpj", new String[]{cnpj});

            }

        }
        //Validando o status da loja
        if (this.allMap.containsKey("status")) {

            ValidationBoolean validationBoolean = new ValidationBoolean();

            if (validationBoolean.isValid(this.allMap.get("status")[0])) {

                this.errorValidation.replace("status", false);

            }

        }
        //Validando o valor do campo nome-fantasia
        if (this.allMap.containsKey("nome-fantasia")) {

            validationTamanho.setTamanho(150);

            if (validationTamanho.isValid(this.allMap.get("nome-fantasia")[0])
                    && validationAlpha.isValid(this.allMap.get("nome-fantasia")[0])) {

                this.errorValidation.replace("nome-fantasia", false);

            }

        }
        //Validando o valor e o tamanho do campo celular no formulário Cadastrar 
        //Loja 
        if (this.allMap.containsKey("celular")) {

            //Extrai apenas os digitos numéricos do campo
            String celular = this.allMap.get("celular")[0].replaceAll("\\D", "");

            validationTamanho.setTamanho(11);

            if (validationTamanho.isValid(celular)) {
                this.errorValidation.replace("celular", false);
                this.allMap.replace("celular", new String[]{celular});

            }

        }
        //Validando o valor e o tamanho do campo telefone no formulário Cadastrar
        //Loja
        if (this.allMap.containsKey("telefone")) {

            //Extrai apenas os digitos numéricos do campo telefone
            String telefone = this.allMap.get("telefone")[0].replaceAll("\\D", "");
            validationTamanho.setTamanho(11);

            if (validationTamanho.isValid(telefone)) {

                this.errorValidation.replace("telefone", false);
                this.allMap.replace("telefone", new String[]{telefone});

            }

        }
        //Validando o valor do campo enail do formulario cadastrar loja
        if (this.allMap.containsKey("email")) {

            ValidationEmail validationEmail = new ValidationEmail();

            validationTamanho.setTamanho(150);

            if (validationTamanho.isValid(this.allMap.get("email")[0])
                    && validationEmail.isValid(this.allMap.get("email")[0])) {

                this.errorValidation.replace("email", false);

            }

        }
        //Validando o valor do campo logradouro do formulario cadastrar loja com caractéres 
        //válidos apenas
        if (this.allMap.containsKey("logradouro")) {

            validationTamanho.setTamanho(150);
            if (validationTamanho.isValid(this.allMap.get("logradouro")[0])
                    && validationString.isValid(this.allMap.get("logradouro")[0])) {
                this.errorValidation.replace("logradouro", false);

            }

        }
        //Validando o valor do campo numero do formulario cadastrar loja com caractéres
        //válidos
        if (this.allMap.containsKey("numero")) {
            String numero = this.allMap.get("numero")[0].replaceAll("\\D", "");

            validationTamanho.setTamanho(10);

            if (validationTamanho.isValid(numero)) {
                this.errorValidation.replace("numero", false);

            }

        }
        //Validando o valor do campo complemento do formulário cadastrar loja verificando se
        //ha algum valor diferente de ""
        if (this.allMap.containsKey("complemento")) {
            if (!this.allMap.get("complemento")[0].isEmpty()) {
                validationTamanho.setTamanho(50);
            }
            if (validationTamanho.isValid(this.allMap.get("complemento")[0])
                    && validationString.isValid(this.allMap.get("complemento")[0])) {
                this.errorValidation.replace("complemento", false);

            }

        }
        //Validando o valor do campo Bairro do formulario cadastrar loja - tamanho e 
        //caractperes válidos
        if (this.allMap.containsKey("bairro")) {

            validationTamanho.setTamanho(50);

            if (validationTamanho.isValid(this.allMap.get("bairro")[0])
                    && validationString.isValid(this.allMap.get("bairro")[0])) {

                this.errorValidation.replace("bairro", false);

            }

        }
        //Validando o valor do campo cidade do formulário cadastrar loja - tamanho e 
        //campos válidos
        if (this.allMap.containsKey("cidade")) {

            validationTamanho.setTamanho(50);
            if (validationTamanho.isValid(this.allMap.get("cidade")[0])
                    && validationAlpha.isValid(this.allMap.get("cidade")[0])) {
                this.errorValidation.replace("cidade", false);
            }

        }
        //Validando o valor do campo UF(estado) do formulario cliente de acordo com 
        //valor igual da lista
        if (this.allMap.containsKey("uf")) {

            List ufs = new ArrayList();
            ufs.addAll(Arrays.asList(new String[]{"AC", "AL", "AP", "AM", "BA",
                "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PR", "PB",
                "PA", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SE",
                "SP", "TO"}));

            if (ufs.contains(this.allMap.get("uf")[0])) {

                this.errorValidation.replace("uf", false);

            }

        }
        // Validando o valor do campo CEP e o tamanho no formulário cadastrar loja
        if(this.allMap.containsKey("cep")){
        
            //Extrai apenas os digitos
            String cep = this.allMap.get("cep")[0].replaceAll("\\D", "");
            
            validationTamanho.setTamanho(8);
            
            if(validationTamanho.isValid(cep)){
            
                this.errorValidation.replace("cep", false);
                this.allMap.replace("cep", new String[]{cep});
                
            }
        
        
        }

        return this.errorStatus();
    }

    @Override
    protected Model getModel() {
        
        Loja loja = new Loja();
        
        try{
           
            if (!this.allMap.get("id")[0].isEmpty()) {
                loja.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }
            loja.setRazaoSocial(this.allMap.get("razaoSocial")[0]);
            loja.setCnpj(this.allMap.get("cnpj")[0]);
            loja.setStatus(Boolean.valueOf(this.allMap.get("status")[0]));
            loja.setNomeFantasia(this.allMap.get("razaoSocial")[0]);
            loja.setCelular(this.allMap.get("celular")[0]);
            loja.setTelefone(this.allMap.get("telefone")[0]);
            loja.setEmail(this.allMap.get("email")[0]);
            loja.setLogradouro(this.allMap.get("logradouro")[0]);
            loja.setNumero(this.allMap.get("numero")[0]);
            
            if (!this.allMap.get("complemento")[0].isEmpty()) {
                loja.setComplemento(this.allMap.get("complemento")[0]);
            }
            loja.setBairro(this.allMap.get("bairro")[0]);
            loja.setCidade(this.allMap.get("cidade")[0]);
            loja.setUf(this.allMap.get("uf")[0]);
            loja.setCep(this.allMap.get("cep")[0]);
            
            
        
        }catch(NumberFormatException e){
        
            e.printStackTrace(System.err);
            loja = null;
        
        }

        return loja;
    }

}
