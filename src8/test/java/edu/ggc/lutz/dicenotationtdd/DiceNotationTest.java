package edu.ggc.lutz.dicenotationtdd;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DiceNotationTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("running @Before method");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("running @After method");
    }

    @Test
    public void isValid3d6() {
        boolean valid = DiceNotation.isValid("3d6");
        assertTrue(valid);
    }

    @Test
    public void roll3d6() {
        DiceNotation dn = new DiceNotation("3d6");
        for (int i = 0; i<10; i++) {
            int chance = dn.roll();
            System.out.println("chance =" + chance);
            assertTrue(chance >= 3 && chance <= 18);
        }
    }

    @Test
    public void isNotValidSpuriousChars() {
        boolean valid = DiceNotation.isValid("3dyyy6");
        assertFalse(valid);
    }

    @Test
    public void isNotValidEnptyString() {
        boolean valid = DiceNotation.isValid("");
        assertFalse(valid);
    }

    @Test
    public void rollAndCheckRange() {
        DiceNotation dn = new DiceNotation("d6");
        for (int i = 0; i < 100; i++) {
            int result = dn.roll();
            assertTrue(result >= 1 && result <= 6);
        }
    }
}