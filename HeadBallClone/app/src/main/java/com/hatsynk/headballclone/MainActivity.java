package com.hatsynk.headballclone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hatsynk.headballclone.activities.GameActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We don't need a layout here because we are immediately
        // redirecting to the GameActivity.

        // Create intent to launch the game directly
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

        // Finish this activity so the user can't back into a blank screen
        finish();
    }
}