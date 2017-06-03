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

import br.senac.tads.pi3a.model.Cliente;
import br.senac.tads.pi3a.model.Funcionario;
import br.senac.tads.pi3a.model.ItensVenda;
import br.senac.tads.pi3a.model.Loja;
import br.senac.tads.pi3a.model.Model;
import br.senac.tads.pi3a.model.Venda;
import br.senac.tads.pi3a.validation.ValidationCpf;
import br.senac.tads.pi3a.validation.ValidationInt;
import br.senac.tads.pi3a.validation.ValidationString;
import br.senac.tads.pi3a.validation.ValidationTamanho;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        ValidationInt validationInt = new ValidationInt();
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

        // Validar nome produto
        if (this.allMap.containsKey("nome")) {
            validationTamanho.setTamanho(150);

            if (validationTamanho.isValid(this.allMap.get("nome")[0])
                    && validationString.isValid(this.allMap.get("nome")[0])) {
                this.errorValidation.replace("nome", false);
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
        if (this.allMap.containsKey("quantidade")) {
            String estoque = this.allMap.get("quantidade")[0]
                    .replaceAll("\\D", "");

            if (validationInt.isValid(estoque)) {
                this.errorValidation.replace("quantidade", false);
                this.allMap.replace("quantidade", new String[]{estoque});
            }

        }
        return this.errorStatus();

    }

    @Override
    protected Model getModel() {
        Venda venda = new Venda();
        Cliente cliente = new Cliente();
        Funcionario funcionario = new Funcionario();
        Loja loja = new Loja();
        ItensVenda itensVenda = new ItensVenda();

        try {

            //VENDA
            if (!this.allMap.get("id")[0].isEmpty()) {
                venda.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }

            //INSERIR DATA ATUAL DO CADASTRO DA VENDA
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataCadastro = new Date(sdf.parse(this.allMap
                    .get("data-cadastro")[0]).getTime());
            venda.setData(dataCadastro);
            //valor-venda-total
             String valorVenda = this.allMap.get("preco-venda")[0]
                    .replaceAll("\\.", "")
                    .replaceAll(",", ".");

            venda.setValorVenda(Float.valueOf(valorVenda));
            
            //CLIENTE
            if (!this.allMap.get("id-cliente")[0].isEmpty()) {
                cliente.setId(Integer.valueOf(this.allMap.get("id-cliente")[0]));
            }
            cliente.setCpf(this.allMap.get("cpf")[0]);
            
            //FUNCIONARIO
            if (!this.allMap.get("id")[0].isEmpty()) {
                funcionario.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }
            
            //Loja
            if (!this.allMap.get("id")[0].isEmpty()) {
                loja.setId(Integer.valueOf(this.allMap.get("id")[0]));
            }
            
            //Itens_Venda
            itensVenda.setVenda(venda);
            
            //#MOCK
            loja.setId(1);
            funcionario.setId(1);
            
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace(System.err);
            itensVenda= null;
        }
        return venda;
    }
}
