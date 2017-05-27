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

import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.util.Map;

/**
 *
 * @author joao.mihamasaki
 */
public class InputFilterManutencaoProduto extends InputFilter{

    public InputFilterManutencaoProduto(Map<String, String[]> allMap) {
        super(allMap);
    }

    @Override
    public boolean isValid() {
        ValidationTamanho validationTamanho = new ValidationTamanho();
        ValidationString validationString = new ValidationString();
        ValidationInt validationInt = new ValidationInt();
        //Validar nome produto
        if (this.allMap.containsKey("nome")) {
            validationTamanho.setTamanho(150);

            if (validationTamanho.isValid(this.allMap.get("nome")[0])
                    && validationString.isValid(this.allMap.get("nome")[0])) {
                this.errorValidation.replace("nome", false);
            }
        }      
        //Validar justificativa
        if (this.allMap.containsKey("justificativa")) {
            String justificativa = this.allMap.get("justificativa")[0];

            if (!justificativa.isEmpty()) {
                if (justificativa.equalsIgnoreCase("Entrada")
                        || justificativa.equalsIgnoreCase("Saida")
                        || justificativa.equalsIgnoreCase("Fora de Linha")
                        || justificativa.equalsIgnoreCase("Quebra")){
                    this.errorValidation.replace("justificativa", false);
                }
            }
        } 
        //Validar quantidade
        if (this.allMap.containsKey("quantidade")) {
            String estoque = this.allMap.get("quantidade")[0]
                    .replaceAll("\\D", "");

            if (validationInt.isValid(estoque)) {
                this.errorValidation.replace("quantidade", false);
                this.allMap.replace("quantidade", new String[]{estoque});
            }

        }      
        //Validar campo observação
        if (this.allMap.containsKey("descricao")) {
            validationTamanho.setTamanho(8000);

            if (validationTamanho.isValid(this.allMap.get("descricao")[0])
                    && validationString.isValid(this.allMap
                            .get("descricao")[0])) {
                this.errorValidation.replace("descricao", false);
            }
        }

        return this.errorStatus();
    }

    @Override
    protected Model getModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
