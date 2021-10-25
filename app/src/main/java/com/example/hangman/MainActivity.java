package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Hangman game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If Hangman object is not instantiated (ie. game == null).
        // Then, construct game with default guesses
        if (game == null) {
            game = new Hangman(Hangman.DEFAULT_GUESSES);
        }
        setContentView(R.layout.activity_main);
        TextView status = (TextView) findViewById(R.id.status);
        status.setText("" + game.getGuessesLeft());
        if (getSupportFragmentManager().findFragmentById(R.id.game_state) == null) {
            // create fragment and add it to the activity
            GameStateFragment fragment = new GameStateFragment();
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.game_state, fragment, null)
                    .commit();
        }
        if (getSupportFragmentManager().findFragmentById(R.id.game_result) == null) {
            // create fragment and add it to the activity
            GameResultFragment fragment = new GameResultFragment();
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.game_result, fragment, null)
                    .commit();
        }
    }

    public void play() {
        // TODO implement play()
    }

    public Hangman getGame() { return game; }
}