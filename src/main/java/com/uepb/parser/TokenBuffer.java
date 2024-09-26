package com.uepb.parser;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;

import com.uepb.lexer.Lexer;
import com.uepb.parser.exceptions.SyntaxError;
import com.uepb.token.Token;
import com.uepb.token.TokenType;

public class TokenBuffer implements Closeable{

    private final int SIZE;
    private boolean reachedEndOfFile;
    private final LinkedList<Token> buffer;
    private final Lexer lexer;

    public TokenBuffer(Lexer lexer) throws IOException{
        SIZE = 10;
        buffer = new LinkedList<>();
        this.lexer = lexer;
        reachedEndOfFile = false;
        confirmToken();
    }

    private void confirmToken() throws IOException {        
        if(!buffer.isEmpty()) 
            buffer.poll();
        
        while(buffer.size() < SIZE && !reachedEndOfFile){
            var tk = lexer.readNextToken();
            buffer.addLast(tk);

            if(tk.type() == TokenType.EOF)
                reachedEndOfFile = true;
        }
    }

    public Token lookAhead(int k){
        if(buffer.isEmpty()) 
            return null;

        k = k-1 < 0 ? 0 : k-1;
        return k >= buffer.size() ? buffer.getLast() : buffer.get(k);
    }

    public void match(TokenType type) throws IOException{
        var la = lookAhead(1);

        if(la.type() == type){
            return;
        }

        throw new SyntaxError(la, type);
    }

    @Override
    public void close() throws IOException {
        if(lexer != null) lexer.close();
    }
    
}
