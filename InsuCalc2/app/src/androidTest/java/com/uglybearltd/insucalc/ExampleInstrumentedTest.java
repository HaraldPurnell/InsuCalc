package com.uglybearltd.insucalc;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.openLinkWithText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    public String insuDoseCheck;

    @Rule
    public ActivityTestRule<InsuCalc> mActivityRule = new ActivityTestRule<InsuCalc>(InsuCalc.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        insuDoseCheck = "00.0";
    }

    @Test
    public void IC1(){
        onView(withId(R.id.CBS)).perform(typeText("11.0"), closeSoftKeyboard());
        onView(withId(R.id.CV)).perform(typeText("60.0"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("42.0"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("10.0"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("8.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText("7.5")));
    }

    @Test
    public void IC2(){
        onView(withId(R.id.CBS)).perform(typeText("2.0"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("5.0"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("10.0"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("1.0"), closeSoftKeyboard());
        onView(withId(R.id.CV)).perform(typeText("1.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());

        try {
            synchronized(this){
                wait(7000);
            }
        }
        catch(InterruptedException ex){
        }

        onView(withId(R.id.insuDose)).check(matches(withText("0.5")));
    }

    @Test
    public void IC3(){
        onView(withId(R.id.CBS)).perform(typeText("22.0"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("8.0"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("20.0"), closeSoftKeyboard());
        onView(withId(R.id.CV)).perform(typeText("200.0"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("100.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText("24.0")));
    }

    @Test
    public void IC4() {
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.TBS)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.ICR)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.CV)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.DIA)).check(matches(hasErrorText("You must Input a number!")));
    }

    @Test
    public void IC5(){
        onView(withId(R.id.CBS)).perform(typeText("22.1"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("8.1"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("20.1"), closeSoftKeyboard());
        onView(withId(R.id.CV)).perform(typeText("200.1"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("100.1"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.CV)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.DIA)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.ICR)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.TBS)).check(matches(hasErrorText("Out of range!")));
    }

    @Test
    public void IC6(){
        onView(withId(R.id.CBS)).perform(typeText("0.9"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("4.9"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("0.9"), closeSoftKeyboard());
        onView(withId(R.id.CV)).perform(typeText("0.9"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("0.9"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.TBS)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.ICR)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.CV)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.DIA)).check(matches(hasErrorText("Out of range!")));
    }

    @Test
    public void IC7(){
        onView(withId(R.id.CBS)).perform(typeText("22.1"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("8.0"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("10.0"), closeSoftKeyboard());
        onView(withId(R.id.CV)).perform(typeText("60.0"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("42.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());

        try {
            synchronized(this){
                wait(7000);
            }
        }
        catch(InterruptedException ex){
        }

        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("Out of range!")));
    }

    @Test
    public void IC8(){
        onView(withId(R.id.CBS)).perform(typeText("22.1"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("8.1"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("10.0"), closeSoftKeyboard());
        onView(withId(R.id.CV)).perform(typeText("60.0"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("42.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.TBS)).check(matches(hasErrorText("Out of range!")));
    }

    @Test
    public void IC9(){
        onView(withId(R.id.CBS)).perform(typeText("22.1"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("8.1"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("20.1"), closeSoftKeyboard());
        onView(withId(R.id.CV)).perform(typeText("60.0"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("42.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.TBS)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.ICR)).check(matches(hasErrorText("Out of range!")));
    }

    @Test
    public void IC10(){
        onView(withId(R.id.CBS)).perform(typeText("22.1"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("8.1"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("20.1"), closeSoftKeyboard());
        onView(withId(R.id.CV)).perform(typeText("200.1"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("42.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.TBS)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.ICR)).check(matches(hasErrorText("Out of range!")));
        onView(withId(R.id.CV)).check(matches(hasErrorText("Out of range!")));
    }

    @Test
    public void IC11(){
        onView(withId(R.id.CV)).perform(typeText("60.0"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("42.0"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("10.0"), closeSoftKeyboard());
        onView(withId(R.id.TBS)).perform(typeText("8.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("You must Input a number!")));
    }

    @Test
    public void IC12(){
        onView(withId(R.id.CV)).perform(typeText("60.0"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("42.0"), closeSoftKeyboard());
        onView(withId(R.id.ICR)).perform(typeText("10.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.TBS)).check(matches(hasErrorText("You must Input a number!")));
    }

    @Test
    public void IC13(){
        onView(withId(R.id.CV)).perform(typeText("60.0"), closeSoftKeyboard());
        onView(withId(R.id.DIA)).perform(typeText("42.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.TBS)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.ICR)).check(matches(hasErrorText("You must Input a number!")));
    }

    @Test
    public void IC14(){
        onView(withId(R.id.DIA)).perform(typeText("42.0"), closeSoftKeyboard());
        onView(withId(R.id.CalcInsDos)).perform(click());
        onView(withId(R.id.insuDose)).check(matches(withText(insuDoseCheck)));
        onView(withId(R.id.CBS)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.TBS)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.ICR)).check(matches(hasErrorText("You must Input a number!")));
        onView(withId(R.id.CV)).check(matches(hasErrorText("You must Input a number!")));
    }

    @Test
    public void IC15(){
        onView(withId(R.id.ghLink)).perform(openLinkWithText("https://github.com/HaraldPurnell/InsuCalc"));
    }
}
