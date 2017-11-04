package net.matthiasauer.ga.nbody.calculation;

import java.util.Collection;

public interface NBodyFitnessTerminationAlgorithm {
    boolean areBodiesInCorrectDistanceToEachOther(Collection<NBodyAllele> bodies, NBodyExperimentArgument experimentArgument);
}
