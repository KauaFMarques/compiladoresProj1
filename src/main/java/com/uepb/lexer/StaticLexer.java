package com.uepb.lexer;

import java.io.IOException;

import com.uepb.token.Token;
import com.uepb.token.TokenType;

public class StaticLexer implements Lexer{
    
    private final CharBuffer reader;

    public StaticLexer(String programSource) throws IOException {
        reader = new CharBuffer(programSource);
    }

    public Token readNextToken() throws IOException {
        int readedChar;

        while ((readedChar = reader.readNextChar()) != CharBuffer.FILE_END) {
            char c = (char) readedChar;

            if (Character.isWhitespace(c)) {
                continue;
            }
        }

        return new Token(TokenType.EOF, null);
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}