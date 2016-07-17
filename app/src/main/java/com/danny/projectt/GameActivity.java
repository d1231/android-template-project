package com.danny.projectt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.danny.projectt.model.BackendService;
import com.danny.projectt.model.BackendServiceFactory;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.screen_holder);

        final FragmentTransitionManager fragmentTransitionManager = new FragmentTransitionManager();
        fragmentTransitionManager.takeActivity(this, R.id.content);

        final BackendService backendService = BackendServiceFactory.create();
        final GameManager gameManager = new GameManager(backendService, fragmentTransitionManager);

        gameManager.startGame();

    }

}
