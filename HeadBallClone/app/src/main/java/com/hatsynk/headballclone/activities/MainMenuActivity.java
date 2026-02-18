package com.hatsynk.headballclone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hatsynk.headballclone.R;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnCustomize = findViewById(R.id.btnCustomize);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the game
                startActivity(new Intent(MainMenuActivity.this, GameActivity.class));
            }
        });

        btnCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to selection screen
                startActivity(new Intent(MainMenuActivity.this, AvatarSelectionActivity.class));
            }
        });
    }
}