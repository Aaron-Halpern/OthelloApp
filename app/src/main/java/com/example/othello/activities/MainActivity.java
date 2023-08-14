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

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
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