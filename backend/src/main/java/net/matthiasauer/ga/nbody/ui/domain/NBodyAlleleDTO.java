package net.matthiasauer.ga.nbody.ui.domain;

import net.matthiasauer.ga.nbody.calculation.NBodyAllele;

public class NBodyAlleleDTO {
    private double posX;
    private double posY;
    private double mass;
    private double velocityX;
    private double velocityY;

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public static NBodyAlleleDTO from(NBodyAllele nBodyAllele) {
        NBodyAlleleDTO dto = new NBodyAlleleDTO();
        dto.setPosX(nBodyAllele.getPosX());
        dto.setPosY(nBodyAllele.getPosY());
        dto.setMass(nBodyAllele.getMass());
        dto.setVelocityX(nBodyAllele.getVelocityX());
        dto.setVelocityY(nBodyAllele.getVelocityY());

        return dto;
    }
}
