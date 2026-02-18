package com.hatsynk.headballclone.game;

import com.hatsynk.headballclone.objects.Ball;
import com.hatsynk.headballclone.objects.Player;

public class BotIntelligence {
    private int screenWidth;
    private int groundLevel;

    public BotIntelligence(int w, int ground) {
        this.screenWidth = w;
        this.groundLevel = ground;
    }

    public void update(Player bot, Ball ball) {
        float ax = bot.getX();
        float ay = bot.getY();
        float bx = ball.getX();
        float by = ball.getY();

        // 1. PREDICTION LOGIC (Fixes "Slow" feel)
        // Instead of chasing the current position, run to where the ball WILL be.
        // This makes the bot run smoothly without stuttering.
        float predictedX = bx + (ball.getVelocityX() * 10);

        // Target: We want to be slightly behind the ball to hit it forward
        // If ball is to our right (attacking), stand 40px behind it.
        // If ball is to our left (defending), stand 40px in front of it (panic).
        float targetX = predictedX + 40;

        // 2. MOVEMENT LOGIC
        // "Deadzone" reduced to 5px to keep bot moving responsively
        if (bx > ax + 20) {
            // Ball is behind us! Panic sprint back to goal.
            bot.moveRight();
        }
        else if (ax < targetX - 10) {
            bot.moveRight();
        }
        else if (ax > targetX + 10) {
            bot.moveLeft();
        }
        else {
            // Only stop if we are perfectly positioned
            bot.stop();
        }

        // 3. JUMP LOGIC
        // Jump if ball is high AND close (Head it!)
        boolean ballIsHigh = by < groundLevel - 80;
        boolean ballIsClose = Math.abs(bx - ax) < 80;

        if (ballIsHigh && ballIsClose && bot.getVelocityY() == 0) {
            bot.jump();
        }

        // 4. AGGRESSIVE KICK LOGIC
        // Humans use "Low Kick" to score. Bot should too.
        if (Math.abs(bx - ax) < Constants.KICK_RANGE && Math.abs(by - ay) < Constants.KICK_RANGE) {

            // Default to Low Kick (Power Shot) for scoring
            boolean useHighKick = false;

            // Only use High Kick (Chip) if we are stuck very close to our own goal
            // (Defensive clear)
            if (ax > screenWidth * 0.85f) {
                useHighKick = true;
            }

            bot.kick(ball, useHighKick);
        }
    }
}