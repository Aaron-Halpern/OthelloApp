package com.example.othello.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.othello.R;
import com.example.othello.lib.Utils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this,R.xml.root_preferences,false);
        Utils.setNightModeOnOffFromPreferenceValue(getApplicationContext(),getString(R.string.night_mode_key));

        startActivity (new Intent(getApplicationContext (), MainActivity.class));
        finish ();
    }
}