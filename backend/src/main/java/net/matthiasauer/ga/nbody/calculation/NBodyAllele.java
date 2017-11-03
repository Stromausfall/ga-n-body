package net.matthiasauer.ga.nbody.calculation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class NBodyAllele {
    private final double posX;
    private final double posY;
    private final double mass;
    private final double velocityX;
    private final double velocityY;

    public NBodyAllele(double posX, double posY, double mass, double velocityX, double velocityY) {
        this.posX = posX;
        this.posY = posY;
        this.mass = mass;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getMass() {
        return mass;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public Vector2D getVelocity() {
        return new Vector2D(
                this.getVelocityX(),
                this.getVelocityY()
        );
    }

    public Vector2D getPosition() {
        return new Vector2D(
                this.getPosX(),
                this.getPosY()
        );
    }
}
