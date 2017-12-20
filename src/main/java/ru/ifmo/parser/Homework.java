package ru.ifmo.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.*;

/**
 * Created by Анастасия on 05.10.2017.
 */
public class Homework {
    static Parser p = new Parser();

    public static void main(String[] args) {
        String s = "a|b";
        boolean result = false;
        try {
            InputStream is = new ByteArrayInputStream(s.getBytes("UTF-8"));
            result = p.parse(is);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if (result) displayTree();
    }

    private static void displayTree() {
        Graph g = new SingleGraph("Parser homework");
        g.addAttribute("ui.stylesheet", "node { size: 30px, 30px; text-size: 40;}");

        Tree root = p.getRoot();
        Node start = g.addNode(root.hash());
        start.addAttribute("ui.style", "fill-color: rgb(255,0,0);");
        start.addAttribute("ui.label", root.getNode());

        g.display();
        fillGraph(g, root);
    }

    private static void fillGraph(Graph g, Tree t) {
        if (t.children.isEmpty()) return;
        for (Tree child: t.children) {
            g.addNode(child.hash()).addAttribute("ui.label", child.getNode());;
            g.addEdge(t.hash() + child.hash(), t.hash(), child.hash());

            fillGraph(g, child);
        }
    }
}
