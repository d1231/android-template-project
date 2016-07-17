package com.danny.projectt.presenters;

import com.danny.projectt.GuessName;
import com.danny.projectt.Key;
import com.danny.projectt.model.objects.Player;
import com.danny.projectt.utils.RxUtils;
import com.danny.projectt.views.QuestionView;

import java.util.ArrayList;

import rx.Subscription;

public class QuestionPresenter implements BasePresenter<QuestionView> {

    private final Player player;

    private GuessName guessName;

    private QuestionView view;

    private ArrayList<Subscription> subscriptions = new ArrayList<>();

    public QuestionPresenter(Player player) {

        this.player = player;
    }

    @Override
    public void attachView(QuestionView view) {

        this.view = view;

        guessName = new GuessName(player.name());

        final Subscription guessSubs = guessName.textObservable()
                                                .subscribe(view::setGuess, RxUtils::onError, this::guessFinish);

        final Subscription guessInputSubs = view.guesses()
                                                .subscribe(this::guessLetter, RxUtils::onError);

        subscriptions.add(guessSubs);
        subscriptions.add(guessInputSubs);

        view.setTeamHistory(player.teamHistory());
    }

    @Override
    public void detachView() {

        RxUtils.safeUnsubscribe(subscriptions);
        view = null;

    }

    private void guessFinish() {

        view.guessFinish();

    }

    private void guessLetter(Key key) {

        final boolean correctGuess = guessName.guessChar(key.getLetter());
        if (correctGuess) {
            view.correctGuess(key);
        } else {
            view.incorrectGuess(key);
        }
    }
}
