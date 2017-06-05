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
package br.senac.tads.pi3a.inputFilter;

import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.validation.ValidationCpf;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Douglas Oliveira
 */
public class InputFilterVenda extends InputFilter {

    /**
     * Construtor padrão
     *
     * @param allMap
     */
    public InputFilterVenda(Map<String, String[]> allMap) {
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
        ValidationString validationString = new ValidationString();
        
        // Verifica se o cpf informado no formulário é válido
        if (this.allMap.containsKey("cpf")) {
            // Deixa só os digitos
            String cpf = this.allMap.get("cpf")[0].replaceAll("\\D", "");
            
            ValidationCpf validationCpf = new ValidationCpf();
            
            if (validationCpf.isValid(cpf)) {
                this.errorValidation.replace("cpf", false);
                this.allMap.replace("cpf", new String[]{cpf});
            }
        }
        
        // Verifica se existe id de cliente
        if (this.allMap.containsKey("id-cliente")) {            
            ValidationInt validationInt = new ValidationInt();

            if (validationInt.isValid(this.allMap.get("id-cliente")[0])) {
                if (Integer.valueOf(this.allMap.get("id-cliente")[0]) > 0) {
                    this.errorValidation.replace("id-cliente", false);
                }
            }
        }
        
        // Verifica se o id do produto é um número válido
        if (this.allMap.containsKey("codigo")) {
            ValidationInt validationInt = new ValidationInt();
            boolean error = true;
            
            for (int i = 0; i < this.allMap.get("codigo").length; i++) {
                if (validationInt.isValid(this.allMap.get("codigo")[i])) {
                    if (Integer.valueOf(this.allMap.get("codigo")[i]) > 0) {
                        error = false;
                    } else {
                        error = true;
                        break;
                    }
                }
            }
            
            this.errorValidation.replace("codigo", error);
        }
        
        // Verifica se o nome do produto é um válido válido
        if (this.allMap.containsKey("produto")) {
            boolean error = true;
            
            for (int i = 0; i < this.allMap.get("produto").length; i++) {
                validationTamanho.setTamanho(150);

                if (validationTamanho.isValid(this.allMap.get("produto")[i])
                        && validationString.isValid(this.allMap
                                .get("produto")[i])) {
                    error = false;
                } else {
                    error = true;
                    break;
                }
            }
            
            this.errorValidation.replace("produto", error);
        }
        
        // Verifica se a quantidade é um número válido
        if (this.allMap.containsKey("quantidade")) {
            ValidationInt validationInt = new ValidationInt();
            boolean error = true;
            
            for (int i = 0; i < this.allMap.get("quantidade").length; i++) {
                if (validationInt.isValid(this.allMap.get("quantidade")[i])) {
                    if (Integer.valueOf(this.allMap.get("quantidade")[i]) > 0) {
                        error = false;
                    } else {
                        error = true;
                        break;
                    }
                }
            }
            
            this.errorValidation.replace("quantidade", error);
        }
        
        // Muda o status desses campos para a validação passar. Esses campos
        // são preenchidos de maneira automatica
        this.errorValidation.replace("nome", false);
        this.errorValidation.replace("marca", false);
        this.errorValidation.replace("preco-unidade", false);
        this.errorValidation.replace("preco-total", false);
        
        return this.errorStatus();
    }

    @Override
    protected Model getModel() {
        return null;
    }
    
    public Map<String, Object[]> getDataForm() {
        Map<String, Object[]> dados = new LinkedHashMap<>();
        
        dados.put("cpf", new Object[]{this.allMap.get("cpf")[0]});
        dados.put("nome", new Object[]{this.allMap.get("nome")[0]});
        dados.put("idCliente", new Object[]{this.allMap.get("id-cliente")[0]});
        
        Object[] codigos = new Object[this.allMap.get("codigo").length];
        for (int i = 0; i < codigos.length; i++) {
            codigos[i] = this.allMap.get("codigo")[i];
        }
        dados.put("codigo", codigos);
        
        Object[] produtos = new Object[this.allMap.get("produto").length];
        for (int i = 0; i < produtos.length; i++) {
            produtos[i] = this.allMap.get("produto")[i];
        }
        dados.put("produto", produtos);
        
        Object[] marcas = new Object[this.allMap.get("marca").length];
        for (int i = 0; i < marcas.length; i++) {
            marcas[i] = this.allMap.get("marca")[i];
        }
        dados.put("marca", marcas);
        
        Object[] quantidades = new Object[this.allMap.get("quantidade").length];
        for (int i = 0; i < quantidades.length; i++) {
            quantidades[i] = this.allMap.get("quantidade")[i];
        }
        dados.put("quantidade", quantidades);
        
        Object[] precosUnidades = new Object[this.allMap.get("preco-unidade").length];
        for (int i = 0; i < precosUnidades.length; i++) {
            precosUnidades[i] = this.allMap.get("preco-unidade")[i];
        }
        dados.put("precoUnidade", precosUnidades);
        
        Object[] precosTotais = new Object[this.allMap.get("preco-total").length];
        for (int i = 0; i < precosTotais.length; i++) {
            precosTotais[i] = this.allMap.get("preco-total")[i];
        }
        dados.put("precoTotal", precosTotais);
        
        dados.put("cont", new Object[]{codigos.length - 1});
        
        return dados;
    }
}
