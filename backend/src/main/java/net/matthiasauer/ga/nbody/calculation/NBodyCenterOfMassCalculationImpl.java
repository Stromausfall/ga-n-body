package net.matthiasauer.ga.nbody.calculation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NBodyCenterOfMassCalculationImpl implements NBodyCenterOfMassCalculation {
    @Override
    public Vector2D calculate(List<NBodyAllele> bodies) {
        // calculate the mass of all bodies
        double totalMass = bodies.stream().mapToDouble(NBodyAllele::getMass).sum();

        double centerOfMassX = bodies.stream().mapToDouble(body -> (body.getMass() / totalMass) * body.getPosX()).sum();
        double centerOfMassY = bodies.stream().mapToDouble(body -> (body.getMass() / totalMass) * body.getPosY()).sum();

        return new Vector2D(centerOfMassX, centerOfMassY);
    }
}
