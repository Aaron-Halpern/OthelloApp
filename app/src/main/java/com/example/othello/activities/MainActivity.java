package com.example.othello.activities;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.othello.lib.Utils.showInfoDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.othello.R;
import com.example.othello.models.OthelloGame;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.othello.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Snackbar mSnackBar;
    private boolean mUseAutoSave;

    private final String mKEY_GAME = "GAME";
    private String mKEY_AUTO_SAVE;
    private final Drawable WHITE_PIECE = getDrawable(R.drawable.white_piece);

    private final Drawable BLACK_PIECE = getDrawable(R.drawable.black_piece);
    private OthelloGame mGame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupButtonListeners();
        setupFAB();






        startNewGame();


    }


    private void updateBoardButtons(){

        //use reg for loops, need to compare the index against a parallel array of ids
        for (int row = 0;row<=7;row++){
            for (int col = 0;col<=7;col++){
                if (mGame.mBoard[row][col]==1){
                    ImageButton space = findViewById(mGame.mSpaces[row][col]);
                    space.setImageDrawable(WHITE_PIECE);
                } else if (mGame.mBoard[row][col]==2) {
                    ImageButton space = findViewById(mGame.mSpaces[row][col]);
                    space.setImageDrawable(BLACK_PIECE);
                }
            }
        }
        //TODO: method to update scores
    }


    private void startNewGame() {
        mGame=new OthelloGame();
        chooseColor();//TODO: make dialog at begining of game to get color
        updateBoardButtons();
        //TODO: method: if comp turn, do comp move, else: nothing
    }

    //TODO: method to run player turn, end with automatically doing comp turn

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
    //TODO: create method to let user choose color (dialog?)
        //get selection from user, either 1 or 2
//        mGame.SetUserColorSelection(turnSelection);
    }



    private void setupButtonListeners() {

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A1.setOnClickListener(view -> playerSelection("A",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A2.setOnClickListener(view -> playerSelection("A",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A3.setOnClickListener(view -> playerSelection("A",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A4.setOnClickListener(view -> playerSelection("A",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A5.setOnClickListener(view -> playerSelection("A",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A6.setOnClickListener(view -> playerSelection("A",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A7.setOnClickListener(view -> playerSelection("A",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.A8.setOnClickListener(view -> playerSelection("A",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B1.setOnClickListener(view -> playerSelection("B",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B2.setOnClickListener(view -> playerSelection("B",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B3.setOnClickListener(view -> playerSelection("B",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B4.setOnClickListener(view -> playerSelection("B",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B5.setOnClickListener(view -> playerSelection("B",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B6.setOnClickListener(view -> playerSelection("B",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B7.setOnClickListener(view -> playerSelection("B",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.B8.setOnClickListener(view -> playerSelection("B",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C1.setOnClickListener(view -> playerSelection("C",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C2.setOnClickListener(view -> playerSelection("C",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C3.setOnClickListener(view -> playerSelection("C",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C4.setOnClickListener(view -> playerSelection("C",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C5.setOnClickListener(view -> playerSelection("C",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C6.setOnClickListener(view -> playerSelection("C",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C7.setOnClickListener(view -> playerSelection("C",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.C8.setOnClickListener(view -> playerSelection("C",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D1.setOnClickListener(view -> playerSelection("D",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D2.setOnClickListener(view -> playerSelection("D",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D3.setOnClickListener(view -> playerSelection("D",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D4.setOnClickListener(view -> playerSelection("D",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D5.setOnClickListener(view -> playerSelection("D",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D6.setOnClickListener(view -> playerSelection("D",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D7.setOnClickListener(view -> playerSelection("D",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.D8.setOnClickListener(view -> playerSelection("D",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E1.setOnClickListener(view -> playerSelection("E",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E2.setOnClickListener(view -> playerSelection("E",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E3.setOnClickListener(view -> playerSelection("E",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E4.setOnClickListener(view -> playerSelection("E",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E5.setOnClickListener(view -> playerSelection("E",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E6.setOnClickListener(view -> playerSelection("E",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E7.setOnClickListener(view -> playerSelection("E",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.E8.setOnClickListener(view -> playerSelection("E",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F1.setOnClickListener(view -> playerSelection("F",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F2.setOnClickListener(view -> playerSelection("F",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F3.setOnClickListener(view -> playerSelection("F",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F4.setOnClickListener(view -> playerSelection("F",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F5.setOnClickListener(view -> playerSelection("F",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F6.setOnClickListener(view -> playerSelection("F",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F7.setOnClickListener(view -> playerSelection("F",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.F8.setOnClickListener(view -> playerSelection("F",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G1.setOnClickListener(view -> playerSelection("G",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G2.setOnClickListener(view -> playerSelection("G",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G3.setOnClickListener(view -> playerSelection("G",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G4.setOnClickListener(view -> playerSelection("G",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G5.setOnClickListener(view -> playerSelection("G",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G6.setOnClickListener(view -> playerSelection("G",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G7.setOnClickListener(view -> playerSelection("G",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.G8.setOnClickListener(view -> playerSelection("G",8));

        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H1.setOnClickListener(view -> playerSelection("H",1));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H2.setOnClickListener(view -> playerSelection("H",2));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H3.setOnClickListener(view -> playerSelection("H",3));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H4.setOnClickListener(view -> playerSelection("H",4));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H5.setOnClickListener(view -> playerSelection("H",5));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H6.setOnClickListener(view -> playerSelection("H",6));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H7.setOnClickListener(view -> playerSelection("H",7));
        binding.contentMain.mainIncludeAllContentItems.mainIncludeGameBoard.H8.setOnClickListener(view -> playerSelection("H",8));


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
    private void playerSelection(String row, int col){
        int y= col-1;
        int x;
        switch (row){
            case "A":
                x=0;
                break;
            case "B":
                x=1;
                break;
            case "C":
                x=2;
                break;
            case "D":
                x=3;
                break;
            case "E":
                x=4;
                break;
            case "F":
                x=5;
                break;
            case "G":
                x=6;
                break;
            case "H":
                x=7;
                break;
            default: x=-1;
        }
        //now we have x and y values for the player's selection


        //TODO: put code here to process player move
        //need to make move, get results, update mBoard array

        //change the images on the buttons after processing the move
        updateBoardButtons();


        //TODO: call method to do comp turn, update again
    }

    //TODO: meke methods to switch turn view, call inside each turn


    //TODO method: check for game over, call after EVERY turn,
    //if over, update statistics (if time)
    //if over, give prompt to play again, if yes call startNewGame()
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
        //mUseAutoSave = sp.getBoolean(mKEY_AUTO_SAVE, true);
    }
    private void showSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        settingsLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> settingsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> restoreOrSetFromPreferences_AllAppAndGameSettings());


}