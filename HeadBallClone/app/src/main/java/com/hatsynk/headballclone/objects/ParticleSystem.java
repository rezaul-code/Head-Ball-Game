package com.hatsynk.headballclone.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Handles visual effects (explosions, confetti).
 * Spawns short-lived particles that fade out over time.
 */
public class ParticleSystem {

    // Inner class representing a single particle
    private class Particle {
        float x, y;
        float vx, vy;
        int life;       // How many frames the particle survives
        int maxLife;    // To calculate alpha/fading
        int color;
        float size;

        Particle(float x, float y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;

            Random r = new Random();
            // Random explosion velocity
            this.vx = (r.nextFloat() - 0.5f) * 15; // Random X spread
            this.vy = (r.nextFloat() - 0.5f) * 15; // Random Y spread

            this.life = 40 + r.nextInt(20); // Lasts 40-60 frames
            this.maxLife = this.life;
            this.size = 8 + r.nextInt(12);  // Random size
        }

        boolean update() {
            x += vx;
            y += vy;

            // Apply simple physics
            vy += 0.5f;       // Gravity pulls particles down
            vx *= 0.95f;      // Air resistance slows them horizontally

            life--;
            size *= 0.95f;    // Shrink over time

            return life > 0;  // Return false if dead
        }
    }

    private ArrayList<Particle> particles = new ArrayList<>();
    private Paint paint = new Paint();

    /**
     * Spawns a burst of particles at a specific location.
     */
    public void spawn(float x, float y, int count, int color) {
        for(int i = 0; i < count; i++) {
            particles.add(new Particle(x, y, color));
        }
    }

    /**
     * Updates all active particles.
     * Removes dead particles to save memory.
     */
    public void update() {
        Iterator<Particle> it = particles.iterator();
        while(it.hasNext()) {
            if (!it.next().update()) {
                it.remove();
            }
        }
    }

    /**
     * Draws all active particles.
     */
    public void draw(Canvas canvas) {
        for(Particle p : particles) {
            paint.setColor(p.color);

            // Calculate transparency based on remaining life
            // 255 = fully opaque, 0 = invisible
            int alpha = (int) ((float)p.life / p.maxLife * 255);
            paint.setAlpha(alpha);

            canvas.drawCircle(p.x, p.y, p.size, paint);
        }
    }
}