package com.danny.projectt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danny.projectt.GameManager;
import com.danny.projectt.Key;
import com.danny.projectt.R;
import com.danny.projectt.adapters.KeyboardAdapter;
import com.danny.projectt.adapters.TeamHistoryAdapter;
import com.danny.projectt.model.objects.Player;
import com.danny.projectt.model.objects.TeamHistory;
import com.danny.projectt.presenters.QuestionPresenter;
import com.danny.projectt.views.QuestionView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.PublishSubject;

public class QuestionFragment extends Fragment implements QuestionView, KeyboardAdapter.KeyClickedListener {

    private static final String ARG_PLAYER = "player";

    @Bind(R.id.question_letters)
    RecyclerView keyboardRv;

    @Bind(R.id.question_teamhistory)
    RecyclerView teamhistoryRv;

    @Bind(R.id.answer)
    TextView guessView;

    private PublishSubject<Key> guessedLettersSubject = PublishSubject.create();

    private QuestionPresenter presenter;

    private KeyboardAdapter keyboardAdapter;

    private GameManager gameManager;

    public QuestionFragment() {

    }

    public static QuestionFragment newInstance(Player player) {

        QuestionFragment fragment = new QuestionFragment();

        Bundle args = new Bundle();

        args.putParcelable(ARG_PLAYER, player);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            final Player player = getArguments().getParcelable(ARG_PLAYER);
            presenter = new QuestionPresenter(player);
        } else {
            throw new IllegalStateException("No player");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.screen_question, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        final Context context = getContext();
        final GridLayoutManager layoutManager = new GridLayoutManager(context, 6, LinearLayoutManager.VERTICAL, false);
        keyboardAdapter = new KeyboardAdapter(context, this);

        keyboardRv.setLayoutManager(layoutManager);
        keyboardRv.setItemAnimator(new DefaultItemAnimator());
        keyboardRv.setAdapter(keyboardAdapter);
        keyboardRv.setHasFixedSize(true);

        presenter.attachView(this);

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        presenter.detachView();
    }

    @Override
    public void setTeamHistory(List<TeamHistory> teamHistory) {

        Context context = getContext();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        TeamHistoryAdapter teamHistoryAdapter = new TeamHistoryAdapter(context, teamHistory);

        teamhistoryRv.setLayoutManager(linearLayoutManager);
        teamhistoryRv.setAdapter(teamHistoryAdapter);
        teamhistoryRv.setHasFixedSize(true);

    }

    @Override
    public void onKeyClicked(Key key) {

        guessedLettersSubject.onNext(key);

    }

    @Override
    public Observable<Key> guesses() {

        return guessedLettersSubject;

    }

    @Override
    public void setGuess(String guess) {

        guessView.setText(guess);

    }

    @Override
    public void correctGuess(Key key) {


        keyboardAdapter.addCorrectGuess(key);
    }

    @Override
    public void incorrectGuess(Key key) {

        keyboardAdapter.addIncorrectGuess(key);
    }

    @Override
    public void guessFinish() {

        gameManager.finishQuestion();

    }

    public void attachListener(GameManager gameManager) {

        this.gameManager = gameManager;
    }
}
