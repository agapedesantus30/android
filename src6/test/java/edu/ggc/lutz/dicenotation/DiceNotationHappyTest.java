package edu.ggc.lutz.dicenotation;

import com.google.common.collect.Range;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.google.common.truth.Truth.assertThat;

@RunWith(Parameterized.class)
public class DiceNotationHappyTest {

    private static final int TRIALS = 1000;
    private Integer min;
    private Integer max;
    private String inputNotation;
    private String expectedToString;

    public DiceNotationHappyTest(Integer min, Integer max, String inputNotation, String expectedToString) {
        this.min = min;
        this.max = max;
        this.inputNotation = inputNotation;
        this.expectedToString = expectedToString;
    }

    @Parameterized.Parameters
    public static Collection inputs() {
        return Arrays.asList(new Object[][] {
                { 1, 6, "d6",  "DiceNotation{a=1, b=6, c=1, d=0, groupAdd='null', groupMult='null', groupMath='null', notation='d6'}"},
                { 3, 18, "3d6", "DiceNotation{a=3, b=6, c=1, d=0, groupAdd='null', groupMult='null', groupMath='null', notation='3d6'}"},
                { 5, 25, "4d6+1", "DiceNotation{a=4, b=6, c=1, d=1, groupAdd='+1', groupMult='null', groupMath='+1', notation='4d6+1'}"},
                { 30, 180, "3d6x10", "DiceNotation{a=3, b=6, c=10, d=0, groupAdd='null', groupMult='x10', groupMath='x10', notation='3d6x10'}"},
                { 0, 3, "d6/2", "DiceNotation{a=1, b=6, c=2, d=0, groupAdd='null', groupMult='/2', groupMath='/2', notation='d6/2'}"},
                { -6, -1, "3d4/2-7", "DiceNotation{a=3, b=4, c=2, d=7, groupAdd='-7', groupMult='/2', groupMath='/2-7', notation='3d4/2-7'}"},
        });
    }

    @Test
    public void happyCreate() {
        DiceNotation dn = new DiceNotation(inputNotation);
        assertThat(new DiceNotation(inputNotation).toString()
                .equals(expectedToString)).isTrue();
    }

    @Test
    public void happyRoll() {
        Integer chance = new DiceNotation(inputNotation).roll();
        for (int i = 0; i < TRIALS; i++) {
            assertThat(chance).isIn(Range.closed(min, max));
        }
    }
}