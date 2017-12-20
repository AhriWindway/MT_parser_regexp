package ru.ifmo.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Анастасия on 05.10.2017.
 */
public class Tree {
    String node;
    List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
        this.children = Collections.emptyList();
    }

    public String getNode() {
        return node;
    }

    public String hash() {
        return new Integer(this.hashCode()).toString();
    }
}
