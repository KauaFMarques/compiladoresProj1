package com.uepb;

import java.io.IOException;
import java.util.List;

import com.uepb.lexer.Lexer;
import com.uepb.parser.ASTNode;
import com.uepb.token.Token;
import com.uepb.parser.*;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Caminho do arquivo que será lido
        String filePath = "C:\\Users\\ferre\\Projects\\Myproject-main\\JavaProjcs\\compiladores\\base_projeto_comp_un1\\code_example.uepb";
        
        try {
            // Lê o conteúdo do arquivo para uma string
            String input = new String(Files.readAllBytes(Paths.get(filePath)));

            // Cria o lexer e gera os tokens a partir do conteúdo do arquivo
            Lexer lexer = new Lexer(input);
            List<Token> tokens = lexer.lex();
            
            // Imprime os tokens gerados
            System.out.println("Tokens:");
            for (Token token : tokens) {
                System.out.println(token);
            }

            // Cria o parser e realiza a análise sintática
            Parser parser = new Parser(tokens);
            try {
                parser.program();  // Inicia a análise sintática pelo ponto de entrada 'program'
                System.out.println("Análise sintática concluída com sucesso.");
            } catch (Exception e) {
                System.err.println("Erro durante a análise sintática: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}