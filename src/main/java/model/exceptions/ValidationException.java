package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

    //map é uma interface que mapeia valores pares-chave
        //aqui, cada campo receberá um erro, sendo assim o par = campo, erro
    public Map<String,String> errors = new HashMap<>();

    public ValidationException (String msg){
        super(msg);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String errorMessage){
        errors.put(fieldName,errorMessage);
    }
}
