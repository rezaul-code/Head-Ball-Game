package com.hatsynk.headballclone.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import com.hatsynk.headballclone.game.Constants;

public class Player {
    private float x, y, startX, startY;
    private float velocityX, velocityY;
    private int kickTimer = 0;
    private boolean isFacingRight;

    // Avatar Image
    private Bitmap avatar;
    private Paint shoeP;

    public Player(Context context, int drawableId, float x, float y, boolean faceRight) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.isFacingRight = faceRight;

        // Load the image
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), drawableId);

        // Resize image to match the player size
        int size = Constants.PLAYER_RADIUS * 2;
        avatar = Bitmap.createScaledBitmap(original, size, size, true);

        shoeP = new Paint();
        shoeP.setColor(Color.DKGRAY);
    }

    public void update() {
        if (kickTimer > 0) kickTimer--;
        if (Math.abs(velocityX) > 1) isFacingRight = velocityX > 0;
    }

    public void moveLeft() { velocityX = -Constants.PLAYER_MOVE_SPEED; }
    public void moveRight() { velocityX = Constants.PLAYER_MOVE_SPEED; }
    public void stop() { velocityX = 0; }

    public void jump() {
        if (Math.abs(velocityY) < 1) velocityY = Constants.PLAYER_JUMP_POWER;
    }

    public void kick(Ball ball, boolean high) {
        if (kickTimer > 0) return;

        float dx = ball.getX() - x;
        float dy = ball.getY() - y;
        if (Math.sqrt(dx*dx + dy*dy) < Constants.KICK_RANGE) {
            float dir = isFacingRight ? 1 : -1;
            float px = high ? Constants.KICK_HIGH_POWER_X : Constants.KICK_LOW_POWER_X;
            float py = high ? Constants.KICK_HIGH_POWER_Y : Constants.KICK_LOW_POWER_Y;

            ball.setVelocityX(px * dir);
            ball.setVelocityY(py);
            ball.setRotationalVelocity(high ? -20 * dir : 10 * dir);

            kickTimer = (int)Constants.KICK_COOLDOWN;
        }
    }

    public void draw(Canvas canvas, Ball ball) {
        canvas.save();
        canvas.translate(x, y);

        // Draw the Avatar Image (Centered)
        canvas.drawBitmap(avatar, -Constants.PLAYER_RADIUS, -Constants.PLAYER_RADIUS, null);

        // Draw Shoe
        int r = Constants.PLAYER_RADIUS;
        float sx = isFacingRight ? 15 : -15;
        if (kickTimer > 10) sx += (isFacingRight ? 20 : -20);

        canvas.drawOval(new RectF(sx-20, r-30, sx+20, r), shoeP);

        canvas.restore();
    }

    public void reset() { x=startX; y=startY; velocityX=0; velocityY=0; }

    // Physics Getters/Setters
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public float getVelocityX() { return velocityX; }
    public void setVelocityX(float v) { this.velocityX = v; }
    public float getVelocityY() { return velocityY; }
    public void setVelocityY(float v) { this.velocityY = v; }
    public int getRadius() { return Constants.PLAYER_RADIUS; }
}