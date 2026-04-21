package edu.ggc.lutz.dicenotationtdd;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DiceNotationTDDTest {

    @Test
    public void isValid4d10() {
        boolean valid = DiceNotation.isValid("4d10");
        assertTrue(valid);
    }

    @Test
    public void isValid4dx() {
        boolean valid = DiceNotation.isValid("4dx");
        assertFalse(valid);    }

    @Test
    public void roll4d10() {
        DiceNotation dn = new DiceNotation("4d10");
        for (int i = 0; i<50; i++) {
            int chance = dn.roll();
            System.out.println("chance =" + chance);
            assertTrue(chance >= 4 && chance <= 40);
        }
    }
}
