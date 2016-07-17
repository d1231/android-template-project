package com.danny.projectt.fragments;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.danny.projectt.GameActivity;
import com.danny.projectt.Key;
import com.danny.projectt.PlayerTestHelper;
import com.danny.projectt.R;
import com.danny.projectt.model.objects.Player;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.observers.TestSubscriber;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class QuestionFragmentTest {

    @Rule
    public ActivityTestRule<GameActivity> mActivityRule = new ActivityTestRule<>(
            GameActivity.class);

    @Test
    public void testGuessesObservable() throws Exception {

        final QuestionFragment questionFragment = getQuestionFragment();


        final TestSubscriber<Key> testSubscriber = TestSubscriber.create();

        questionFragment.guesses().subscribe(testSubscriber);

        onView(withId(R.id.question_letters)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        testSubscriber.assertValue(Key.C);
        testSubscriber.assertNoErrors();
        testSubscriber.assertNotCompleted();

    }

    private QuestionFragment getQuestionFragment() {

        final Player dummyPlayer = PlayerTestHelper.getDummyPlayer();
        final QuestionFragment questionFragment = QuestionFragment.newInstance(dummyPlayer);

        mActivityRule.getActivity().getSupportFragmentManager()
                     .beginTransaction()
                     .add(R.id.content, questionFragment)
                     .commit();
        return questionFragment;
    }

}