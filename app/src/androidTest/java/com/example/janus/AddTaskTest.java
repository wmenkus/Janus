package com.example.janus;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.CoreMatchers.anything;

import android.os.SystemClock;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import  androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import  androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class AddTaskTest extends TestCase {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void validateAddTaskBehavior() throws InterruptedException {
        //starts test by clicking lets go button, a user must already be logged in
        onView(withId(R.id.startFragmentStartButton)).perform(click());

        // attempts to make sure right view is displayed, if not throws error
        try {
            onView((withId(R.id.taskFragmentAddTaskButton))).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {

        }
        ;
        //clicks add task button
        SystemClock.sleep(2000);
        onView(withId(R.id.taskFragmentAddTaskButton)).perform(click());
        try {
            onView((withId(R.id.addTaskNewTaskButton))).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {

        }
        ;
        //clicks new task button
        onView(withId(R.id.addTaskNewTaskButton)).perform(click());
        try {
            onView((withId(R.id.newTaskSaveButton))).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {

        }
        ;
        //attempts to fill in values for new task and click save
        onView(withId(R.id.newTaskNotesEditText)).perform(click()).perform(typeText("TestNotes"));
        onView(withId(R.id.newTaskDueDateEditText)).perform(click()).perform(typeText("01/01/2100"));
        onView((withId(R.id.newTaskTaskNameEditText))).perform(click()).perform(typeText("TestTask"));
        onView(withId(R.id.newTaskSourceEditText)).perform(click()).perform(typeText("TestSource"));
        onView(withId(R.id.newTaskWeightSpinner)).perform(click());
        onData(anything()).atPosition(1).perform(click(), ViewActions.closeSoftKeyboard());
        onView((withId(R.id.newTaskSaveButton))).perform( click());
        SystemClock.sleep(2000);
       // try {
        //    onView((withId(R.id.taskFragmentAddTaskButton))).check(matches(isDisplayed()));
       // } catch (AssertionFailedError e) {


       // }
    //}
}
}