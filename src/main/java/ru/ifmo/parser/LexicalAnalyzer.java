package ru.ifmo.parser;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by Анастасия on 05.10.2017.
 */
public class LexicalAnalyzer {
    InputStream is;
    int curChar, curPos;
    Token curToken;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    private boolean isBlank() {
        return curChar == '_' || curChar == '\r' || curChar == '\n' || curChar == '\t';
    }

    public void nextToken() throws ParseException {
        while (isBlank()) nextChar();
        System.out.print((char) curChar + " ");
        if (Character.isLowerCase(curChar)) {
            nextChar();
            curToken = Token.LETTER;
            return;
        }

        switch (curChar) {
            case '(':
                nextChar();
                curToken = Token.LPAREN;
                break;
            case ')':
                nextChar();
                curToken = Token.RPAREN;
                break;
            case '|':
                nextChar();
                curToken = Token.BAR;
                break;
            case '*':
                nextChar();
                curToken = Token.STAR;
                break;
            case '+':
                nextChar();
                curToken = Token.PLUS;
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                throw new ParseException("Illegal character " + (char) curChar, curPos);
        }
    }

    public Token getCurToken() {
        return curToken;
    }

    public int getCurPos() {
        return curPos;
    }
}
