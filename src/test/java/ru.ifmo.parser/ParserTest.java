package ru.ifmo.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ParserTest {
    private static Parser p;

    @BeforeClass
    public static void initParser() {
        p = new Parser();
    }

    @After
    public void afterEachTest() {
        System.out.println();
    }

    @Test
    public void test() {
        successTest("ab");
    }

    @Test
    public void testBar() {
        successTest("a|b");
    }

    @Test
    public void testEmpty() {
        successTest("");
    }

    @Test
    public void testStar() {
        successTest("a*b");
    }

    @Test
    public void testParens() {
        successTest("(ab)*");
    }

    @Test
    public void testTwoGroups() {
        successTest("(ab)**(cd)");
    }

    @Test
    public void testTwoGroups2() {
        successTest("(ab)|(cd)");
    }

    @Test
    public void testTwoGroups3() {
        successTest("(ab)(cd)");
    }

    @Test
    public void testLong() {
        successTest("abc*(cde|aaaaaaaa)*");
    }

    @Test
    public void hardTest() {
        successTest("((abc*b|a)*ab(aa|b*)b)*");
    }

    @Test
    public void testPlus() { successTest("(abc*b|a)+"); }
    @Test
    public void testWrongChar() {
        failTest("a1a");
    }

    @Test
    public void testWrongStar() {
        failTest("*");
    }

    @Test
    public void testWrongStar2() {
        failTest("*aaaa");
    }

    @Test
    public void testNotBalancedOne() {
        failTest("(ab))");
    }

    @Test
    public void testNotBalancedTwo() {
        failTest("((ab)");
    }

    @Test
    public void testTwoBars() {
        failTest("a||b");
    }

    private void successTest(String s) {
        assertTrue(test(s));
    }

    private void failTest(String s) {
        assertFalse(test(s));
    }

    private boolean test(String s) {
        boolean res = false;
        try {
            InputStream is = new ByteArrayInputStream(s.getBytes("UTF-8"));
            res = p.parse(is);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            return res;
        }
    }
}