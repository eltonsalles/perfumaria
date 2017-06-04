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
import br.senac.tads.pi3a.model.Loja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Produto;
import br.senac.tads.pi3a.validation.ValidationBoolean;
import br.senac.tads.pi3a.validation.ValidationDate;
import br.senac.tads.pi3a.validation.ValidationFloat;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.text.ParseException;
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
   // Validação para Loja - campo em cadastrar-produto.jsp
        if (this.allMap.containsKey("loja")) {
            if (validationInt.isValid(this.allMap.get("loja")[0])) {
                if (Integer.valueOf(this.allMap.get("loja")[0]) > 0) {
                    this.errorValidation.replace("loja", false);
                }
            }
        }
        // Validar nome produto
        if (this.allMap.containsKey("nome")) {
            validationTamanho.setTamanho(150);

            if (validationTamanho.isValid(this.allMap.get("nome")[0])
                    && validationString.isValid(this.allMap.get("nome")[0])) {
                this.errorValidation.replace("nome", false);
            }
        }

        // Validar data de cadastro
        if (this.allMap.containsKey("data-cadastro")) {
            ValidationDate validationDate = new ValidationDate();

            if (validationDate.isValid(this.allMap.get("data-cadastro")[0])) {
                this.errorValidation.replace("dataCadastro", false);
            }
        }

        // Validação status
        if (this.allMap.containsKey("status")) {
            ValidationBoolean validationBoolean = new ValidationBoolean();

            if (validationBoolean.isValid(this.allMap.get("status")[0])) {
                this.errorValidation.replace("status", false);
            }
        }

        // Validar categoria
        if (this.allMap.containsKey("categoria")) {
            validationTamanho.setTamanho(50);

            if (validationTamanho.isValid(this.allMap.get("categoria")[0])
                    && validationString.isValid(this.allMap
                            .get("categoria")[0])) {
                this.errorValidation.replace("categoria", false);
            }
        }

        // Validar marca
        if (this.allMap.containsKey("marca")) {
            validationTamanho.setTamanho(50);

            if (validationTamanho.isValid(this.allMap.get("marca")[0])
                    && validationString.isValid(this.allMap.get("marca")[0])) {
                this.errorValidation.replace("marca", false);
            }
        }

        // Validar quantidade
        if (this.allMap.containsKey("estoque")) {
            String estoque = this.allMap.get("estoque")[0]
                    .replaceAll("\\D", "");

            if (validationInt.isValid(estoque)) {
                this.errorValidation.replace("estoque", false);
                this.allMap.replace("estoque", new String[]{estoque});
            }

        }

        // Validar qtd medida
        if (this.allMap.containsKey("valor-unidade-medida")) {
            String valorUnidadeMedida = this.allMap
                    .get("valor-unidade-medida")[0].replaceAll("\\D", "");

            if (validationInt.isValid(valorUnidadeMedida)) {
                this.errorValidation.replace("valorUnidadeMedida", false);
                this.allMap.replace("valor-unidade-medida",
                        new String[]{valorUnidadeMedida});
            }
        }

        // Validar unidade de media
        if (this.allMap.containsKey("unidade-medida")) {
            String unidadeMedida = this.allMap.get("unidade-medida")[0];

            if (!unidadeMedida.isEmpty()) {
                if (unidadeMedida.equalsIgnoreCase("ml")
                        || unidadeMedida.equalsIgnoreCase("g")) {
                    this.errorValidation.replace("unidadeMedida", false);
                }
            }
        }

        // Validar compra
        if (this.allMap.containsKey("valor-compra")) {
            String valorCompra = this.allMap.get("valor-compra")[0]
                    .replaceAll("\\.", "")
                    .replaceAll(",", ".");

            ValidationFloat validationFloat = new ValidationFloat();

            if (validationFloat.isValid(valorCompra)) {
                this.errorValidation.replace("valorCompra", false);
            }
        }

        // Validar venda
        if (this.allMap.containsKey("valor-venda")) {
            String valorVenda = this.allMap.get("valor-venda")[0]
                    .replaceAll("\\.", "")
                    .replaceAll(",", ".");

            ValidationFloat validationFloat = new ValidationFloat();

            if (validationFloat.isValid(valorVenda)) {
                this.errorValidation.replace("valorVenda", false);
            }
        }

        // Validar gênero
        if (this.allMap.containsKey("genero")) {
            String genero = this.allMap.get("genero")[0];

            if (!genero.isEmpty()) {
                if (genero.equalsIgnoreCase("masculino")
                        || genero.equalsIgnoreCase("feminino")) {
                    this.errorValidation.replace("genero", false);
                }
            }
        }

        // Validar descrição
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
        Produto produto = new Produto();
        ItensLoja itensLoja = new ItensLoja();

        try {
            if (!this.allMap.get("id")[0].isEmpty()) {
                produto.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }
            produto.setNome(this.allMap.get("nome")[0]);
            produto.setMarca(this.allMap.get("marca")[0]);
            produto.setCategoria(this.allMap.get("categoria")[0]);
            produto.setValorUnidadeMedida(Integer.valueOf(this.allMap
                    .get("valor-unidade-medida")[0]));
            produto.setUnidadeMedida(this.allMap.get("unidade-medida")[0]);
            produto.setGenero(this.allMap.get("genero")[0]);
            produto.setDescricao(this.allMap.get("descricao")[0]);

            if (!this.allMap.get("id")[0].isEmpty()) {
                itensLoja.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }
            itensLoja.setStatus(Boolean.valueOf(this.allMap.get("status")[0]));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataCadastro = new Date(sdf.parse(this.allMap
                    .get("data-cadastro")[0]).getTime());
            itensLoja.setDataCadastro(dataCadastro);

            itensLoja.setEstoque(Integer.valueOf(this.allMap
                    .get("estoque")[0]));

            String valorCompra = this.allMap.get("valor-compra")[0]
                    .replaceAll("\\.", "")
                    .replaceAll(",", ".");

            itensLoja.setValorCompra(Float.valueOf(valorCompra));

            String valorVenda = this.allMap.get("valor-venda")[0]
                    .replaceAll("\\.", "")
                    .replaceAll(",", ".");

            itensLoja.setValorVenda(Float.valueOf(valorVenda));

            itensLoja.setProduto(produto);

            // #MOCK
            Loja loja = new Loja();
            loja.setId(Integer.parseInt(this.allMap.get("loja")[0]));
            itensLoja.setLoja(loja);
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace(System.err);
            itensLoja = null;
        }

        return itensLoja;
    }
}
