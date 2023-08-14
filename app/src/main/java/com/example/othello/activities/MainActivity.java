package com.example.othello.activities;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.othello.lib.Utils.showInfoDialog;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Snackbar mSnackBar;
    private boolean mUseAutoSave;

    private final String mKEY_GAME = "GAME";
    private String mKEY_AUTO_SAVE;

    private OthelloGame mGame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupButtonListeners();
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
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

    private void playerSelection(String x, int y){
        //TODO: put code here to process player move
    }
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