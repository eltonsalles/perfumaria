/*
 * The MIT License
 *
 * Copyright 2017 Joao.
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
package br.senac.tads.pi3a.validation;

/**
 *
 * @author Joao
 */
public class validationCpf extends validationAbstract {

    @Override
    public boolean isValid(Object objeto) {

        int d1, d2, digito1, digito2, resto, digitoCPF;
        String digVerificador, digResultado, cpf = objeto.toString();

        // Deixa apenas os números
        cpf = cpf.replaceAll("[^0-9]*", "");
        if (cpf.trim().isEmpty()) {
            return false;
        }

        if (cpf.equals("00000000000") || cpf.equals("11111111111")
                || cpf.equals("22222222222") || cpf.equals("33333333333")
                || cpf.equals("44444444444") || cpf.equals("55555555555")
                || cpf.equals("66666666666") || cpf.equals("77777777777")
                || cpf.equals("88888888888") || cpf.equals("99999999999")) {
            return false;
        }

        // Inicializa as variáveis
        d1 = 0;
        d2 = 0;

        for (int i = 1; i < cpf.length() - 1; i++) {
            // Converte para integer
            digitoCPF = Integer.valueOf(cpf.substring(i - 1, i));

            // Faz a soma das multiplicações
            d1 = d1 + (11 - i) * digitoCPF;
            d2 = d2 + (12 - i) * digitoCPF;
        }

        // Faz as contas para verificar se o 10º número está correto
        resto = (d1 % 11);
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        // Faz as contas para verificar se o 11º número está correto
        resto = (d2 % 11);
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        // Compara os resultados das contas com os valores digitados
        digVerificador = cpf.substring(cpf.length() - 2, cpf.length());
        digResultado = String.valueOf(digito1) + String.valueOf(digito2);

        // Se o cpf for válido retorna apenas os números
        if (digVerificador.equals(digResultado)) {
            return true;
        }

        // Se for inválido retorna false
        return false;
    }

}
