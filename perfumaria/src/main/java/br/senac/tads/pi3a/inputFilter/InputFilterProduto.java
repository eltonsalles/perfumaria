/*
 * The MIT License
 *
 * Copyright 2017 fabiano.bfcarvalho.
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
import br.senac.tads.pi3a.validation.ValidationDate;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.util.Map;

/**
 *
 * @author fabiano.bfcarvalho
 */
public class InputFilterProduto extends InputFilter {

    public InputFilterProduto(Map<String, String[]> allMap) {
        super(allMap);
    }

    @Override
    public boolean isValid() {
        
        ValidationTamanho validationTamanho = new ValidationTamanho();
        ValidationString validationString = new ValidationString();
        ValidationAlpha validationAlpha = new ValidationAlpha();
        
        // Garante que o id do formulário produto está vazio ou que é um inteiro
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
       
       // validar categoria
       if(this.allMap.containsKey("categoria")){
            validationTamanho.setTamanho(50);
            
             if (validationTamanho.isValid(this.allMap.get("categoria")[0]) &&
                    validationAlpha.isValid(this.allMap.get("categoria")[0])) {
                this.errorValidation.replace("categoria", false);
            }
       }
       
       // validar data registro
       
       if(this.allMap.containsKey("data-registro")){
           
           ValidationDate validationDate = new ValidationDate();
           
           if(validationDate.isValid(this.allMap.get("data-registro")[0])){
               this.errorValidation.replace("data-registro", false);
           }
       }
       
       // validar nome produto
       
       if(this.allMap.containsKey("nome-produto")){
           
           validationTamanho.setTamanho(150);
           
           if(validationTamanho.isValid(this.allMap.get("nome-produto")[0]) &&
                   validationAlpha.isValid(this.allMap.get("nome-produto")[0])) {
               
                this.errorValidation.replace("nome-produto", false);
           }
       }
       
       // validar marca
         if(this.allMap.containsKey("marca")){
           
           validationTamanho.setTamanho(50);
           
           if(validationTamanho.isValid(this.allMap.get("marca")[0]) &&
                   validationAlpha.isValid(this.allMap.get("marca")[0])) {
               
                this.errorValidation.replace("marca", false);
           }
       }
         
         //
         
        return this.errorStatus();
    }

    @Override
    protected Model getModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
