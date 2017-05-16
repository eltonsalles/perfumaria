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
}

function configurarForm() {
    var cpf = document.querySelector("#cpf");
    var celular = document.querySelector("#celular");
    var telefone = document.querySelector("#telefone");
    var cep = document.querySelector("#cep");
    
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
           this.value = formatarTelefone(this.value);
        });
    }
}

function formatarCpf(value) {
    var pattern = /^([\d]{3})([\d]{3})([\d]{3})([\d]{2})$/;
    value = value.replace(/[^\d]+/g, '');
    value = value.replace(pattern, '$1.$2.$3-$4');
    
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
