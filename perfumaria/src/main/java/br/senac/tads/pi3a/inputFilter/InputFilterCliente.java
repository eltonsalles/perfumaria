/*
 * The MIT License
 *
 * Copyright 2017 Elton.
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

import br.senac.tads.pi3a.model.Cliente;
import br.senac.tads.pi3a.model.Loja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.validation.ValidationAlfaNumerico;
import br.senac.tads.pi3a.validation.ValidationBoolean;
import br.senac.tads.pi3a.validation.ValidationCpf;
import br.senac.tads.pi3a.validation.ValidationDate;
import br.senac.tads.pi3a.validation.ValidationEmail;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elton
 */
public class InputFilterCliente extends InputFilter {
    /**
     * Construtor padrão
     * 
     * @param allMap 
     */
    public InputFilterCliente(Map<String, String[]> allMap) {
        super(allMap);
    }
    
    /**
     * Faz a validação do formulário cliente
     * 
     * @return 
     */
    @Override
    public boolean isValid() {
        ValidationTamanho validationTamanho = new ValidationTamanho();
        ValidationAlfaNumerico validationAlfaNumerico
                = new ValidationAlfaNumerico();
        
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
        
        // Verifica o nome do formulário cliente validando o tamanho e 
        // deixando apenas caracteres válidos na string
        if (this.allMap.containsKey("nome")) {
            ValidationString validationString = new ValidationString();
            
            validationTamanho.setTamanho(150);
            
            if (validationTamanho.isValid(this.allMap.get("nome")[0]) &&
                    validationString.isValid(this.allMap.get("nome")[0])) {
                this.errorValidation.replace("nome", false);
            }
        }
        
        // Verifica se a data de nascimento informada no formulário cliente
        // é uma data válida
        if (this.allMap.containsKey("data-nascimento")) {
            ValidationDate validationDate = new ValidationDate();
            
            if (validationDate.isValid(this.allMap.get("data-nascimento")[0])) {
                this.errorValidation.replace("dataNascimento", false);
            }
        }
        
        // Verifica se o cpf informado no formulário cliente é válido
        if (this.allMap.containsKey("cpf")) {
            // Deixa só os digitos
            String cpf = this.allMap.get("cpf")[0].replaceAll("\\D", "");
            
            ValidationCpf validationCpf = new ValidationCpf();
            
            if (validationCpf.isValid(cpf)) {
                this.errorValidation.replace("cpf", false);
                this.allMap.replace("cpf", new String[]{cpf});
            }
        }
        
        // Verifica se o valor de status do formulário cliente é um boolean
        if (this.allMap.containsKey("status")) {
            ValidationBoolean validationBoolean = new ValidationBoolean();
            
            if (validationBoolean.isValid(this.allMap.get("status")[0])) {
                this.errorValidation.replace("status", false);
            }
        }
        
        // Verifica o email do formulário cliente e garante que email seja
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
        // do formulário cliente
        if (this.allMap.containsKey("estado-civil")) {
            String estadoCivil = this.allMap.get("estado-civil")[0];
            
            if (!estadoCivil.isEmpty()) {
                if (estadoCivil.equalsIgnoreCase("solteiro")
                        || estadoCivil.equalsIgnoreCase("casado")
                        || estadoCivil.equalsIgnoreCase("divorciado")
                        || estadoCivil.equalsIgnoreCase("viuvo")) {
                    this.errorValidation.replace("estadoCivil", false);
                }
            }
        }
        
        // Garante que o valor do campo sexo do formulário cliente
        // seja F ou M
        if (this.allMap.containsKey("sexo")) {
            if (this.allMap.get("sexo")[0].equalsIgnoreCase("F")
                    || this.allMap.get("sexo")[0].equalsIgnoreCase("M")) {
                this.errorValidation.replace("sexo", false);
            }
        }
        
        // Verifica o valor e o tamanho de celular no formulário cliente
        if (this.allMap.containsKey("celular")) {
            // Deixa apenas digitos
            String celular = this.allMap.get("celular")[0]
                    .replaceAll("\\D", "");
            
            validationTamanho.setTamanho(11);
            
            if (validationTamanho.isValid(celular)) {
                this.errorValidation.replace("celular", false);
                this.allMap.replace("celular", new String[]{celular});
            }
        }
        
        // Verifica o valor e o tamanho de telefone no formulário cliente
        if (this.allMap.containsKey("telefone")) {
            // Deixa apenas digitos
            String telefone = this.allMap.get("telefone")[0]
                    .replaceAll("\\D", "");
            
            validationTamanho.setTamanho(11);
            
            if (validationTamanho.isValid(telefone)) {
                this.errorValidation.replace("telefone", false);
                this.allMap.replace("telefone", new String[]{telefone});
            }
        }
        
        // Verifica o logradouro do formulário cliente validando o tamanho e 
        // deixando apenas caracteres válidos na string
        if (this.allMap.containsKey("logradouro")) {
            validationTamanho.setTamanho(150);
            
            if (validationTamanho.isValid(this.allMap.get("logradouro")[0]) &&
                    validationAlfaNumerico.isValid(this.allMap
                            .get("logradouro")[0])) {
                this.errorValidation.replace("logradouro", false);
            }
        }
        
        // Verifica o número (endereço) do formulário cliente validando o 
        // tamanho e deixando apenas caracteres válidos na string
        if (this.allMap.containsKey("numero")) {
            validationTamanho.setTamanho(10);
            
            if (validationTamanho.isValid(this.allMap.get("numero")[0]) &&
                    validationAlfaNumerico.isValid(this.allMap
                            .get("numero")[0])) {
                this.errorValidation.replace("numero", false);
            }
        }
        
        // O campo complemento do formulário de cliente não é obrigatório
        // então apenas verifica se houver algum valor diferente de ""
        if (this.allMap.containsKey("complemento")) {
            if (!this.allMap.get("complemento")[0].isEmpty()) {
                validationTamanho.setTamanho(50);
                
                if (validationTamanho.isValid(this.allMap.get("complemento")[0])
                        && validationAlfaNumerico.isValid(this.allMap
                                .get("complemento")[0])) {
                    this.errorValidation.replace("complemento", false);
                }
            } else {
                this.errorValidation.replace("complemento", false);
            }
        }
        
        // Verifica o bairro do formulário cliente validando o tamanho e 
        // deixando apenas caracteres válidos na string
        if (this.allMap.containsKey("bairro")) {
            validationTamanho.setTamanho(50);
            
            if (validationTamanho.isValid(this.allMap.get("bairro")[0]) &&
                    validationAlfaNumerico.isValid(this.allMap
                            .get("bairro")[0])) {
                this.errorValidation.replace("bairro", false);
            }
        }
        
        // Verifica a cidade do formulário cliente validando o tamanho e 
        // deixando apenas caracteres válidos na string
        if (this.allMap.containsKey("cidade")) {
            validationTamanho.setTamanho(50);
            
            if (validationTamanho.isValid(this.allMap.get("cidade")[0]) &&
                    validationAlfaNumerico.isValid(this.allMap
                            .get("cidade")[0])) {
                this.errorValidation.replace("cidade", false);
            }
        }
        
        // Verifica se o valor do campo uf do formulário cliente é igual
        // a algum da lista
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
        
        // Verifica o valor e o tamanho de cep no formulário cliente
        if (this.allMap.containsKey("cep")) {
            // Deixa apenas digitos
            String cep = this.allMap.get("cep")[0].replaceAll("\\D", "");
            
            validationTamanho.setTamanho(8);
            
            if (validationTamanho.isValid(cep)) {
                this.errorValidation.replace("cep", false);
                this.allMap.replace("cep", new String[]{cep});
            }
        }
        
        return this.errorStatus();
    }

    /**
     * Retorna um objeto cliente
     * 
     * @return 
     */
    @Override
    protected Model getModel() {
        Cliente cliente = new Cliente();
        
        try {
            if (!this.allMap.get("id")[0].isEmpty()) {
                cliente.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }
            cliente.setStatus(Boolean.valueOf(this.allMap.get("status")[0]));
            cliente.setCpf(this.allMap.get("cpf")[0]);
            cliente.setNome(this.allMap.get("nome")[0]);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataNascimento = new Date(sdf.parse(this.allMap
                    .get("data-nascimento")[0]).getTime());
            cliente.setDataNascimento(dataNascimento);
            
            cliente.setSexo(this.allMap.get("sexo")[0].charAt(0));
            cliente.setEstadoCivil(this.allMap.get("estado-civil")[0]);
            cliente.setCelular(this.allMap.get("celular")[0]);
            cliente.setTelefone(this.allMap.get("telefone")[0]);
            cliente.setEmail(this.allMap.get("email")[0]);
            cliente.setLogradouro(this.allMap.get("logradouro")[0]);
            cliente.setNumero(this.allMap.get("numero")[0]);
            
            if (!this.allMap.get("complemento")[0].isEmpty()) {
                cliente.setComplemento(this.allMap.get("complemento")[0]);
            }
            
            cliente.setBairro(this.allMap.get("bairro")[0]);
            cliente.setCep(this.allMap.get("cep")[0]);
            cliente.setCidade(this.allMap.get("cidade")[0]);
            cliente.setUf(this.allMap.get("uf")[0]);

            // #MOCK
            Loja loja = new Loja();
            loja.setId(1);
            cliente.setLoja(loja);
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace(System.err);
            cliente = null;
        }
        
        return cliente;
    }
}
