package com.uepb.parser;

import java.util.List;
import com.uepb.token.*;

public class Parser {
    private final List<Token> tokens;
    private int currentTokenIndex = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token lookahead() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex);
        }
        return null; // Representa o fim do fluxo de tokens
    }

    private Token match(String tokenType) {
        Token token = lookahead();
        if (token != null && token.type.equals(tokenType)) {
            currentTokenIndex++;
            return token;
        }
        throw new RuntimeException("Erro sintático: esperado " + tokenType + " mas encontrado " + (token != null ? token.type : "EOF"));
    }

    // Programa : expr (SEMICOLON expr)* SEMICOLON?
    public void program() {
        expr();
        while (lookahead() != null && lookahead().type.equals("SEMICOLON")) {
            match("SEMICOLON");
            if (lookahead() != null) {
                expr();
            }
        }
    }

    private void expr() {
        term();
        while (lookahead() != null && (lookahead().type.equals("PLUS") || lookahead().type.equals("MINUS"))) {
            match(lookahead().type);
            term();
        }
    }

    private void term() {
        factor();
        while (lookahead() != null && (lookahead().type.equals("MULTIPLY") || lookahead().type.equals("DIVIDE"))) {
            match(lookahead().type);
            factor();
        }
    }

    private void factor() {
        if (lookahead() != null) {
            if (lookahead().type.equals("INT") || lookahead().type.equals("FLOAT") || lookahead().type.equals("ID")) {
                match(lookahead().type); // Consume INT, FLOAT, or ID token
            } else if (lookahead().type.equals("LPAREN")) { 
                match("LPAREN");
                expr(); // Parenteses para expressões
                match("RPAREN");
            } else if (lookahead().type.equals("EXPONENTIAL")) { // Atualizar para o token EXPONENTIAL
                match("EXPONENTIAL");
                factor(); // Consome o próximo fator
            } else {
                throw new RuntimeException("Erro sintático: esperado INT, FLOAT, ID ou LPAREN, mas encontrado " + lookahead().type);
            }
        } else {
            throw new RuntimeException("Erro sintático: esperado INT, FLOAT, ID ou LPAREN, mas não há mais tokens");
        }
    }
    

    // base : ref | LPAREN expr RPAREN
    private void base() {
        if (lookahead().type.equals("LPAREN")) {
            match("LPAREN");
            expr();
            match("RPAREN");
        } else {
            ref();
        }
    }

    // ref : INT | FLOAT | ID | ref EQUAL ref | ref DOUBLE_EQUAL ref
    private void ref() {
        if (lookahead().type.equals("INT") || lookahead().type.equals("FLOAT") || lookahead().type.equals("ID")) {
            match(lookahead().type); // Consome INT, FLOAT ou ID
        } else if (lookahead().type.equals("ID")) {
            match("ID");
            if (lookahead().type.equals("EQUAL")) {
                match("EQUAL");
                ref();
            } else if (lookahead().type.equals("DOUBLE_EQUAL")) {
                match("DOUBLE_EQUAL");
                ref();
            }
        } else {
            throw new RuntimeException("Erro sintático em ref: esperado INT, FLOAT ou ID");
        }
    }

}