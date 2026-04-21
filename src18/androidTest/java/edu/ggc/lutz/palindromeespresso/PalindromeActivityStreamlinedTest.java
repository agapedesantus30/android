package edu.ggc.lutz.palindromeespresso;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PalindromeActivityStreamlinedTest {

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }

    @Rule
    public ActivityTestRule<PalindromeActivity> mActivityTestRule = new ActivityTestRule<>(PalindromeActivity.class);

    @Test
    public void palindromeActivityTestHappy1() {
        onView(withId(R.id.etInput)).perform(replaceText("radar"));
        onView(withId(R.id.etInput)).perform(closeSoftKeyboard());
        onView(withId(R.id.tvResult)).check(matches(withText("Is a Palindrome")));
        onView(withId(R.id.ivResult)).check(matches(withDrawable(R.drawable.check)));
    }

    @Test
    public void palindromeActivityTestHappy2() {
        onView(withId(R.id.etInput)).perform(replaceText("radarx"));
        onView(withId(R.id.etInput)).perform(closeSoftKeyboard());
        onView(withId(R.id.tvResult)).check(matches(withText("Is NOT a Palindrome")));
        onView(withId(R.id.ivResult)).check(matches(withDrawable(R.drawable.x)));
    }

}
