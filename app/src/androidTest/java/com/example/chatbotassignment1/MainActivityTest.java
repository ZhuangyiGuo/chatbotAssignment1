package com.example.chatbotassignment1;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.chatbotassignment1", appContext.getPackageName());
    }
    @Test
    public void onCreate() {

        assertTrue(MainActivity.ACTIVITY_NAME.compareTo(
                "MainActivity") == 0);
//        assertTrue(MainActivity.class.getName().compareTo(
//                "com.code.abdulrahman.widgetexamples.MainActivity") == 0);
    }

    @Test
    public void startToast() {
        assertTrue(MainActivity.ACTIVITY_NAME.compareTo(
                "MainActivity") == 0);
    }

    @Test
    public void assertingSame() {
        Object x= new Object ();
        Object y= new Object();
        assertSame(x, y);
    }
    @Test
    public void assertingSame2() {
        Object x= new Object ();
        Object y= x;
        assertSame(y, x);
    }
    @Test
    public void readingFile() {
    }

    @Test
    public void usingAssertThat() {
        //   assertThat(0, is(1));
        assertThat(0, is(not(1)));
    }
}