package net.matthiasauer.ga.nbody.calculation;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class NBodyFitnessTerminationAlgorithmImpl implements NBodyFitnessTerminationAlgorithm {
    private boolean areBodiesInCorrectDistanceToEachOther(NBodyAllele body1, NBodyAllele body2, NBodyExperimentArgument experimentArgument) {
        double distance = body1.getPosition().distance(body2.getPosition());

        if (body1 == body2) {
            return true;
        }

        boolean tooNear = distance < experimentArgument.getFitnessMinDistanceBetweenBodies();
        boolean tooFar = distance > experimentArgument.getFitnessMaxDistanceBetweenBodies();

        // only correct distance if neither too near or too far
        return !tooNear && !tooFar;
    }


    private boolean isBodyInCorrectDistanceToOtherBodies(NBodyAllele body, Collection<NBodyAllele> allBodies, NBodyExperimentArgument experimentArgument) {
        // check each body against the 'body' argument
        return allBodies.stream()
                .allMatch(otherBody -> this.areBodiesInCorrectDistanceToEachOther(body, otherBody, experimentArgument));
    }

    @Override
    public boolean areBodiesInCorrectDistanceToEachOther(Collection<NBodyAllele> bodies, NBodyExperimentArgument experimentArgument) {
        // call for all bodies
        return bodies.stream()
                .allMatch(body -> this.isBodyInCorrectDistanceToOtherBodies(body, bodies, experimentArgument));
    }
}
