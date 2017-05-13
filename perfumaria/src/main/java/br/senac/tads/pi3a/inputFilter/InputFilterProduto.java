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

import br.senac.tads.pi3a.model.ItensLoja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Produto;
import br.senac.tads.pi3a.validation.ValidationAlpha;
import br.senac.tads.pi3a.validation.ValidationBoolean;
import br.senac.tads.pi3a.validation.ValidationDate;
import br.senac.tads.pi3a.validation.ValidationFloat;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        ValidationInt validationInt = new ValidationInt();

        // Garante que o id do formulário produto está vazio ou que é um inteiro
        // maior que 0
        if (this.allMap.containsKey("id")) {
            if (!this.allMap.get("id")[0].isEmpty()) {

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
        if (this.allMap.containsKey("categoria")) {
            validationTamanho.setTamanho(50);

            if (validationTamanho.isValid(this.allMap.get("categoria")[0])
                    && validationAlpha.isValid(this.allMap.get("categoria")[0])) {
                this.errorValidation.replace("categoria", false);
            }
        }

        // validar data registro
        if (this.allMap.containsKey("data-registro")) {

            ValidationDate validationDate = new ValidationDate();

            if (validationDate.isValid(this.allMap.get("data-registro")[0])) {
                this.errorValidation.replace("data-registro", false);
            }
        }

        // validar nome produto
        if (this.allMap.containsKey("nome-produto")) {

            validationTamanho.setTamanho(150);

            if (validationTamanho.isValid(this.allMap.get("nome-produto")[0])
                    && validationAlpha.isValid(this.allMap.get("nome-produto")[0])) {

                this.errorValidation.replace("nome-produto", false);
            }
        }

        // validar marca
        if (this.allMap.containsKey("marca")) {

            validationTamanho.setTamanho(50);

            if (validationTamanho.isValid(this.allMap.get("marca")[0])
                    && validationAlpha.isValid(this.allMap.get("marca")[0])) {

                this.errorValidation.replace("marca", false);
            }
        }

        // validar quantidade
        if (this.allMap.containsKey("quantidade")) {
            validationTamanho.setTamanho(10);

            if (validationInt.isValid(this.allMap.get("quantidade")[0])
                    && validationTamanho.isValid(this.allMap.get("quantidade")[0])) {
                this.errorValidation.replace("marca", false);
            }

        }
        // validar qtd medida

        if (this.allMap.containsKey("qtd-medida")) {
            validationTamanho.setTamanho(10);

            if (validationInt.isValid(this.allMap.get("qtd-medida")[0])
                    && validationTamanho.isValid(this.allMap.get("qtd-medida")[0])) {

                this.errorValidation.replace("qtd-medida", false);
            }
        }

        // validar unidade de media
        if (this.allMap.containsKey("uni-medida")) {
            String uniMedida = this.allMap.get("uni-media")[0];

            validationTamanho.setTamanho(3);

            if (!uniMedida.isEmpty()) {
                if (validationTamanho.isValid(this.allMap.get("uni-medida")[0])) {
                    if (uniMedida.equalsIgnoreCase("ml")
                            || uniMedida.equalsIgnoreCase("g")) {
                        this.errorValidation.replace("uni-medida", false);
                    }
                }
            }
        }

        // validar compra
        if (this.allMap.containsKey("compra")) {

            ValidationFloat validationFloat = new ValidationFloat();
            validationTamanho.setTamanho(12);

            if (validationTamanho.isValid(this.allMap.get("compra")[0])
                    && validationFloat.isValid(this.allMap.get("compra")[0])) {

                this.errorValidation.replace("compra", false);
            }

        }

        //validar venda
        if (this.allMap.containsKey("venda")) {

            ValidationFloat validationFloat = new ValidationFloat();
            validationTamanho.setTamanho(12);

            if (validationTamanho.isValid(this.allMap.get("venda")[0])
                    && validationFloat.isValid(this.allMap.get("venda")[0])) {

                this.errorValidation.replace("venda", false);
            }

        }

        // validar genero
        if (this.allMap.containsKey("genero")) {
            String genero = this.allMap.get("genero")[0];

            validationTamanho.setTamanho(9);

            if (!genero.isEmpty()) {
                if (validationTamanho.isValid(this.allMap.get("genero")[0])) {
                    if (genero.equalsIgnoreCase("masculino")
                            || genero.equalsIgnoreCase("feminino")) {
                        this.errorValidation.replace("genero", false);
                    }
                }
            }
        }
        // validar descrição

        if (this.allMap.containsKey("descricao")) {

            validationTamanho.setTamanho(8000);

            if (validationTamanho.isValid(this.allMap.get("descricao")[0])
                    && validationAlpha.isValid(this.allMap.get("descricao")[0])) {

                this.errorValidation.replace("descricao", false);
            }
        }
// validação status

        if (this.allMap.containsKey("status")) {
            ValidationBoolean validationBoolean = new ValidationBoolean();

            if (validationBoolean.isValid(this.allMap.get("status")[0])) {
                this.errorValidation.replace("status", false);
            }
        }
        return this.errorStatus();
    }

    @Override
    protected Model getModel() {
        Produto produto = new Produto();
        ItensLoja itensLoja = new ItensLoja();
        try {
            if (!this.allMap.get("id")[0].isEmpty()) {
                produto.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }
            produto.setNome(this.allMap.get("nome-produto")[0]);
            produto.setCategoria(this.allMap.get("categoria")[0]);
            produto.setGenero(this.allMap.get("genero")[0]);
            produto.setMarca(this.allMap.get("marca")[0]);
            produto.setValorUnidadeMedida(Integer.valueOf(this.allMap.get("qtd-medida")[0]));

            if (!this.allMap.get("id")[0].isEmpty()) {
                itensLoja.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataRegistro = new Date(sdf.parse(this.allMap
                    .get("data-registro")[0]).getTime());
            itensLoja.setDataCadastro(dataRegistro);

            itensLoja.setEstoque(Integer.valueOf(this.allMap.get("quantidade")[0]));
            itensLoja.setProduto(produto);
            itensLoja.setStatus(Boolean.valueOf(this.allMap.get("status")[0]));
            
            itensLoja.setValorCompra(Float.valueOf(this.allMap.get("compra")[0]));
            itensLoja.setValorVenda(Float.valueOf(this.allMap.get("venda")[0]));;
            
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            produto = null;
        }
        return itensLoja;
    }

}
