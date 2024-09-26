package com.uepb.lexer;

import com.uepb.token.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static final Map<String, String> tokenPatterns = new LinkedHashMap<>();
    private final String input;
    private int position = 0;
    private List<Token> tokens = new ArrayList<>();

    static {
        tokenPatterns.put("PLUS", "\\+");
        tokenPatterns.put("MINUS", "\\-");
        tokenPatterns.put("MULTIPLY", "\\*");
        tokenPatterns.put("DIVIDE", "/");
        // Alterar de \\*\\* para \\^
        tokenPatterns.put("EXPONENTIAL", "\\^");
        tokenPatterns.put("EQUAL", "=");
        tokenPatterns.put("DOUBLE_EQUAL", "==");
        tokenPatterns.put("SEMICOLON", ";");
        tokenPatterns.put("LPAREN", "\\(");
        tokenPatterns.put("RPAREN", "\\)");
        tokenPatterns.put("FLOAT", "[0-9]+\\.[0-9]+");
        tokenPatterns.put("INT", "[0-9]+");
        tokenPatterns.put("ID", "[a-zA-Z_][a-zA-Z_0-9]*");
        tokenPatterns.put("WS", "[ \\t\\r\\n]+"); // Ignorar espaços e quebras de linha
    }
    

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> lex() {
        while (position < input.length()) {
            boolean matched = false;
            for (Map.Entry<String, String> entry : tokenPatterns.entrySet()) {
                String tokenType = entry.getKey();
                String pattern = entry.getValue();
                Pattern regex = Pattern.compile("^" + pattern);
                Matcher matcher = regex.matcher(input.substring(position));
                if (matcher.find()) {
                    String tokenValue = matcher.group();
                    position += tokenValue.length();
                    if (!tokenType.equals("WS")) { // Ignorar espaços em branco
                        tokens.add(new Token(tokenType, tokenValue));
                    }
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                throw new RuntimeException("Erro léxico na posição: " + position);
            }
        }
        return tokens;
    }
}
