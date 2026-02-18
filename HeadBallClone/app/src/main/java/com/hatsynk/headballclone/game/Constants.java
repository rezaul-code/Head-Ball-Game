package com.hatsynk.headballclone.game;

import android.graphics.Color;

public class Constants {

    // --- Performance ---
    public static final int TARGET_FPS = 60;
    public static final long TARGET_FRAME_TIME = 1000 / TARGET_FPS;

    // We use a fixed timestep for consistent physics
    public static final float TIME_STEP = 1.0f;

    // --- Physics ---
    public static final float GRAVITY = 1.8f;
    public static final float AIR_RESISTANCE = 1.02f;
    public static final float GROUND_FRICTION = 0.85f;

    // --- Player ---
    public static final float PLAYER_MOVE_SPEED = 18f;
    public static final float PLAYER_JUMP_POWER = -22f;
    public static final int PLAYER_RADIUS = 100;

    // --- Kicking ---
    public static final int KICK_RANGE = 160;
    public static final float KICK_COOLDOWN = 20;

    public static final float KICK_LOW_POWER_X = 45f;
    public static final float KICK_LOW_POWER_Y = -5f;

    public static final float KICK_HIGH_POWER_X = 20f;
    public static final float KICK_HIGH_POWER_Y = -45f;

    // --- Ball ---
    public static final int BALL_RADIUS = 38;
    public static final float BALL_MAX_SPEED = 50f;

    // --- Rules & Goals ---
    public static final int GOAL_WIDTH = 160;
    public static final int GOAL_HEIGHT = 360; // <--- ADDED THIS BACK
    public static final int WINNING_SCORE = 10;

    // --- Visuals ---
    public static final int COLOR_SKY_TOP = Color.rgb(41, 128, 185);
    public static final int COLOR_SKY_BOTTOM = Color.rgb(109, 213, 250);
    public static final int COLOR_GRASS_LIGHT = Color.rgb(46, 204, 113);
    public static final int COLOR_GRASS_DARK = Color.rgb(39, 174, 96);

    public static final float SHAKE_INTENSITY = 25f;
    public static final float RECOVER_SPEED = 0.2f;
}