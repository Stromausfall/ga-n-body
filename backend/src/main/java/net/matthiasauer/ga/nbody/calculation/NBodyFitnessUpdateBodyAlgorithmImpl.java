package net.matthiasauer.ga.nbody.calculation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class NBodyFitnessUpdateBodyAlgorithmImpl implements NBodyFitnessUpdateBodyAlgorithm {
    @Override
    public NBodyAllele updatetBody(NBodyAllele bodyToUpdate, Collection<NBodyAllele> allBodies, NBodyExperimentArgument experimentArgument) {
        return null;
    }
}
