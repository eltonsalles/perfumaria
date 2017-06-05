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
window.addEventListener('load', init);

function init() {
    configurarForm();
    configurarLista();
    carregarProdutos();
    carregarEndereco();
    selects();
    inserirProduto();
    carregarCliente();
    carregarProduto();
}

function configurarForm() {
    var cpf = document.querySelector("#cpf");
    var celular = document.querySelector("#celular");
    var telefone = document.querySelector("#telefone");
    var cep = document.querySelector("#cep");
    var cnpj = document.querySelector("#cnpj");
    var valorCompra = document.querySelector("#valor-compra");
    var valorVenda = document.querySelector("#valor-venda");
    var dataCadastro = document.querySelector("#data-cadastro");
    
    if (cpf !== null) {
        cpf.value = formatarCpf(cpf.value);
        cpf.addEventListener('focusout', function () {
            this.value = formatarCpf(this.value);
        });
    }
    
    if (celular !== null) {
        celular.value = formatarTelefone(celular.value);
        celular.addEventListener('focusout', function () {
           this.value = formatarTelefone(this.value);
        });
    }
    
    if (telefone !== null) {
        telefone.value = formatarTelefone(telefone.value);
        telefone.addEventListener('focusout', function () {
           this.value = formatarTelefone(this.value);
        });
    }
    
    if (cep !== null) {
        cep.value = formatarCep(cep.value);
        cep.addEventListener('focusout', function () {
           this.value = formatarCep(this.value);
        });
    }
    
    if (cnpj !== null) {
        cnpj.value = formatarCnpj(cnpj.value);
        cnpj.addEventListener('focusout', function () {
           this.value = formatarCnpj(this.value);
        });
    }
    
    if (valorCompra !== null) {
        valorCompra.value = formatarDinheiro(valorCompra.value);
        valorCompra.addEventListener('focusout', function () {
            this.value = formatarDinheiro(this.value);
        });
    }
    
    if (valorVenda !== null) {
        valorVenda.value = formatarDinheiro(valorVenda.value);
        valorVenda.addEventListener('focusout', function () {
            this.value = formatarDinheiro(this.value);
        });
    }
    
    if (dataCadastro !== null) {
        if (dataCadastro.value === '') {
            incluirDataDoDia(dataCadastro);
        }
    }
}

function formatarCpf(value) {
    var pattern = /^([\d]{3})([\d]{3})([\d]{3})([\d]{2})$/;
    value = value.replace(/[^\d]+/g, '');
    value = value.replace(pattern, '$1.$2.$3-$4');
    
    return value;
}

function formatarCnpj(value) {
    var pattern = /^([\d]{2})([\d]{3})([\d]{3})([\d]{4})([\d]{2})$/;
    value = value.replace(/[^\d]+/g, '');
    value = value.replace(pattern, '$1.$2.$3/$4-$5');
    
    return value;
}

function formatarTelefone(value) {
    var pattern = /^([\d]{2})([\d]{4,5})([\d]{4})$/;
    value = value.replace(/[^\d]+/g, '');
    value = value.replace(pattern, '($1) $2-$3');
    
    return value;
}

function formatarCep(value) {
    var pattern = /^([\d]{5})([\d]{3})$/;
    value = value.replace(/[^\d]+/g, '');
    value = value.replace(pattern, '$1-$2');
    
    return value;
}

function formatarDinheiro(value) {
    var test = value;
    test = test.replace(/[\D]+/g, '');
    
    // Só formata se for um número
    if (parseInt(test)) {
        test += '';
        test = test.replace(/([0-9]{2})$/g, ',$1');
        if (test.length > 6) {
            test = test.replace(/([0-9]{3}),([0-9]{2}$)/g, '.$1,$2');
        }
        
        return test;
    }
    
    return value;
}

function incluirDataDoDia(field) {
    field.value = new Date().toISOString().slice(0, 10);
}

function configurarLista() {
    var cpfs = document.querySelectorAll('.cpfs');
    var cnpjs = document.querySelectorAll('.cnpjs');
    var dinheiro = document.querySelectorAll('.dinheiro');
    var dataInicial = document.querySelector('#data-inicial');
    var dataFinal = document.querySelector('#data-final');
    
    if (cpfs !== null) {
        cpfs.forEach(function (td){
           td.textContent = formatarCpf(td.textContent);
        });
    }
    
    if (cnpjs !== null) {
        cnpjs.forEach(function (td){
           td.textContent = formatarCnpj(td.textContent);
        });
    }
    
    if (dinheiro !== null) {
        dinheiro.forEach(function (td) {
            td.textContent = "R$ " + formatarDinheiro(td.textContent);
        });
    }
    
    if (dataInicial !== null) {
        if (dataInicial.value === '') {
            incluirDataDoDia(dataInicial);
            var data = dataInicial.value.split("-");
            dataInicial.value = data[0] + "-" + data[1] + "-01";
        }
    }
    
    if (dataFinal !== null) {
        if (dataFinal.value === '') {
            incluirDataDoDia(dataFinal);
        }
    }
}

/**
 * Carrega as informações que serão comuns nos produtos para qualquer loja
 * 
 * @returns
 */
function carregarProdutos() {
    var produtos = document.querySelector("#form-produtos");
    
    if (produtos !== null) {
        var nome = document.querySelector("#nome");

        nome.addEventListener('keyup', function () {
            var pattern = /[a-zA-Z\u00C0-\u00FF0-9]|\.|-|\s+$/i; // ^([a-zA-Zà-úÀ-Ú0-9]|\.|-|\s)+$
            
            if (this.value.length > 2 && pattern.test(this.value)) {
                $.ajax({
                    url : location.origin + '/perfumaria/sistema?controller=Produto&action=produtos&nome=' + this.value,
                    dataType : 'json',
                    contentType:"application/json",
                    error : function() {
                        alert("Error");
                    },
                    success : function(data) {
                        var categoria = document.querySelector("#categoria");
                        var marca = document.querySelector("#marca");
                        var valorUnidadeMedida = document.querySelector("#valor-unidade-medida");
                        var unidadeMedida = document.querySelector("#unidade-medida");
                        var genero = document.querySelector("#genero");
                        var descricao = document.querySelector("#descricao");
                            
                        var lista = data.filter(filtrarNome);
                        if (lista.length === 1) {
                            categoria.value = lista[0].categoria;
                            marca.value = lista[0].marca;
                            valorUnidadeMedida.value = lista[0].valorUnidadeMedida;
                            unidadeMedida.value = lista[0].unidadeMedida;
                            genero.value = lista[0].genero;
                            descricao.value = lista[0].descricao;
                        } else {
                            categoria.value = "";
                            marca.value = "";
                            valorUnidadeMedida.value = "";
                            unidadeMedida.value = "";
                            genero.value = "";
                            descricao.value = "";
                        }
                    }
                });
            }
        });
    }
}

function filtrarNome(obj) {
    var value = document.querySelector("#nome").value;
    
    return obj.nome.toUpperCase() === value.toUpperCase();
}

function carregarEndereco() {
    var cep = document.querySelector("#cep");
    
    if (cep !== null) {
        cep.addEventListener('keyup', function (e) {
            var buscarCep = this.value.replace(/\D/, '');
            var codigo = e.keyCode;
            
            // Faz a busca se existir 8 números no buscarCep e se a tecla
            // pressionado no teclado estiver entre os códigos abaixo
            if (buscarCep.length === 8 && (codigo >= 48 && codigo <= 57
                    || codigo >= 96 && codigo <= 105)) {
                $.ajax({
                    url : 'https://viacep.com.br/ws/' + buscarCep + '/json/',
                    dataType : 'json',
                    contentType:"application/json",
                    error : function() {
                        alert("Error");
                    },
                    success : function(data) {
                        var logradouro = document.querySelector("#logradouro");
                        var complemento = document.querySelector("#complemento");
                        var bairro = document.querySelector("#bairro");
                        var cidade = document.querySelector("#cidade");
                        var uf = document.querySelector("#uf");
                                                
                        if (!data.erro) {
                            logradouro.value = data.logradouro;
                            complemento.value = data.complemento;
                            bairro.value = data.bairro;
                            cidade.value = data.localidade;
                            uf.value = data.uf;
                            
                            var numero = document.querySelector("#numero");
                            numero.focus();
                        } else {
                            logradouro.value = "";
                            complemento.value = "";
                            bairro.value = "";
                            cidade.value = "";
                            uf.value = "";
                        }
                    }
                });
            }
        });
    }
}

function selects() {
    var movimentarProduto = $("#movimentar-produto");
    
    if (movimentarProduto !== null) {
        var produto = $("#produto");
        var loja = $("#loja");
        
        // Verifica se existe algo selecionado nos campos produto e loja
        $(movimentarProduto).submit(function() {
            // Se os campos estiverem vazios, então não subimete p formulário
            if (produto.val() === "" || loja.val() === "") {
                return false;
            }
        });
        
        produto.chosen({
            placeholder_text_single: "Escolha um produto",
            no_results_text: "Opção não encontrada:",
            max_shown_results: 5
        });
        
        loja.chosen({
            placeholder_text_single: "Escolha uma loja",
            no_results_text: "Opção não encontrada:",
            max_shown_results: 5
        });
    }
}

function inserirProduto() {
    var btn = document.querySelector("#inserir-produto");
    
    if (btn !== null) {
        btn.addEventListener("click", function () {
            var table = document.querySelector("#lista-itens-venda tbody");
            var tr = document.querySelector("#lista-itens-venda tr:nth-of-type(2)");
            var clone = tr.cloneNode(true);

            table.appendChild(clone);

            var btns = document.querySelectorAll(".btn-excluir");
            var index = btns.length;
            removerProduto(btns[index - 1], index);
            
            var codigos = document.querySelectorAll(".codigo");
            buscarProdutoVenda(codigos[index - 1], index - 1);
        });
    }
}

function removerProduto(btn, index) {
    btn.addEventListener("click", function () {
        var table = document.querySelector("#lista-itens-venda");
        var trs = document.querySelectorAll("#lista-itens-venda tr");
        
        // Só deixa excluir a última linha.
        if (trs.length - 1 === index) {
            table.deleteRow(index);
        }
    });
}

/**
 * Carrega as informações do cliente para a venda
 * 
 * @returns
 */
function carregarCliente() {
    var venda = document.querySelector("#form-venda");
    
    if (venda !== null) {
        var cpf = document.querySelector("#cpf");

        cpf.addEventListener('keyup', function () {
            var nome = document.querySelector("#nome");
            var id = document.querySelector("#id-cliente");
                        
            if (this.value.length === 11 || this.value.length === 14) {
                $.ajax({
                    url : location.origin + '/perfumaria/sistema?controller=Cliente&action=cliente&cpf=' + this.value,
                    dataType : 'json',
                    contentType:"application/json",
                    error : function() {
                        alert("Error");
                    },
                    success : function(data) {                        
                        nome.value = data.nome;
                        id.value = data.id;
                    }
                });
            } else {
                nome.value = "";
                id.value = "";
            }
        });
    }
}

/**
 * Carrega as informações do produto para a venda
 * 
 * @returns
 */
function carregarProduto() {
    var venda = document.querySelector("#form-venda");
    
    if (venda !== null) {
        var codigo = document.querySelectorAll(".codigo")[0];
        buscaProdutoVenda(codigo, 0);
        
        var quantidade = document.querySelectorAll(".quantidade")[0];
        quantidade.addEventListener("click", function () {
            var precoUnidade = document.querySelectorAll(".preco-unidade")[0];
            var precoTotal = document.querySelectorAll(".preco-total")[0];
            
            console.log(this.value);
            console.log(precoUnidade.value);
            
            precoTotal.value = (this.value * precoUnidade.value.replace(",", ".")).toLocaleString("pt-BR", {minimumFractionDigits: 2});
        });
    }
}

/**
 * Quando adicionado mais itens na 
 * 
 * @param {type} field
 * @param {type} i
 * @returns {undefined}
 */
function buscarProdutoVenda(field, i) {
    field.addEventListener('keyup', function () {
        var produto = document.querySelectorAll(".produto")[i];
        var marca = document.querySelectorAll(".marca")[i];
        var precoUnitario = document.querySelectorAll(".preco-unidade")[i];

        var pattern = /^[0-9]+$/i;

        if (pattern.test(this.value)) {
            $.ajax({
                url : location.origin + '/perfumaria/sistema?controller=Produto&action=produto&id=' + this.value,
                dataType : 'json',
                contentType:"application/json",
                error : function() {
                    alert("Error");
                },
                success : function(data) {
                    produto.value = data.nome;
                    marca.value = data.marca;
                    precoUnitario.value = data.valorVenda.toLocaleString("pt-BR", {minimumFractionDigits: 2});
                }
            });
        } else {
            var precoTotal = document.querySelectorAll(".preco-total")[i];

            produto.value = "";
            marca.value = "";
            precoUnitario.value = "";
            precoTotal.value = "";
        }
    });
}