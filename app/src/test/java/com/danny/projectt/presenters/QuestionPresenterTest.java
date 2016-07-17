package com.danny.projectt.presenters;

import com.danny.projectt.Key;
import com.danny.projectt.PlayerTestHelper;
import com.danny.projectt.model.objects.Player;
import com.danny.projectt.views.QuestionView;

import org.junit.Test;
import org.mockito.Mockito;

import rx.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuestionPresenterTest {

    @Test
    public void testBindTeamHistory() throws Exception {

        final Player dummyPlayer = PlayerTestHelper.getDummyPlayer();
        QuestionPresenter questionPresenter = new QuestionPresenter(dummyPlayer);

        QuestionView questionView = Mockito.mock(QuestionView.class);

        when(questionView.guesses()).thenReturn(Observable.never());

        questionPresenter.attachView(questionView);

        verify(questionView, times(1)).setTeamHistory(dummyPlayer.teamHistory());
    }

    @Test
    public void testCorrectGuess() throws Exception {

        final Player dummyPlayer = PlayerTestHelper.getDummyPlayer();
        QuestionPresenter questionPresenter = new QuestionPresenter(dummyPlayer);

        QuestionView questionView = Mockito.mock(QuestionView.class);

        when(questionView.guesses()).thenReturn(Observable.defer(() -> Observable.just(Key.E)));

        questionPresenter.attachView(questionView);

        verify(questionView, times(1)).correctGuess(Key.E);
    }

    @Test
    public void testIncorrectGuess() throws Exception {

        final Player dummyPlayer = PlayerTestHelper.getDummyPlayer();
        QuestionPresenter questionPresenter = new QuestionPresenter(dummyPlayer);

        QuestionView questionView = Mockito.mock(QuestionView.class);

        when(questionView.guesses()).thenReturn(Observable.defer(() -> Observable.just(Key.Q)));

        questionPresenter.attachView(questionView);

        verify(questionView, times(1)).incorrectGuess(Key.Q);
    }
}