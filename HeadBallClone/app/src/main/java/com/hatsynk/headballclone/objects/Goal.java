package com.hatsynk.headballclone.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.hatsynk.headballclone.game.Constants;

public class Goal {

    private Rect bounds;
    private Paint postPaint;
    private Paint netPaint;
    private boolean isLeft;

    public Goal(int x, int groundLevel, boolean isLeft) {
        this.isLeft = isLeft;
        this.bounds = new Rect(x, groundLevel - Constants.GOAL_HEIGHT,
                x + Constants.GOAL_WIDTH, groundLevel);

        postPaint = new Paint();
        postPaint.setColor(Color.WHITE);
        postPaint.setStyle(Paint.Style.STROKE);
        postPaint.setStrokeWidth(12);

        netPaint = new Paint();
        netPaint.setColor(Color.LTGRAY);
        netPaint.setStrokeWidth(2);
    }

    public boolean checkGoal(Ball ball) {
        return bounds.contains((int)ball.getX(), (int)ball.getY());
    }

    public void draw(Canvas canvas) {
        // Draw Net (Crosshatch pattern)
        int gridSize = 20;
        for (int i = bounds.left; i <= bounds.right; i += gridSize) {
            canvas.drawLine(i, bounds.top, i, bounds.bottom, netPaint);
        }
        for (int i = bounds.top; i <= bounds.bottom; i += gridSize) {
            canvas.drawLine(bounds.left, i, bounds.right, i, netPaint);
        }

        // Draw Posts
        canvas.drawRect(bounds, postPaint);

        // Draw Crossbar shine
        Paint shine = new Paint();
        shine.setColor(Color.argb(100, 255,255,255));
        canvas.drawRect(bounds.left, bounds.top, bounds.right, bounds.top+5, shine);
    }
}