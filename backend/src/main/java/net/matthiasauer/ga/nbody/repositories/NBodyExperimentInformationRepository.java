package net.matthiasauer.ga.nbody.repositories;

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;

public interface NBodyExperimentInformationRepository {
    void store(NBodyExperimentArgument experimentArgument, int currentIteration);
    NBodyExperimentInformation getLatest();
    void clear();
}
