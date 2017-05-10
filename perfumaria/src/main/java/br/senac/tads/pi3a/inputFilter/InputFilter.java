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
package br.senac.tads.pi3a.inputFilter;

import br.senac.tads.pi3a.model.Model;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Elton
 */
public abstract class InputFilter {
    protected Map<String, String[]> allMap;
    
    protected Map<String, Object> errorValidation;

    public InputFilter(HttpServletRequest request) {
        this.allMap = request.getParameterMap();
        this.errorValidation = initErrorValidation(this.allMap);
    }
    
    private Map<String, Object> initErrorValidation(Map<String, String[]> map) {
        Map<String, Object> error = new LinkedHashMap<>();
        
        for (String key : map.keySet()) {
            if (!key.equalsIgnoreCase("controller") && !key.equalsIgnoreCase("action")) {
                if (key.contains("-")) {
                    key = arrangeName(key);
                }
                error.put(key, true);
            }
        }
        
        return error;
    }
    
    private String arrangeName(String name) {
        String[] parts = name.split("-");
        String result = "";
        
        for (int i = 1; i < parts.length; i++) {
            char firstLetter = parts[i].charAt(0);
            parts[i] = String.valueOf(firstLetter).toUpperCase() + parts[i]
                    .substring(1);
        }
        
        for (String part : parts) {
            result += part;
        }
        
        return result;
    }
    
    public Map<String, Object> getErrorValidation() {
        return errorValidation;
    }
    
    public abstract boolean isValid();
    
    public abstract Model createModel();
}
