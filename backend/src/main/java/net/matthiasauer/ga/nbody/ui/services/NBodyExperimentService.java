package net.matthiasauer.ga.nbody.ui.services;

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;
import net.matthiasauer.ga.nbody.ui.domain.NBodyIterationInformationDTO;

public interface NBodyExperimentService {
    void createExperiment(NBodyExperimentArgument experimentArgument);

    NBodyIterationInformationDTO getCurrentIteration();
}
