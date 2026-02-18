package com.hatsynk.headballclone.physics;

import com.hatsynk.headballclone.game.Constants;
import com.hatsynk.headballclone.objects.Ball;
import com.hatsynk.headballclone.objects.Player;

public class AdvancedPhysics {

    private int groundLevel;
    private int screenWidth;

    // Physics sub-steps for stability
    private static final int SUB_STEPS = 4;
    private static final float DT = 1.0f / SUB_STEPS;

    // Restitution (Bounciness)
    private static final float WALL_BOUNCE = 0.7f;
    private static final float GROUND_BOUNCE = 0.6f;

    public AdvancedPhysics(int groundLevel, int screenWidth, int screenHeight) {
        this.groundLevel = groundLevel;
        this.screenWidth = screenWidth;
    }

    public void update(Player p1, Player p2, Ball ball) {
        // Run physics multiple times per frame for precision
        for (int i = 0; i < SUB_STEPS; i++) {
            step(p1, p2, ball);
        }
    }

    private void step(Player p1, Player p2, Ball ball) {
        // 1. Move Players (The missing part from before)
        integratePlayer(p1);
        integratePlayer(p2);

        // 2. Move Ball
        integrateBall(ball);

        // 3. Solve Collisions
        resolveWallCollisions(ball);
        resolvePlayerBall(p1, ball);
        resolvePlayerBall(p2, ball);
        resolvePlayerPlayer(p1, p2);
    }

    private void integratePlayer(Player p) {
        // Apply Gravity if in air
        if (p.getY() + p.getRadius() < groundLevel) {
            p.setVelocityY(p.getVelocityY() + Constants.GRAVITY * DT);
        }

        // Apply Velocity to Position
        p.setX(p.getX() + p.getVelocityX() * DT);
        p.setY(p.getY() + p.getVelocityY() * DT);

        // Floor Collision
        if (p.getY() + p.getRadius() > groundLevel) {
            p.setY(groundLevel - p.getRadius());
            p.setVelocityY(0);
        }

        // Screen Bounds
        if (p.getX() - p.getRadius() < 0) {
            p.setX(p.getRadius());
            p.setVelocityX(0);
        }
        if (p.getX() + p.getRadius() > screenWidth) {
            p.setX(screenWidth - p.getRadius());
            p.setVelocityX(0);
        }
    }

    private void integrateBall(Ball b) {
        // Gravity
        b.setVelocityY(b.getVelocityY() + Constants.GRAVITY * DT);

        // Air Resistance
        b.setVelocityX(b.getVelocityX() * 0.998f);

        // Apply Velocity
        b.setX(b.getX() + b.getVelocityX() * DT);
        b.setY(b.getY() + b.getVelocityY() * DT);

        // Spin Physics (Magnus Effect approximation)
        b.setRotationAngle(b.getRotationAngle() + b.getRotationalVelocity() * DT);
        b.setRotationalVelocity(b.getRotationalVelocity() * 0.995f);
    }

    private void resolveWallCollisions(Ball b) {
        float r = b.getRadius();

        // Floor
        if (b.getY() + r > groundLevel) {
            b.setY(groundLevel - r);
            b.setVelocityY(-b.getVelocityY() * GROUND_BOUNCE);

            // Friction on ground
            b.setVelocityX(b.getVelocityX() * Constants.GROUND_FRICTION);

            // Spin from rolling
            b.setRotationalVelocity(b.getRotationalVelocity() + b.getVelocityX() * 0.5f);
        }

        // Ceiling
        if (b.getY() - r < 0) {
            b.setY(r);
            b.setVelocityY(-b.getVelocityY() * WALL_BOUNCE);
        }

        // Walls
        if (b.getX() - r < 0) {
            b.setX(r);
            b.setVelocityX(-b.getVelocityX() * WALL_BOUNCE);
        }
        if (b.getX() + r > screenWidth) {
            b.setX(screenWidth - r);
            b.setVelocityX(-b.getVelocityX() * WALL_BOUNCE);
        }
    }

    private void resolvePlayerBall(Player p, Ball b) {
        float dx = b.getX() - p.getX();
        float dy = b.getY() - p.getY();
        float distSq = dx*dx + dy*dy;
        float minDist = p.getRadius() + b.getRadius();

        if (distSq < minDist * minDist) {
            float dist = (float) Math.sqrt(distSq);
            float nx = dx / dist;
            float ny = dy / dist;

            // Push ball out
            float overlap = minDist - dist;
            b.setX(b.getX() + nx * overlap);
            b.setY(b.getY() + ny * overlap);

            // Velocity Reflection
            float relVx = b.getVelocityX() - p.getVelocityX();
            float relVy = b.getVelocityY() - p.getVelocityY();
            float velAlongNormal = relVx * nx + relVy * ny;

            if (velAlongNormal > 0) return;

            float j = -(1 + 1.2f) * velAlongNormal;
            b.setVelocityX(b.getVelocityX() + j * nx);
            b.setVelocityY(b.getVelocityY() + j * ny);

            // Transfer Kick Power (if player is moving)
            b.applyForce(p.getVelocityX() * 0.8f, p.getVelocityY() * 0.8f);

            // Add Spin based on hit angle
            float tangentMag = relVx * (-ny) + relVy * nx;
            b.setRotationalVelocity(b.getRotationalVelocity() + tangentMag * 0.7f);
        }
    }

    private void resolvePlayerPlayer(Player p1, Player p2) {
        float dx = p2.getX() - p1.getX();
        float dy = p2.getY() - p1.getY();
        float dist = (float) Math.sqrt(dx*dx + dy*dy);
        float min = p1.getRadius() + p2.getRadius();

        if (dist < min) {
            float overlap = (min - dist) / 2f;
            float nx = dx / dist;

            p1.setX(p1.getX() - nx * overlap);
            p2.setX(p2.getX() + nx * overlap);
        }
    }
}