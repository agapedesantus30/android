package edu.ggc.lutz.palindromeespresso;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EspressoTest1 {

    @Test
    public void palindrome_check1() {
        boolean result = Palindrome.check("aba");
        assertTrue(result);
    }

    @Test
    public void palindrome_check2() {
        boolean result = Palindrome.check("abc");
        assertFalse(result);
    }
}
