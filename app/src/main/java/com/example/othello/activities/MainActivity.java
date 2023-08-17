package com.example.othello.activities;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.othello.lib.Utils.showInfoDialog;
import static com.example.othello.models.OthelloConfig.getGameFromJSON;
import static com.example.othello.models.OthelloConfig.getJSONFromGame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.othello.R;
import com.example.othello.models.OthelloConfig;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;

import com.example.othello.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//comment
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Snackbar mSnackBar;
    private boolean mUseAutoSave;
    private boolean mIsMidGame;
    private boolean mIsNightMode;
    private boolean mColorNotSelected;

    private final String mKEY_GAME = "GAME";
    private String mKEY_AUTO_SAVE;
    private String mKEY_IS_MID_GAME;
    private Drawable WHITE_PIECE, BLACK_PIECE, BLANK_SPACE;
    private Toast invalidToast, notTurnToast;
    private Handler turnDelay;


    //private OthelloGame mGame;
    private OthelloConfig mGame2;
    private TextView turnBar, userScore, compScore;

    private String score1, score2;

    //The following code is for the auto-save functionality:

    @Override
    protected void onStop() {
        super.onStop();
        saveOrDeleteGameInSharedPrefs();
    }

    private void saveOrDeleteGameInSharedPrefs() {
        SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();

        // Save current game or remove any prior game to/from default shared preferences
        if (mUseAutoSave) {
            int scoreA = Integer.parseInt(score1);
            int scoreB = Integer.parseInt(score2);
            if ((scoreA != 2 && scoreB != 2)) {
                mIsMidGame = true;
            } else {
                mIsMidGame = false;
            }
            editor.putString(mKEY_GAME, mGame2.getJSONFromCurrentGame());
            editor.putBoolean(mKEY_IS_MID_GAME, mIsMidGame);
        } else {
            editor.remove(mKEY_GAME);
        }

        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

        restoreFromPreferences_SavedGameIfAutoSaveWasSetOn();
        restoreOrSetFromPreferences_AllAppAndGameSettings();
    }

    private void restoreFromPreferences_SavedGameIfAutoSaveWasSetOn() {
        SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(this);
        if (defaultSharedPreferences.getBoolean(mKEY_AUTO_SAVE, true)) {
            String gameString = defaultSharedPreferences.getString(mKEY_GAME, null);
            if (gameString != null) {
                mGame2 = getGameFromJSON(gameString);
                mIsMidGame = defaultSharedPreferences.getBoolean(mKEY_IS_MID_GAME, true);
                updateBoardButtonsAndScoreTextViews();
            }
        }
    }

    private void restoreOrSetFromPreferences_AllAppAndGameSettings() {
        SharedPreferences sp = getDefaultSharedPreferences(this);
        mUseAutoSave = sp.getBoolean(mKEY_AUTO_SAVE, true);
        mIsMidGame = sp.getBoolean(mKEY_IS_MID_GAME, true);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(mKEY_GAME, getJSONFromGame(mGame2));
        outState.putBoolean(mKEY_AUTO_SAVE, mUseAutoSave);
        outState.putBoolean(mKEY_IS_MID_GAME, mIsMidGame);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mGame2 = getGameFromJSON(savedInstanceState.getString(mKEY_GAME));
        mUseAutoSave = savedInstanceState.getBoolean(mKEY_AUTO_SAVE, true);
        mIsMidGame = savedInstanceState.getBoolean(mKEY_IS_MID_GAME, true);
        updateBoardButtonsAndScoreTextViews();
    }

    //until here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupDrawables();
        mKEY_IS_MID_GAME = getString(R.string.is_mid_game);
        mKEY_AUTO_SAVE = getString(R.string.auto_save_key);
        turnBar = findViewById(R.id.turn);
        userScore = findViewById(R.id.userScore);
        compScore = findViewById(R.id.compScore);
        startNewGame(savedInstanceState);

    }

    //Sets up board pieces
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

    //Updates the displayed Othello board and the score text views
    private void updateBoardButtonsAndScoreTextViews() {
        int[][] board = mGame2.getBoard();
        for (int row = 0; row <= 7; row++) {
            for (int col = 0; col <= 7; col++) {
                if (board[row][col] == 1) {
                    ImageButton space = findViewById(mGame2.spaces[row][col]); //see if this code works
                    space.setImageDrawable(WHITE_PIECE);
                } else if (board[row][col] == 2) {
                    ImageButton space = findViewById(mGame2.spaces[row][col]);
                    space.setImageDrawable(BLACK_PIECE);
                } else if (board[row][col] == 0) {
                    ImageButton space = findViewById(mGame2.spaces[row][col]);
                    space.setImageDrawable(BLANK_SPACE);
                }
            }
        }
        score1 = "" + mGame2.getScore()[0];
        score2 = "" + mGame2.getScore()[1];
        String userScoreMsg = getString(R.string.your_score) + score1;
        String compScoreMsg = getString(R.string.comp_score) + score2;
        userScore.setText(userScoreMsg);
        compScore.setText(compScoreMsg);
    }


    private void startNewGame(Bundle savedInstanceState) {
        mGame2 = new OthelloConfig();
        updateBoardButtonsAndScoreTextViews();
        setupButtonListeners();
        setupToast();
        mColorNotSelected = true;
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
                    mGame2.changeTurn(mGame2.getCompTurn());
                    setTurnBarToCompTurn();
                    compMove();

                } else if ("Black".equals(colors[color])) {
                    mGame2.setTurn(2);
                    mGame2.changeTurn(mGame2.getUserTurn());
                    setTurnBarToUserTurn();

                }
            }
        });
        builder.show();
    }

    //Sets up Othello board, which is made up of buttons
    private void setupButtonListeners() {

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A1.setOnClickListener(view -> userAndCompMoves("A", 1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A2.setOnClickListener(view -> userAndCompMoves("A", 2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A3.setOnClickListener(view -> userAndCompMoves("A", 3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A4.setOnClickListener(view -> userAndCompMoves("A", 4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A5.setOnClickListener(view -> userAndCompMoves("A", 5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A6.setOnClickListener(view -> userAndCompMoves("A", 6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A7.setOnClickListener(view -> userAndCompMoves("A", 7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A8.setOnClickListener(view -> userAndCompMoves("A", 8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B1.setOnClickListener(view -> userAndCompMoves("B", 1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B2.setOnClickListener(view -> userAndCompMoves("B", 2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B3.setOnClickListener(view -> userAndCompMoves("B", 3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B4.setOnClickListener(view -> userAndCompMoves("B", 4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B5.setOnClickListener(view -> userAndCompMoves("B", 5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B6.setOnClickListener(view -> userAndCompMoves("B", 6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B7.setOnClickListener(view -> userAndCompMoves("B", 7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B8.setOnClickListener(view -> userAndCompMoves("B", 8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C1.setOnClickListener(view -> userAndCompMoves("C", 1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C2.setOnClickListener(view -> userAndCompMoves("C", 2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C3.setOnClickListener(view -> userAndCompMoves("C", 3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C4.setOnClickListener(view -> userAndCompMoves("C", 4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C5.setOnClickListener(view -> userAndCompMoves("C", 5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C6.setOnClickListener(view -> userAndCompMoves("C", 6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C7.setOnClickListener(view -> userAndCompMoves("C", 7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C8.setOnClickListener(view -> userAndCompMoves("C", 8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D1.setOnClickListener(view -> userAndCompMoves("D", 1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D2.setOnClickListener(view -> userAndCompMoves("D", 2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D3.setOnClickListener(view -> userAndCompMoves("D", 3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D4.setOnClickListener(view -> userAndCompMoves("D", 4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D5.setOnClickListener(view -> userAndCompMoves("D", 5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D6.setOnClickListener(view -> userAndCompMoves("D", 6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D7.setOnClickListener(view -> userAndCompMoves("D", 7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D8.setOnClickListener(view -> userAndCompMoves("D", 8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E1.setOnClickListener(view -> userAndCompMoves("E", 1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E2.setOnClickListener(view -> userAndCompMoves("E", 2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E3.setOnClickListener(view -> userAndCompMoves("E", 3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E4.setOnClickListener(view -> userAndCompMoves("E", 4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E5.setOnClickListener(view -> userAndCompMoves("E", 5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E6.setOnClickListener(view -> userAndCompMoves("E", 6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E7.setOnClickListener(view -> userAndCompMoves("E", 7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E8.setOnClickListener(view -> userAndCompMoves("E", 8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F1.setOnClickListener(view -> userAndCompMoves("F", 1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F2.setOnClickListener(view -> userAndCompMoves("F", 2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F3.setOnClickListener(view -> userAndCompMoves("F", 3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F4.setOnClickListener(view -> userAndCompMoves("F", 4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F5.setOnClickListener(view -> userAndCompMoves("F", 5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F6.setOnClickListener(view -> userAndCompMoves("F", 6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F7.setOnClickListener(view -> userAndCompMoves("F", 7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F8.setOnClickListener(view -> userAndCompMoves("F", 8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G1.setOnClickListener(view -> userAndCompMoves("G", 1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G2.setOnClickListener(view -> userAndCompMoves("G", 2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G3.setOnClickListener(view -> userAndCompMoves("G", 3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G4.setOnClickListener(view -> userAndCompMoves("G", 4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G5.setOnClickListener(view -> userAndCompMoves("G", 5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G6.setOnClickListener(view -> userAndCompMoves("G", 6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G7.setOnClickListener(view -> userAndCompMoves("G", 7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G8.setOnClickListener(view -> userAndCompMoves("G", 8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H1.setOnClickListener(view -> userAndCompMoves("H", 1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H2.setOnClickListener(view -> userAndCompMoves("H", 2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H3.setOnClickListener(view -> userAndCompMoves("H", 3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H4.setOnClickListener(view -> userAndCompMoves("H", 4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H5.setOnClickListener(view -> userAndCompMoves("H", 5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H6.setOnClickListener(view -> userAndCompMoves("H", 6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H7.setOnClickListener(view -> userAndCompMoves("H", 7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H8.setOnClickListener(view -> userAndCompMoves("H", 8));


    }

    private void setContentView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    //The following 2 methods sets up the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_new_game) {
            startNewGame(null);
            return true;
        } else if (itemId == R.id.action_settings) {
            showSettings();
            return true;
        } else if (itemId == R.id.action_rules) {
            showRules();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Allows the user to move, and the computer's move method is called within
    private void userAndCompMoves(String row, int col) {

        int scoreA = Integer.parseInt(score1);
        int scoreB = Integer.parseInt(score2);

        if (scoreA == 2 && scoreB == 2 && mColorNotSelected) {
            chooseColor();
            mColorNotSelected = false;
        } else {
            if (mGame2.getUserTurn() == mGame2.getTurn()) {
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

                if (!mGame2.userMove(x, y)) {
                    invalidToast.show();

                } else {
                    updateBoardButtonsAndScoreTextViews();
                    if (!checkIfGameOver()) {
                        mGame2.changeTurn(mGame2.getCompTurn());
                        setTurnBarToCompTurn();
                        compMove();
                    }
                }
            } else {
                notTurnToast.show();
            }
        }
    }

    //The computer's move method
    private void compMove() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            mGame2.compMove();
            updateBoardButtonsAndScoreTextViews();
            if (!checkIfGameOver()) {
                mGame2.changeTurn(mGame2.getUserTurn());

                setTurnBarToUserTurn();
            }
        }, 1000);
    }

    //The following 2 methods change the turn information text view
    private void setTurnBarToUserTurn() {
        turnBar.setText(R.string.your_turn);
    }

    private void setTurnBarToCompTurn() {
        turnBar.setText(R.string.comp_turn);
    }

    private boolean checkIfGameOver() {
        if (mGame2.fullBoard() || !mGame2.movesLeft()) {
            String winner;
            int[] score = mGame2.getScore();
            if (score[0] > score[1]) {
                winner = "User";
            } else if (score[1] > score[0]) {
                winner = "Computer";
            } else {
                winner = "Tie! No one";
            }

            final String[] options = {"Yes", "No"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(winner + " is the winner!\nWould you like to play again?");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int option) {
                    if ("Yes".equals(options[option])) {
                        startNewGame(null);
                    } else if ("No".equals(options[option])) {
                        updateBoardButtonsAndScoreTextViews();
                    }
                }
            });
            builder.show();
            return true;
        } else {
            return false;
        }
    }

    //The following displays a dialog of the Othello Rules
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


    //The following 2 methods allow the Settings Activity to open up and work
    private void showSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        settingsLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> settingsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> restoreOrSetFromPreferences_AllAppAndGameSettings());


}