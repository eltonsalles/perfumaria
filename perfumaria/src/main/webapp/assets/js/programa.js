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
    carregaProdutos();
    carregarEndereco();
    adicionarLinha();
    removerLinha();
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
}

/**
 * Carrega as informações que serão comuns nos produtos para qualquer loja
 * 
 * @returns
 */
function carregaProdutos() {
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

function adicionarLinha() {
    var cont = 2;
    var btn = document.querySelector('#add-linha');
    if (btn !== null) {
        btn.addEventListener('click', function () {

            var tBody = document.querySelector('#tblvenda tbody');
            var tr = document.createElement('tr');
            tr.setAttribute('id', "a" + cont);
            for (var i = 0; i <= 6; i++) {
                var td = document.createElement('td');
                // Crie aqui os inputs que são necessários
                if (i == 0) {
                    td.innerHTML = '<input type="text" style="border:0;" name="codigo" maxlength="150" placeholder="ID do produto"pattern="[0-9]+$"required/>';
                } else if (i == 1) {
                    td.innerHTML = '<input type="text" style="border:0;" name="produto" maxlength="150" placeholder="Nome do produto"required/>';
                } else if (i == 2) {
                    td.innerHTML = '<input type="text" style="border:0;" name="marca" maxlength="50" placeholder="Marca"required/>';
                } else if (i == 3) {
                    td.innerHTML = '<input type="number" style="border:0;" name="quantidade" maxlength="3" placeholder="Quantidade" pattern="[0-9]+$"required/>'
                } else if (i == 4) {
                    td.innerHTML = '<input type="text" style="border:0;" name="preco-unidade" maxlength="15" placeholder="Preço Unidade" pattern="([0-9]{1,3}\.)?[0-9]{1,3},[0-9]{2}$"required/>';
                } else if (i == 5) {
                    td.innerHTML = '<input type="text" style="border:0;" name="preco-total" maxlength="15" placeholder="Sub-Total" pattern="([0-9]{1,3}\.)?[0-9]{1,3},[0-9]{2}$"required/>';
                } else {
                    td.innerHTML = '<a href="#" class="btn-excluir btn btn-default" role="button" title="Remover"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>'


                }


                tr.appendChild(td);
            }

            tBody.appendChild(tr);
            cont++;
            removerLinha();
        });
    }

}

function removerLinha() {
    var btns = document.querySelectorAll(".btn-excluir");


//    for (var i = 0; i < btns.length; i++) {
//        btns[i].addEventListener("click", function () {
//            var id = "#a" + i;
//            var tr = document.querySelector(id);
//           
//                tr.parentNode.removeChild(tr);
//            
//            
//        })
//    }
    
    btns.forEach(function (btn) {
            var id = btn.getAttribute('id');
            var tr = document.querySelector(id);
           
                tr.parentNode.removeChild(tr); 
    });
}
