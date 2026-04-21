package edu.ggc.lutz.dicenotation;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.google.common.truth.Truth.assertThat;

@RunWith(Parameterized.class)
public class DiceNotationUnhappyTest {

    private String inputNotation;

    public DiceNotationUnhappyTest(String inputNotation) {
        this.inputNotation = inputNotation;
    }

    @Parameterized.Parameters
    public static Collection inputs() {
        return Arrays.asList(new Object[][] {
                { "" },
                { "12d4-" },
                { "d-8" },
                { "4dx" },
                { "FF" },
                { "I own eight pizza pies!" },
        });
    }

    @Test
    public void unhappyCreate() {
        try {
            new DiceNotation(inputNotation);
            TestCase.fail("fail!");
        } catch (IllegalArgumentException iae) {
            assertThat(iae instanceof IllegalArgumentException).isTrue();
        }
    }
}