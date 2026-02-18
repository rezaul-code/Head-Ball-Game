package com.hatsynk.headballclone.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.hatsynk.headballclone.game.Constants;

import java.util.ArrayList;
import java.util.List;

public class Ball {

    private float x, y;
    private float velocityX, velocityY;

    // NEW: Rotation Physics properties (These were missing!)
    private float rotationAngle = 0;
    private float rotationalVelocity = 0;

    private int radius = Constants.BALL_RADIUS;
    private Paint paint;
    private Paint borderPaint;

    // Trail effect
    private List<Point> trail;
    private Paint trailPaint;

    private static class Point {
        float x, y;
        Point(float x, float y) { this.x = x; this.y = y; }
    }

    public Ball(float x, float y) {
        this.x = x;
        this.y = y;
        initPaints();
        trail = new ArrayList<>();
    }

    private void initPaints() {
        paint = new Paint();
        paint.setAntiAlias(true);
        // 3D Ball Effect
        paint.setShader(new RadialGradient(0, 0, radius,
                Color.WHITE, Color.rgb(200, 200, 200), Shader.TileMode.CLAMP));

        borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(3);
        borderPaint.setAntiAlias(true);

        trailPaint = new Paint();
        trailPaint.setColor(Color.WHITE);
        trailPaint.setAlpha(100);
        trailPaint.setStyle(Paint.Style.FILL);
    }

    public void update() {
        // Trail Logic
        if (Math.abs(velocityX) > 5 || Math.abs(velocityY) > 5) {
            trail.add(0, new Point(x, y));
            if (trail.size() > 10) trail.remove(trail.size() - 1);
        } else if (!trail.isEmpty()) {
            trail.remove(trail.size() - 1);
        }
    }

    public void draw(Canvas canvas) {
        // Draw Trail
        for (int i = 0; i < trail.size(); i++) {
            Point p = trail.get(i);
            int alpha = 150 - (i * 15);
            trailPaint.setAlpha(Math.max(0, alpha));
            float sizeScale = 1f - (i * 0.1f);
            canvas.drawCircle(p.x, p.y, radius * sizeScale, trailPaint);
        }

        // Draw Ball
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(rotationAngle); // Visual rotation

        // Draw Soccer Ball Pattern
        paint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, radius, paint);
        canvas.drawCircle(0, 0, radius, borderPaint);

        // Draw patches (Pentagons) to visualize spin
        Paint patchPaint = new Paint();
        patchPaint.setColor(Color.BLACK);
        patchPaint.setAntiAlias(true);

        canvas.drawCircle(0, 0, radius / 2.5f, patchPaint);

        for(int i=0; i<5; i++) {
            double angle = Math.toRadians(i * 72);
            float px = (float)(Math.cos(angle) * radius * 0.8);
            float py = (float)(Math.sin(angle) * radius * 0.8);
            canvas.drawCircle(px, py, radius / 3.5f, patchPaint);
        }

        canvas.restore();
    }

    public void applyForce(float fx, float fy) {
        velocityX += fx;
        velocityY += fy;
    }

    public void reset(float x, float y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
        this.rotationAngle = 0;
        this.rotationalVelocity = 0;
        this.trail.clear();
    }

    // --- GETTERS AND SETTERS (Crucial for Physics Engine) ---

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public float getVelocityX() { return velocityX; }
    public void setVelocityX(float vx) { this.velocityX = vx; }

    public float getVelocityY() { return velocityY; }
    public void setVelocityY(float vy) { this.velocityY = vy; }

    public int getRadius() { return radius; }

    // THESE ARE THE METHODS YOUR ERROR LOG SAYS ARE MISSING:
    public float getRotationAngle() { return rotationAngle; }
    public void setRotationAngle(float angle) { this.rotationAngle = angle; }

    public float getRotationalVelocity() { return rotationalVelocity; }
    public void setRotationalVelocity(float rv) { this.rotationalVelocity = rv; }
}