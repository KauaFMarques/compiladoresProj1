package com.uepb.parser;

public class ASTNode {
    public String value;
    public ASTNode left;
    public ASTNode right;

    public ASTNode(String value) {
        this.value = value;
    }
}

