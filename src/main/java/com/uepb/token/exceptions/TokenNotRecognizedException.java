package com.uepb.token.exceptions;

public class TokenNotRecognizedException extends RuntimeException{
    
    public TokenNotRecognizedException(String message){
        super("Erro Léxico: " + message);
    }

}
