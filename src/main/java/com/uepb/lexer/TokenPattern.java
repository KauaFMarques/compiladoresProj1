package com.uepb.lexer;

import java.util.regex.Pattern;

public enum TokenPattern {

    WHITESPACE_PATTERN(Pattern.compile("\\s+"));

    private final Pattern pattern;

    private TokenPattern(Pattern pattern){
        this.pattern = pattern;
    }

    public Pattern getPattern(){
        return pattern;
    }
}
