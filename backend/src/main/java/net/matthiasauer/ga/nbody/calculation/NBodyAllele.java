package net.matthiasauer.ga.nbody.calculation;

import java.math.BigDecimal;

public class NBodyAllele {
    private final BigDecimal posX;
    private final BigDecimal posY;
    private final BigDecimal mass;
    private final BigDecimal velocityX;
    private final BigDecimal velocityY;

    public NBodyAllele(BigDecimal posX, BigDecimal posY, BigDecimal mass, BigDecimal velocityX, BigDecimal velocityY) {
        this.posX = posX;
        this.posY = posY;
        this.mass = mass;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public BigDecimal getPosX() {
        return posX;
    }

    public BigDecimal getPosY() {
        return posY;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public BigDecimal getVelocityX() {
        return velocityX;
    }

    public BigDecimal getVelocityY() {
        return velocityY;
    }
}
