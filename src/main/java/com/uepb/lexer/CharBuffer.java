package com.uepb.lexer;

import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

public class CharBuffer implements Closeable{

    private final PushbackReader reader;
    public static final int FILE_END = -1;

    public CharBuffer(String path) throws IOException {
        reader = new PushbackReader(new FileReader(path));
    }

    public int readNextChar() throws IOException {
        return reader.read();
    }

    public void pushback(int c) throws IOException {
        if (c != FILE_END) {
            reader.unread(c);
        }
    }

    public void close() throws IOException {
        reader.close();
    }

}
