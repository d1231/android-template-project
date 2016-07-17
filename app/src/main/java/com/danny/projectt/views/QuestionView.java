package com.danny.projectt.views;

import com.danny.projectt.Key;
import com.danny.projectt.model.objects.TeamHistory;

import java.util.List;

import rx.Observable;

public interface QuestionView extends BaseView {

    void setTeamHistory(List<TeamHistory> teamHistory);

    Observable<Key> guesses();

    void setGuess(String guess);

    void correctGuess(Key key);

    void incorrectGuess(Key key);

    void guessFinish();
}
