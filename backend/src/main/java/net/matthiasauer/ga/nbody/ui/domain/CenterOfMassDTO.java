package net.matthiasauer.ga.nbody.ui.domain;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class CenterOfMassDTO {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static CenterOfMassDTO from(Vector2D vector2D) {
        CenterOfMassDTO dto = new CenterOfMassDTO();
        dto.setX(vector2D.getX());
        dto.setY(vector2D.getY());

        return dto;
    }
}
