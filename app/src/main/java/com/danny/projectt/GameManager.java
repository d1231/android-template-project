package com.danny.projectt;

import com.danny.projectt.fragments.QuestionFragment;
import com.danny.projectt.model.BackendService;
import com.danny.projectt.model.objects.Player;
import com.danny.projectt.utils.RxUtils;

import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class GameManager {

    private final BackendService backendService;

    private final FragmentTransitionManager fragmentTransitionManager;

    public GameManager(BackendService backendService, FragmentTransitionManager fragmentTransitionManager) {

        this.backendService = backendService;
        this.fragmentTransitionManager = fragmentTransitionManager;
    }

    public void startGame() {

        startNextQuestion();

    }

    private void startNextQuestion() {

        backendService.getPlayer()
                      .observeOn(AndroidSchedulers.mainThread())
                      .map(list -> list.get(0))
                      .subscribe(this::displayQuestion, RxUtils::onError);
    }

    private void displayQuestion(Player player) {

        final QuestionFragment questionFragment = QuestionFragment.newInstance(player);
        fragmentTransitionManager.resetTo(questionFragment);

        questionFragment.attachListener(this);
    }

    public void finishQuestion() {

        Timber.d("Finished question");

        startNextQuestion();
    }
}
