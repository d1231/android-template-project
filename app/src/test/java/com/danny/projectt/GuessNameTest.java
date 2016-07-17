package com.danny.projectt;

import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GuessNameTest {

    @Test
    public void testEncodedString() throws Exception {

        GuessName guessName = new GuessName("Robinho");

        assertThat(guessName.text(), is("*******"));

    }

    @Test
    public void testEncodedStringWithSpaces() throws Exception {

        GuessName guessName = new GuessName("Frank Lampard");

        assertThat(guessName.text(), is("***** *******"));

    }

    @Test
    public void testAddChar() throws Exception {

        GuessName guessName = new GuessName("Frank Lampard");

        assertThat(guessName.guessChar('r'), is(true));
        assertThat(guessName.text(), is("*r*** *****r*"));

    }

    @Test
    public void testAddCharNotExisting() throws Exception {

        GuessName guessName = new GuessName("Frank Lampard");

        assertThat(guessName.guessChar('q'), is(false));
        assertThat(guessName.text(), is("***** *******"));

    }

    @Test
    public void testAddCharCapitalLetter() throws Exception {

        GuessName guessName = new GuessName("Frank Lampard");

        guessName.guessChar('F');

        assertThat(guessName.text(), is("F**** *******"));

    }

    @Test
    public void testObservable() throws Exception {

        final TestSubscriber<String> testSubscriber = TestSubscriber.create();
        final TestSubscriber<String> testSubscriber1 = TestSubscriber.create();

        GuessName guessName = new GuessName("Frank Lampard");
        final Observable<String> shared = guessName.textObservable().share();

        shared.subscribe(testSubscriber);
        shared.last().subscribe(testSubscriber1);


        guessName.guessChar('f');
        guessName.guessChar('r');
        guessName.guessChar('q');
        guessName.guessChar('d');

        testSubscriber.assertValues("***** *******", "F**** *******", "Fr*** *****r*", "Fr*** *****rd");
        testSubscriber.assertNotCompleted();

        guessName.guessChar('a');
        guessName.guessChar('n');
        guessName.guessChar('k');
        guessName.guessChar('l');
        guessName.guessChar('m');
        guessName.guessChar('p');

        testSubscriber.assertCompleted();

        testSubscriber1.assertValue("Frank Lampard");
        testSubscriber1.assertCompleted();
    }


}