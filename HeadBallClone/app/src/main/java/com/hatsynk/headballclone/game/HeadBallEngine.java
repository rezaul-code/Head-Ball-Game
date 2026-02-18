package com.hatsynk.headballclone.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// MAKE SURE THIS IMPORT IS HERE. If it's red, build your project once.
import com.hatsynk.headballclone.R;

import com.hatsynk.headballclone.objects.Ball;
import com.hatsynk.headballclone.objects.Goal;
import com.hatsynk.headballclone.objects.ParticleSystem;
import com.hatsynk.headballclone.objects.Player;
import com.hatsynk.headballclone.physics.AdvancedPhysics;

public class HeadBallEngine extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private AdvancedPhysics physics;
    private BotIntelligence botAI;
    private ParticleSystem particles;

    private Player player, opponent;
    private Ball ball;
    private Goal leftGoal, rightGoal;

    // Game State
    private int playerScore = 0, opponentScore = 0;
    private int gameTimer = 90;
    private long lastTime = 0;
    private boolean isGameOver = false;
    private int pauseTimer = 0;

    // Rendering
    private int screenWidth, screenHeight, groundLevel;
    private Paint textPaint;

    // Input Controls
    private RectF btnLeft, btnRight, btnJump, btnKickLow, btnKickHigh;

    // Game Over Controls
    private RectF btnRestart, btnExit;

    private boolean isLeftPressed, isRightPressed;

    public HeadBallEngine(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setFakeBoldText(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setShadowLayer(5, 2, 2, Color.BLACK);
    }

    private void initMatch() {
        screenWidth = getWidth();
        screenHeight = getHeight();
        groundLevel = (int) (screenHeight * 0.75f);

        physics = new AdvancedPhysics(groundLevel, screenWidth, screenHeight);
        botAI = new BotIntelligence(screenWidth, groundLevel);
        particles = new ParticleSystem();

        // --- AVATAR SETUP (UPDATED) ---

        // 1. Define the list of available avatars.
        // IMPORTANT: These MUST match the image filenames in your res/drawable folder.
        int[] availableAvatars = {
                R.drawable.avatar_1,
                R.drawable.avatar_2,
                R.drawable.avatar_3,
                R.drawable.avatar_4
        };

        // Ensure we have at least one avatar to avoid crashes
        int defaultAvatar = (availableAvatars.length > 0) ? availableAvatars[0] : R.mipmap.ic_launcher;

        // 2. Get access to saved settings
        SharedPreferences prefs = getContext().getSharedPreferences("GameSettings", Context.MODE_PRIVATE);

        // 3. Load Player's choice (Load saved ID, fallback to default if none saved)
        int playerImg = prefs.getInt("PLAYER_AVATAR_ID", defaultAvatar);

        // 4. Pick a random avatar for the bot from the available list
        int botImg;
        if (availableAvatars.length > 0) {
            botImg = availableAvatars[(int)(Math.random() * availableAvatars.length)];
        } else {
            botImg = defaultAvatar;
        }

        // Initialize Players with loaded images
        player = new Player(getContext(), playerImg, 200, groundLevel - 100, true);
        opponent = new Player(getContext(), botImg, screenWidth - 200, groundLevel - 100, false);
        // --------------------

        ball = new Ball(screenWidth / 2, screenHeight / 3);

        leftGoal = new Goal(0, groundLevel, true);
        rightGoal = new Goal(screenWidth - Constants.GOAL_WIDTH, groundLevel, false);

        setupUI();
    }

    private void setupUI() {
        // --- GAMEPLAY BUTTONS ---
        int s = screenHeight / 5;
        int m = 30;

        btnLeft = new RectF(m, screenHeight - s - m, m + s, screenHeight - m);
        btnRight = new RectF(m + s + 20, screenHeight - s - m, m + s*2 + 20, screenHeight - m);

        int r = screenWidth - m;
        btnKickLow = new RectF(r - s, screenHeight - s - m, r, screenHeight - m);
        btnKickHigh = new RectF(r - s*2 - 20, screenHeight - s - m, r - s - 20, screenHeight - m);
        btnJump = new RectF(r - s*3 - 40, screenHeight - s - m, r - s*2 - 40, screenHeight - m);

        // --- GAME OVER BUTTONS ---
        float cx = screenWidth / 2f;
        float cy = screenHeight / 2f;
        int btnW = 300;
        int btnH = 100;

        btnRestart = new RectF(cx - btnW/2, cy + 20, cx + btnW/2, cy + 20 + btnH);
        btnExit = new RectF(cx - btnW/2, cy + 150, cx + btnW/2, cy + 150 + btnH);
    }

    public void update() {
        if (isGameOver) return;

        if (pauseTimer > 0) {
            pauseTimer--;
            if (pauseTimer == 0) resetRound();
            return;
        }

        if (System.currentTimeMillis() - lastTime > 1000) {
            gameTimer--;
            lastTime = System.currentTimeMillis();
            if (gameTimer <= 0) isGameOver = true;
        }

        if (isLeftPressed) player.moveLeft();
        else if (isRightPressed) player.moveRight();
        else player.stop();

        player.update();
        opponent.update();
        ball.update();
        particles.update();

        botAI.update(opponent, ball);
        physics.update(player, opponent, ball);

        if (leftGoal.checkGoal(ball)) score(false);
        else if (rightGoal.checkGoal(ball)) score(true);
    }

    private void score(boolean playerScored) {
        if (playerScored) playerScore++; else opponentScore++;

        pauseTimer = 60;
        particles.spawn(ball.getX(), ball.getY(), 50, Color.YELLOW);

        if (playerScore >= 5 || opponentScore >= 5) {
            isGameOver = true;
        }
    }

    private void resetRound() {
        ball.reset(screenWidth/2, screenHeight/3);
        player.reset();
        opponent.reset();
        ball.setVelocityX(Math.random() > 0.5 ? 2 : -2);
    }

    private void resetGameFull() {
        playerScore = 0;
        opponentScore = 0;
        gameTimer = 90;
        isGameOver = false;
        resetRound();
        // Note: We don't reload avatars here to keep it quick.
        // If you want new avatars on restart, call initMatch() instead,
        // but you'd need to recreate threads which is complex.
        // Current approach is standard for "Play Again".
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas == null) return;

        canvas.drawColor(Constants.COLOR_SKY_TOP);
        Paint grass = new Paint();
        grass.setColor(Constants.COLOR_GRASS_LIGHT);
        canvas.drawRect(0, groundLevel, screenWidth, screenHeight, grass);

        leftGoal.draw(canvas);
        rightGoal.draw(canvas);
        player.draw(canvas, ball);
        opponent.draw(canvas, ball);
        ball.draw(canvas);
        particles.draw(canvas);

        if (!isGameOver) {
            drawGameplayUI(canvas);

            if (pauseTimer > 0) {
                textPaint.setTextSize(100);
                textPaint.setColor(Color.YELLOW);
                canvas.drawText("GOAL!", screenWidth/2, screenHeight/2, textPaint);
            }
        } else {
            drawGameOverScreen(canvas);
        }
    }

    private void drawGameplayUI(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.argb(100, 255, 255, 255));

        canvas.drawRoundRect(btnLeft, 20, 20, p);
        canvas.drawRoundRect(btnRight, 20, 20, p);
        canvas.drawRoundRect(btnJump, 20, 20, p);

        p.setColor(Color.argb(100, 255, 100, 100));
        canvas.drawRoundRect(btnKickLow, 20, 20, p);
        p.setColor(Color.argb(100, 100, 100, 255));
        canvas.drawRoundRect(btnKickHigh, 20, 20, p);

        Paint tp = new Paint(textPaint);
        tp.setTextSize(40);
        canvas.drawText("<", btnLeft.centerX(), btnLeft.centerY()+15, tp);
        canvas.drawText(">", btnRight.centerX(), btnRight.centerY()+15, tp);
        canvas.drawText("J", btnJump.centerX(), btnJump.centerY()+15, tp);
        canvas.drawText("LO", btnKickLow.centerX(), btnKickLow.centerY()+15, tp);
        canvas.drawText("HI", btnKickHigh.centerX(), btnKickHigh.centerY()+15, tp);

        tp.setTextSize(80);
        canvas.drawText(playerScore + " - " + opponentScore, screenWidth/2, 100, tp);
    }

    private void drawGameOverScreen(Canvas canvas) {
        canvas.drawColor(Color.argb(200, 0, 0, 0));

        String title;
        int color;

        if (playerScore > opponentScore) {
            title = "YOU WON!";
            color = Color.GREEN;
        } else {
            title = "GAME OVER";
            color = Color.RED;
        }

        textPaint.setTextSize(120);
        textPaint.setColor(color);
        canvas.drawText(title, screenWidth/2, screenHeight/2 - 100, textPaint);

        Paint btnP = new Paint();
        btnP.setColor(Color.WHITE);
        canvas.drawRoundRect(btnRestart, 20, 20, btnP);
        canvas.drawRoundRect(btnExit, 20, 20, btnP);

        Paint btnTextP = new Paint(textPaint);
        btnTextP.setColor(Color.BLACK);
        btnTextP.setTextSize(50);
        btnTextP.setShadowLayer(0,0,0,0);

        canvas.drawText("PLAY AGAIN", btnRestart.centerX(), btnRestart.centerY() + 18, btnTextP);
        canvas.drawText("EXIT", btnExit.centerX(), btnExit.centerY() + 18, btnTextP);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int idx = event.getActionIndex();
        int ptrCount = event.getPointerCount();
        float x = event.getX(idx);
        float y = event.getY(idx);

        if (isGameOver) {
            if (action == MotionEvent.ACTION_DOWN) {
                if (btnRestart.contains(x, y)) {
                    resetGameFull();
                } else if (btnExit.contains(x, y)) {
                    System.exit(0);
                }
            }
            return true;
        }

        boolean l = false, r = false;

        for (int i = 0; i < ptrCount; i++) {
            float px = event.getX(i);
            float py = event.getY(i);
            boolean isUp = (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) && idx == i;

            if (isUp) continue;

            if (btnLeft.contains(px, py)) l = true;
            if (btnRight.contains(px, py)) r = true;

            if ((action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) && idx == i) {
                if (btnJump.contains(px, py)) player.jump();
                if (btnKickLow.contains(px, py)) player.kick(ball, false);
                if (btnKickHigh.contains(px, py)) player.kick(ball, true);
            }
        }
        isLeftPressed = l;
        isRightPressed = r;
        return true;
    }

    @Override public void surfaceCreated(SurfaceHolder h) { initMatch(); thread = new GameThread(this); thread.setRunning(true); thread.start(); }
    @Override public void surfaceChanged(SurfaceHolder h, int f, int w, int ht) {}
    @Override public void surfaceDestroyed(SurfaceHolder h) { boolean retry=true; thread.setRunning(false); while(retry){try{thread.join();retry=false;}catch(Exception e){}} }
}