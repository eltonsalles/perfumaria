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


import br.senac.tads.pi3a.model.HistoricoProduto;
import br.senac.tads.pi3a.model.Loja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Produto;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author joao.mihamasaki
 */
public class InputFilterManutencaoProduto extends InputFilter {

    public InputFilterManutencaoProduto(Map<String, String[]> allMap) {
        super(allMap);
    }

    @Override
    public boolean isValid() {
        ValidationTamanho validationTamanho = new ValidationTamanho();
        ValidationString validationString = new ValidationString();
        ValidationInt validationInt = new ValidationInt();
   
            //Validação do ID
            if (this.allMap.containsKey("id")) {
                if (validationInt.isValid(this.allMap.get("id")[0])) {
                    if (Integer.valueOf(this.allMap.get("id")[0]) > 0) {
                        this.errorValidation.replace("id", false);
                    }
                }
            }
            //Validação para Loja - campo em manutencao-produto.jsp
            if (this.allMap.containsKey("loja")) {
                if (validationInt.isValid(this.allMap.get("loja")[0])) {
                    if (Integer.valueOf(this.allMap.get("loja")[0]) > 0) {
                        this.errorValidation.replace("loja", false);
                    }
                }
            }
            //Validação para Produto
            if (this.allMap.containsKey("produto")) {
                if (validationInt.isValid(this.allMap.get("produto")[0])) {
                    if (Integer.valueOf(this.allMap.get("produto")[0]) > 0) {
                        this.errorValidation.replace("produto", false);
                    }
                }
            }

            //Validar justificativa
            if (this.allMap.containsKey("justificativa")) {
                String justificativa = this.allMap.get("justificativa")[0];
                if (!justificativa.isEmpty()) {
                    if (justificativa.equalsIgnoreCase("Entrada")
                            || justificativa.equalsIgnoreCase("Saida")
                            || justificativa.equalsIgnoreCase("Fora de Linha")
                            || justificativa.equalsIgnoreCase("Quebra")) {
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
        HistoricoProduto historicoProduto = new HistoricoProduto();
        Loja loja = new Loja();
        Produto produto = new Produto();
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataMovimentacao = new Date(sdf.parse(this.allMap
                    .get("data-movimentacao")[0]).getTime());
            
            historicoProduto.setDataMovimentacao(dataMovimentacao);
            historicoProduto.setTipoMovimentacao(this.allMap
                    .get("tipo-movimentacao")[0]);
            historicoProduto.setDescricao(this.allMap.get("descricao")[0]);
            historicoProduto.setQuantidade(Integer.valueOf(this.allMap
                    .get("quantidade")[0]));
            
            produto.setId(Integer.valueOf(this.allMap.get("produto")[0]));
            loja.setId(Integer.valueOf(this.allMap.get("loja")[0]));
        }catch(NumberFormatException | ParseException e) {
            e.printStackTrace(System.err);
            historicoProduto = null;
        }
        return historicoProduto;
    }
}