package edu.ggc.lutz.palindromeespresso;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PalindromeTest {

    public static final String ONE_CHAR = "1";

    @Test
    public void palindrome_check1() {
        boolean result = Palindrome.check("radar");
        assertTrue(result);
    }

    @Test
    public void palindrome_check2() {
        boolean result = Palindrome.check("radarx");
        assertFalse(result);
    }

    @Test
    public void palindrome_check3() {

        boolean result = Palindrome.check("Rise to vote, sir!");
        assertEquals(true, result);
    }

    @Test
    public void palindrome_check4() {

        StringBuilder bigBuilder = new StringBuilder();
        for(int i = 0; i <= 999; i++)
            bigBuilder.append(ONE_CHAR);
        String big = bigBuilder.toString();
        boolean result = Palindrome.check(big);
        assertEquals(true, result);
    }

    @Test
    public void palindrome_check5() {

        StringBuilder bigBuilder = new StringBuilder();
        for(int i = 0; i <= 10001; i++)
            bigBuilder.append(ONE_CHAR);
        String big = bigBuilder.toString();
        try {
            boolean result = Palindrome.check(big);
            fail("palindrome_check5() test failed");
        } catch (IllegalArgumentException iae) {
            // assert
            System.out.println("Passed");
        }

    }

    @Test
    public void palindrome_check6() {
        boolean result = Palindrome.check("99");
        assertEquals(true, result);
    }

    @Test
    public void palindrome_check7() {
        boolean result = Palindrome.check("98");
        assertEquals(false, result);

    }

    @Test
    public void palindrome_check8() {
        boolean result = Palindrome.check("");
        assertEquals(true, result);

    }
}