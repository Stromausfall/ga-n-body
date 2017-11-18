package net.matthiasauer.ga.nbody.ui.services;

import net.matthiasauer.ga.nbody.ui.domain.NBodyExperimentArgumentDTO;
import net.matthiasauer.ga.nbody.ui.domain.NBodyIterationInformationDTO;

public interface NBodyExperimentService {
    void createExperiment(NBodyExperimentArgumentDTO experimentArgument);

    NBodyIterationInformationDTO getCurrentIteration();
}
