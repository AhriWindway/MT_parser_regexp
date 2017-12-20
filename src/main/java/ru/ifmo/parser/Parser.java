package ru.ifmo.parser;
import java.io.InputStream;
import java.net.PasswordAuthentication;
import java.text.ParseException;

/**
 * Created by Анастасия on 05.10.2017.
 */
public class Parser {
    LexicalAnalyzer lex;
    Tree root;

    boolean parse(InputStream is) throws ParseException, AssertionError {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        root = E();
        System.out.println(lex.getCurToken());
        if (lex.getCurToken() == Token.END)
            return true;
        return false;
    }

    Tree getRoot() {
        return root;
    }

    Tree E() throws ParseException {
        switch (lex.getCurToken()) {
            case LETTER:
            case LPAREN:
                Tree A = A();
                Tree EPrime = EPrime();
                return new Tree("E", A, EPrime);
            case END:
                return new Tree("eps");
            default:
                throw new ParseException("Unexpected token " + lex.getCurToken() + " at ", lex.getCurPos());
        }
    }

    Tree EPrime() throws ParseException {
        switch (lex.getCurToken()) {
            case BAR:
                lex.nextToken();
                Tree A = A();
                Tree EPrime = EPrime();
                return new Tree("E'", new Tree("|"), A, EPrime);
            case RPAREN:
            case END:
                return new Tree("eps");
            default:
                throw new ParseException("Unexpected token " + lex.getCurToken() + " at ", lex.getCurPos());
        }
    }

    Tree A() throws ParseException {
        switch (lex.getCurToken()) {
            case LETTER:
            case LPAREN:
                Tree C = C();
                Tree APrime = APrime();
                return new Tree("A", C, APrime);
            default:
                throw new ParseException("Unexpected token " + lex.getCurToken() + " at ", lex.getCurPos());
        }
    }

    Tree APrime() throws ParseException {
        switch (lex.getCurToken()) {
            case LETTER:
            case LPAREN:
                Tree C = C();
                Tree APrime = APrime();
                return new Tree("A'", C, APrime);
            case BAR:
            case END:
            case RPAREN:
                return new Tree("eps");
            default:
                throw new ParseException("Unexpected token " + lex.getCurToken() + " at ", lex.getCurPos());
        }
    }

    Tree C() throws ParseException {
        switch (lex.getCurToken()) {
            case LETTER:
                lex.nextToken();
                Tree CPrime = CPrime();
                return new Tree("C", new Tree("n"), CPrime);
            case LPAREN:
                lex.nextToken();
                Tree E = E();
                if (lex.getCurToken() != Token.RPAREN) throw new ParseException(") expected at position ", lex.getCurPos());
                lex.nextToken();
                CPrime = CPrime();
                return new Tree("C", new Tree("("), E, new Tree(")"), CPrime);
            default:
                throw new ParseException("Unexpected token " + lex.getCurToken() + " at ", lex.getCurPos());
        }
    }

    Tree CPrime() throws ParseException {
        switch (lex.getCurToken()) {
            case STAR:
                lex.nextToken();
                Tree CPrime = CPrime();
                return new Tree("C'", new Tree("*"), CPrime);
            case PLUS:
                lex.nextToken();
                CPrime = CPrime();
                return new Tree("C'", new Tree("+"), CPrime);
            case END:
            case LPAREN:
            case BAR:
            case RPAREN:
            case LETTER:
                return new Tree("eps");
            default:
                throw new ParseException("Unexpected token " + lex.getCurToken() + " at ", lex.getCurPos());
        }
    }
}
