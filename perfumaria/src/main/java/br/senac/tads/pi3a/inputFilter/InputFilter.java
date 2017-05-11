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

import br.senac.tads.pi3a.model.Model;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Elton
 */
public abstract class InputFilter {
    protected Map<String, String[]> allMap;
    
    protected Map<String, Object> errorValidation;

    /**
     * Faz o mapeamento do formulário
     * 
     * @param allMap 
     */
    public InputFilter(Map<String, String[]> allMap) {
        this.allMap = new LinkedHashMap<>(allMap);
        this.errorValidation = initErrorValidation(this.allMap);
    }
    
    /**
     * Retorna o mapa de erros dos campos
     * 
     * @return 
     */
    public Map<String, Object> getErrorValidation() {
        return errorValidation;
    }
    
    /**
     * Retorna um objeto com todos os dados preenchidos e válidos
     * 
     * @return 
     */
    public Model createModel() {
        try {
            // Se true tudo está certo
            if (this.errorStatus()) {
                return this.getModel();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return null;
    }
    
    /**
     * Método para retornar u objeto preenchido, mas sem validação
     * 
     * @return 
     */
    public Model getData() {
        return this.getModel();
    }
    
    /**
     * Retorna o status dos erros. False se ainda existe campo inválido
     * ou true se não existir mais
     * 
     * @return 
     */
    protected boolean errorStatus() {
        // Pegar a lista de erros
        Map<String, Object> error = this.errorValidation;
        
        for (String key : error.keySet()) {
            // Verifica na lista de erros se existe um campo com true e
            // se existir pelo menos um erro retorna false
            if ((boolean) error.get(key)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Inicia o atributo errorValidation com os campos do form e com valor
     * default true (Tem erro)
     * 
     * @param map
     * @return 
     */
    private Map<String, Object> initErrorValidation(Map<String, String[]> map) {
        Map<String, Object> error = new LinkedHashMap<>();
        
        for (String key : map.keySet()) {
            if (!key.equalsIgnoreCase("controller")
                    && !key.equalsIgnoreCase("action")) {
                if (key.contains("-")) {
                    key = arrangeName(key);
                }
                error.put(key, true);
            }
        }
        
        return error;
    }
    
    /**
     * Arruma os nome que tem '-' como separador
     * 
     * @param name
     * @return 
     */
    private String arrangeName(String name) {
        String[] parts = name.split("-");
        String result = "";
        
        for (int i = 1; i < parts.length; i++) {
            char firstLetter = parts[i].charAt(0);
            parts[i] = String.valueOf(firstLetter).toUpperCase() + parts[i]
                    .substring(1);
        }
        
        for (String part : parts) {
            result += part;
        }
        
        return result;
    }
    
    /**
     * Método que faz toda a validação do formulário
     * 
     * @return 
     */
    public abstract boolean isValid();
    
    /**
     * Retorna um objeto preenchido, mas sem validação
     * 
     * @return 
     */
    protected abstract Model getModel();
}
