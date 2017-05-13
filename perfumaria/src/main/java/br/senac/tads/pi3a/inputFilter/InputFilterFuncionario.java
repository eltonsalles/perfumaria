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

    public boolean isValid() {

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
        if(this.allMap.containsKey("nome")){
            ValidationAlpha validationAlpha = new ValidationAlpha();
            ValidationTamanho validationTamanho = new ValidationTamanho();
            
            validationTamanho.setTamanho(150);
            
            
            
        }
        
    }

    @Override
    protected Model getModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
