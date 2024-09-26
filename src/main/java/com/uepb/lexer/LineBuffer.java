package com.uepb.lexer;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;

public class LineBuffer implements Closeable{

    private final BufferedReader bufferedReader;
    private int fileRow, fileCol;
    private String currentLine;

    public LineBuffer(String path) throws IOException{
        this.bufferedReader = new BufferedReader(new FileReader(path));
        this.currentLine = bufferedReader.readLine();
        fileCol = 0;
        fileRow = 0;
    }

    public int getRow(){
        return fileRow;
    }

    public int getCol(){
        return fileCol;
    }

    public String getReadedLine(){
        return currentLine;
    }

    public char currentChar() {
        return currentLine.charAt(fileCol);
    }

    public void nextLine() throws IOException{
        this.currentLine = bufferedReader.readLine();
        fileRow++;
        fileCol = 0;
    }

    public String prefixMatches(TokenPattern pattern){
        var matcher = pattern.getPattern().matcher(currentLine.substring(fileCol));

        if(matcher.lookingAt()){
            String lexema = matcher.group();
            fileCol += lexema.length();
            return lexema;
        }

        return null;
    }

    @Override
    public void close() throws IOException {
        if(bufferedReader != null) bufferedReader.close();
    }
    
}
