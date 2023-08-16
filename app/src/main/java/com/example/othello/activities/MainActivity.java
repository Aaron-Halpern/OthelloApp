package com.example.othello.activities;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.othello.lib.Utils.showInfoDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.othello.R;
import com.example.othello.models.OthelloGame;
import com.example.othello.models.OthelloConfig;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.example.othello.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Snackbar mSnackBar;
    private boolean mUseAutoSave;

    private final String mKEY_GAME = "GAME";
    private String mKEY_AUTO_SAVE;
    private Drawable WHITE_PIECE, BLACK_PIECE, BLANK_SPACE;
    private Toast invalidToast, notTurnToast;
    private Handler turnDelay;


    //private OthelloGame mGame;
    private OthelloConfig mGame2;
    private TextView turnBar, userScore, compScore;


//TODO: figure out why 1st move is always ignored, it waits for the user to click somewhere before making the 1st move
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        setupButtonListeners();
        setupFAB();


        setupDrawables();

        turnBar = findViewById(R.id.turn);
        userScore = findViewById(R.id.userScore);
        compScore = findViewById(R.id.compScore);
//        chooseColor(); //delete this one once startNewGame() works
        startNewGame();

    }

    private void setupDrawables() {
        WHITE_PIECE = getDrawable(R.drawable.white_piece);
        BLACK_PIECE = getDrawable(R.drawable.black_piece);
        BLANK_SPACE = getDrawable(R.drawable.box);
    }

    private void setupToast() {
        invalidToast = Toast.makeText(this, "Invalid move!",
                Toast.LENGTH_LONG);
        notTurnToast = Toast.makeText(this, "Not your move!",
                Toast.LENGTH_LONG);
    }


    private void updateBoardButtons(){
        int [][] board = mGame2.getBoard();
        //use reg for loops, need to compare the index against a parallel array of ids
        for (int row = 0;row<=7;row++){
            for (int col = 0;col<=7;col++){
                if (board[row][col]==1){
                    ImageButton space = findViewById(mGame2.spaces[row][col]); //see if this code works
                    space.setImageDrawable(WHITE_PIECE);
//                    space.refreshDrawableState();
                } else if (board[row][col]==2) {
                    ImageButton space = findViewById(mGame2.spaces[row][col]);
                    space.setImageDrawable(BLACK_PIECE);
//                    space.refreshDrawableState();
                } else if (board[row][col]==0) {
                    ImageButton space = findViewById(mGame2.spaces[row][col]);
                    space.setImageDrawable(BLANK_SPACE);
//                    space.refreshDrawableState();
                }
            }
//            turnDelay();
        }
        String userScoreMsg = getString(R.string.your_score)+(mGame2.getScore()[0]);
        String compScoreMsg = getString(R.string.comp_score) +(mGame2.getScore()[1]);
//        userScore.setText((R.string.your_score) + (mGame2.getScore()[0]));
        userScore.setText(userScoreMsg);
        compScore.setText(compScoreMsg);

//        turnDelay();
//        compScore.setText((R.string.comp_score) + (mGame2.getScore()[1]));
    }


    private void startNewGame() {
        mGame2 = new OthelloConfig();
        chooseColor();
        setupToast();
        updateBoardButtons();




    }

//    private static void turnDelay() {
//        try {
//            Thread.sleep(125);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private void setupFAB() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    private void chooseColor() {
        final String[] colors = {"White", "Black"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your color:");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
        public void onClick(DialogInterface dialog, int color) {
            if ("White".equals(colors[color])) {
                mGame2.setTurn(1);
                setTurnBarToCompTurn();
                compMove();
                setupButtonListeners();
            } else if ("Black".equals(colors[color])) {
                mGame2.setTurn(2);
                setTurnBarToUserTurn();
                setupButtonListeners();
            }
        }
        });
        builder.show();

//        if (mGame2.getCompTurn()==mGame2.getTurn()){
//        if(mGame2.getCompTurn()==2) {
//            call setComputerTurn();

//            compMove();
//        }
//        }
    }


    private void setupButtonListeners() {

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A1.setOnClickListener(view -> userAndCompMoves("A",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A2.setOnClickListener(view -> userAndCompMoves("A",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A3.setOnClickListener(view -> userAndCompMoves("A",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A4.setOnClickListener(view -> userAndCompMoves("A",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A5.setOnClickListener(view -> userAndCompMoves("A",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A6.setOnClickListener(view -> userAndCompMoves("A",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A7.setOnClickListener(view -> userAndCompMoves("A",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A8.setOnClickListener(view -> userAndCompMoves("A",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B1.setOnClickListener(view -> userAndCompMoves("B",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B2.setOnClickListener(view -> userAndCompMoves("B",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B3.setOnClickListener(view -> userAndCompMoves("B",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B4.setOnClickListener(view -> userAndCompMoves("B",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B5.setOnClickListener(view -> userAndCompMoves("B",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B6.setOnClickListener(view -> userAndCompMoves("B",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B7.setOnClickListener(view -> userAndCompMoves("B",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B8.setOnClickListener(view -> userAndCompMoves("B",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C1.setOnClickListener(view -> userAndCompMoves("C",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C2.setOnClickListener(view -> userAndCompMoves("C",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C3.setOnClickListener(view -> userAndCompMoves("C",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C4.setOnClickListener(view -> userAndCompMoves("C",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C5.setOnClickListener(view -> userAndCompMoves("C",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C6.setOnClickListener(view -> userAndCompMoves("C",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C7.setOnClickListener(view -> userAndCompMoves("C",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C8.setOnClickListener(view -> userAndCompMoves("C",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D1.setOnClickListener(view -> userAndCompMoves("D",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D2.setOnClickListener(view -> userAndCompMoves("D",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D3.setOnClickListener(view -> userAndCompMoves("D",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D4.setOnClickListener(view -> userAndCompMoves("D",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D5.setOnClickListener(view -> userAndCompMoves("D",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D6.setOnClickListener(view -> userAndCompMoves("D",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D7.setOnClickListener(view -> userAndCompMoves("D",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D8.setOnClickListener(view -> userAndCompMoves("D",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E1.setOnClickListener(view -> userAndCompMoves("E",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E2.setOnClickListener(view -> userAndCompMoves("E",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E3.setOnClickListener(view -> userAndCompMoves("E",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E4.setOnClickListener(view -> userAndCompMoves("E",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E5.setOnClickListener(view -> userAndCompMoves("E",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E6.setOnClickListener(view -> userAndCompMoves("E",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E7.setOnClickListener(view -> userAndCompMoves("E",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E8.setOnClickListener(view -> userAndCompMoves("E",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F1.setOnClickListener(view -> userAndCompMoves("F",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F2.setOnClickListener(view -> userAndCompMoves("F",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F3.setOnClickListener(view -> userAndCompMoves("F",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F4.setOnClickListener(view -> userAndCompMoves("F",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F5.setOnClickListener(view -> userAndCompMoves("F",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F6.setOnClickListener(view -> userAndCompMoves("F",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F7.setOnClickListener(view -> userAndCompMoves("F",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F8.setOnClickListener(view -> userAndCompMoves("F",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G1.setOnClickListener(view -> userAndCompMoves("G",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G2.setOnClickListener(view -> userAndCompMoves("G",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G3.setOnClickListener(view -> userAndCompMoves("G",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G4.setOnClickListener(view -> userAndCompMoves("G",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G5.setOnClickListener(view -> userAndCompMoves("G",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G6.setOnClickListener(view -> userAndCompMoves("G",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G7.setOnClickListener(view -> userAndCompMoves("G",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G8.setOnClickListener(view -> userAndCompMoves("G",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H1.setOnClickListener(view -> userAndCompMoves("H",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H2.setOnClickListener(view -> userAndCompMoves("H",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H3.setOnClickListener(view -> userAndCompMoves("H",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H4.setOnClickListener(view -> userAndCompMoves("H",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H5.setOnClickListener(view -> userAndCompMoves("H",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H6.setOnClickListener(view -> userAndCompMoves("H",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H7.setOnClickListener(view -> userAndCompMoves("H",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H8.setOnClickListener(view -> userAndCompMoves("H",8));


    }

    private void setContentView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            showSettings();
            return true;
        }  else if (itemId == R.id.action_rules) {
            showRules();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //gets 2 values based on button pressed
    private void userAndCompMoves(String row, int col) {

        if (mGame2.getUserTurn()==mGame2.getTurn()){
            int y = col - 1;
            int x;
            switch (row) {
                case "A":
                    x = 0;
                    break;
                case "B":
                    x = 1;
                    break;
                case "C":
                    x = 2;
                    break;
                case "D":
                    x = 3;
                    break;
                case "E":
                    x = 4;
                    break;
                case "F":
                    x = 5;
                    break;
                case "G":
                    x = 6;
                    break;
                case "H":
                    x = 7;
                    break;
                default:
                    x = -1;
            }


//            boolean invalidMove = false;
            //don't use while loop, just display invalid move, then exit the method, wait for next listener
    //        if (!invalidMove) {
    //
    //        }

            if (!mGame2.userMove(x, y)) {
    //                invalidMove = true;
                invalidToast.show();

            } else {
    //                Toast.makeText(this, "Invalid move!",
    //                        Toast.LENGTH_SHORT).show();
    //                invalidToast.show();
    //            }
    //        }
                updateBoardButtons();
                checkIfGameOver();
    //        mGame2.changeTurn(mGame2.getUserTurn());
                mGame2.changeTurn(mGame2.getCompTurn());
                setTurnBarToCompTurn();
//                turnDelay();

                compMove();
            }
        }else {
            notTurnToast.show();
        }
    }

    private void compMove() {
//        mGame2.changeTurn(mGame2.getCompTurn());

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            mGame2.compMove();
            updateBoardButtons();

            checkIfGameOver();
            mGame2.changeTurn(mGame2.getUserTurn());

            setTurnBarToUserTurn();

        }, 1000);

//        Handler delayCompMove = new Handler();
//        delayCompMove.postDelayed(mGame2.compMove(),1000);
//        mGame2.compMove();

//        updateBoardButtons();
//
//        checkIfGameOver();
//        mGame2.changeTurn(mGame2.getUserTurn());
//
//        setTurnBarToUserTurn();
//        turnDelay();
    }

    private void setTurnBarToUserTurn() {
        turnBar.setText(R.string.your_turn);
    }

    private void setTurnBarToCompTurn() {
        turnBar.setText(R.string.comp_turn);
    }

    private void checkIfGameOver() {
        if(mGame2.fullBoard() || !mGame2.movesLeft()) {
            String winner;
            int[] score = mGame2.getScore();
            if(score[0] > score[1]) {
                OthelloGame.userWins++;
                winner = "User";
            }
            else if(score[1] > score[0]) {
                OthelloGame.compWins++;
                winner = "Computer";
            }
            else {
                OthelloGame.ties++;
                winner = "Tie! No one";
            }
            final String[] options = {"Yes", "No"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(winner + " is the winner!\nWould you like to play again?");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int option) {
                    if ("Yes".equals(options[option])) {
                        startNewGame();
                    }
                }
            });
            builder.show();
        }
    }



    //if over, update statistics (if time)

    private void showRules() {
        showInfoDialog(MainActivity.this, "Othello Rules",
                "Welcome to Othello!\n\n" +
                        "Othello is a two player game.\n" +
                        "You will be playing against the computer.\n" +
                        "Begin by selecting your color of choice.\n" +
                        "Black goes first.\n" +
                        "When it is your turn, select a square to place your piece. " +
                        "You want to sandwich your opponent's piece(s) horizontally, " +
                        "vertically, or diagonally.\n" +
                        "You keep on playing until one of the players runs out of moves on " +
                        "their turn.\n" +
                        "The player with the most amount of pieces of their color by the game's " +
                        "end is the winner!");
    }

    private void restoreOrSetFromPreferences_AllAppAndGameSettings() {
        SharedPreferences sp = getDefaultSharedPreferences(this);
        mUseAutoSave = sp.getBoolean(mKEY_AUTO_SAVE, true);
    }
    private void showSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        settingsLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> settingsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> restoreOrSetFromPreferences_AllAppAndGameSettings());


}