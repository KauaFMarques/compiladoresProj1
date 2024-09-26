package com.uepb.lexer;

import java.io.IOException;

import com.uepb.token.Token;
import com.uepb.token.TokenType;

public class RegexLexer implements Lexer {

    private LineBuffer buffer;

    public RegexLexer(String filePath) throws IOException {
        this.buffer = new LineBuffer(filePath);
    }

    @Override
    public Token readNextToken() throws IOException {

        String tokenValue;
        while (buffer.getReadedLine() != null) {
            if (buffer.getCol() >= buffer.getReadedLine().length()) {
                buffer.nextLine();
                continue;
            }            

            if ((tokenValue = buffer.prefixMatches(TokenPattern.WHITESPACE_PATTERN)) != null)
                continue;
            
            TokenType type = null;

            return new Token(type, tokenValue);
        }

        return new Token(TokenType.EOF, null);
    }

    @Override
    public void close() throws IOException {
        buffer.close();
    }
}