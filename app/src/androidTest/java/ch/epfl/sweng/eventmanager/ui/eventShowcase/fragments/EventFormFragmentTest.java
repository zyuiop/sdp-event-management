package ch.epfl.sweng.eventmanager.ui.eventShowcase.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import ch.epfl.sweng.eventmanager.R;
import ch.epfl.sweng.eventmanager.ui.eventSelector.EventPickingActivity;
import ch.epfl.sweng.eventmanager.ui.eventShowcase.EventShowcaseActivity;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class EventFormFragmentTest {

    @Rule
    public final ActivityTestRule<EventShowcaseActivity> mActivityRule =
            new ActivityTestRule<>(EventShowcaseActivity.class);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        System.err.println("SetUp called");
        new Throwable().printStackTrace();
        intent.putExtra(EventPickingActivity.SELECTED_EVENT_ID, 1);
        mActivityRule.launchActivity(intent);

        SystemClock.sleep(2000);

        onView(withId(R.id.contact_form_go_button)).perform(click());

        SystemClock.sleep(2000);

        Intents.init();
    }

    @After
    public void removeIntents() {
        Intents.release();
    }

    @Test
    public void testGoToForm() {
        String errorText = getResourceString(R.string.contact_form_error);

        onView(withId(R.id.contact_form_send_button))
                .check(matches(isClickable()))
                .perform(click());

        onView(withId(R.id.contact_form_name)).check(matches(hasErrorText(errorText)));

        String target = "John Snow";
        String title = "Knowledge is power";
        String content = "I know nothing...";

        fillInAndClick(R.id.contact_form_name, R.id.contact_form_subject, target, errorText);

        fillInAndClick(R.id.contact_form_subject, R.id.contact_form_content, title, errorText);

        onView(withId(R.id.contact_form_content)).perform(typeText(content), closeSoftKeyboard());

        onView(withId(R.id.contact_form_send_button)).perform(click());

        Intents.intended(Matchers.allOf(
                IntentMatchers.hasAction(Intent.ACTION_CHOOSER),
                IntentMatchers.hasExtra(Matchers.is(Intent.EXTRA_INTENT), Matchers.allOf(
                        IntentMatchers.hasType("message/rfc822"), //email MIME data
                        // TODO: mock event to test email address
                        // IntentMatchers.hasExtra(Intent.EXTRA_EMAIL  , new String[] {email});
                        IntentMatchers.hasExtra(Intent.EXTRA_SUBJECT, target + " : " + title),
                        IntentMatchers.hasExtra(Intent.EXTRA_TEXT, content)
                ))
        ));

    }

    private void fillInAndClick(int id, int errorId, String text, String errorText) {
        onView(withId(id)).perform(typeText(text), closeSoftKeyboard());

        onView(withId(R.id.contact_form_send_button)).perform(click());
        onView(withId(errorId)).check(matches(hasErrorText(errorText)));
    }

    // FIXME same method as in LoginActivityTest, we could consider making a TestUtils class although not certain that getTargetContext will work
    private String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(id);
    }

}