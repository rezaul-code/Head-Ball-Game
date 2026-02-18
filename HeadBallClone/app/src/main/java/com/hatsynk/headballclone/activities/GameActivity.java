package com.hatsynk.headballclone.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.hatsynk.headballclone.game.HeadBallEngine; // Ensure this import matches

public class GameActivity extends Activity {

    private HeadBallEngine gameEngine; // Use HeadBallEngine, NOT GameView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        gameEngine = new HeadBallEngine(this); // Launch Engine
        setContentView(gameEngine);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}