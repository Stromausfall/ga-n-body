package net.matthiasauer.ga.nbody.calculation;

import java.util.Collection;

public interface NBodyFitnessUpdateBodyAlgorithm {
    NBodyAllele updatetBody(NBodyAllele bodyToUpdate, Collection<NBodyAllele> allBodies, NBodyExperimentArgument experimentArgument);
}
