package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
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

    public void play(View view) {
        EditText input = (EditText) findViewById(R.id.letter);
        Editable userText = input.getText();
        if (userText != null && userText.length() > 0) {
            // update number of guesses left
            char letter = userText.charAt(0);
            game.guess(letter);
            TextView status = (TextView) findViewById(R.id.status);
            status.setText("" + game.getGuessesLeft());

            // update incomplete word
            GameStateFragment gsFragment = (GameStateFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.game_state);
            View gsFragmentView = gsFragment.getView();
            TextView gameStateTV = (TextView)
                    gsFragmentView.findViewById(R.id.state_of_game);
            gameStateTV.setText(game.currentIncompleteWord());

            // clear EditText
            input.setText("");

            // TODO check if there is only one guess left
//            if( game.getGuessesLeft( ) == 1 ) {
//                BackgroundFragment background = ( BackgroundFragment )
//                        getSupportFragmentManager().findFragmentByTag( __________ );
//                GameResultFragment grFragment = ( GameResultFragment )
//                        getSupportFragmentManager().findFragmentById( ______________ );
//                // retrieve warning and display it
//                grFragment.setResult( _____________________ );
//            }

            int result = game.gameOver();
            if (result != 0) /* game is over */ {
                GameResultFragment grFragment = (GameResultFragment)
                        getSupportFragmentManager().findFragmentById(R.id.game_result);
                // update TextView in result fragment
                if (result == 1) {
                    grFragment.setResult("YOU WON");
                } else if (result == -1) {
                    grFragment.setResult("YOU LOST");
                }
                // delete hint in EditText
                input.setHint("");
            }
        }
    }

    public Hangman getGame() {
        return game;
    }
}