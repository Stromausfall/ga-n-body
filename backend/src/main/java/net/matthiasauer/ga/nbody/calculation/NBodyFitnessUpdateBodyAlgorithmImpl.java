package net.matthiasauer.ga.nbody.calculation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class NBodyFitnessUpdateBodyAlgorithmImpl implements NBodyFitnessUpdateBodyAlgorithm {
    private Vector2D getForceOnBodyToUpdate(NBodyAllele bodyToUpdate, NBodyAllele otherBody, NBodyExperimentArgument experimentArgument) {
        Vector2D arrowToOtherBody = otherBody.getPosition().subtract(bodyToUpdate.getPosition());
        double r = arrowToOtherBody.getNorm();
        double g = experimentArgument.getFitnessGravityConstant();
        double m1 = bodyToUpdate.getMass();
        double m2 = otherBody.getMass();

        // G * (m1*m2)/r
        double f = (g * m1 * m2) / r;

        // normalize the arrowToOtherBody and then scsale it to f
        Vector2D normalizedArrowToOtherBody = arrowToOtherBody.normalize();

        return normalizedArrowToOtherBody.scalarMultiply(f);
    }

    @Override
    public NBodyAllele updatetBody(NBodyAllele bodyToUpdate, Collection<NBodyAllele> allBodies, NBodyExperimentArgument experimentArgument) {
        // collect all forces that act on the body
        Collection<Vector2D> forcesOnBodyToUpdate =
                allBodies.stream()
                        // don't get the force of the body on itself
                        .filter(body -> body != bodyToUpdate)
                        .map(body -> getForceOnBodyToUpdate(bodyToUpdate, body, experimentArgument))
                        .collect(Collectors.toList());

        // now get the sum of all forces
        Vector2D totalForcesOnBodyToUpdate =
                // ALL forces (the forces of the other bodies as well as the already existing forces on the body
                Stream.concat(
                        forcesOnBodyToUpdate.stream(),
                        Stream.of(bodyToUpdate.getVelocity())
                )
                        .reduce(Vector2D.ZERO, (x, y) -> x.add(y));

        // update the bodyToUpdate and return it
        return new NBodyAllele(
                bodyToUpdate.getPosX() + totalForcesOnBodyToUpdate.getX(),
                bodyToUpdate.getPosY() + totalForcesOnBodyToUpdate.getY(),
                bodyToUpdate.getMass(),
                totalForcesOnBodyToUpdate.getX(),
                totalForcesOnBodyToUpdate.getY());
    }
}
