package com.hatsynk.headballclone.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private HeadBallEngine gameEngine; // CHANGED FROM GameView TO HeadBallEngine
    private boolean running;

    public GameThread(HeadBallEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.surfaceHolder = gameEngine.getHolder();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long targetTime = Constants.TARGET_FRAME_TIME;

        while (running) {
            startTime = System.nanoTime();
            Canvas canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if (canvas != null) {
                        gameEngine.update();
                        gameEngine.draw(canvas);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if (waitTime > 0) sleep(waitTime);
            } catch (Exception e) {}
        }
    }
}