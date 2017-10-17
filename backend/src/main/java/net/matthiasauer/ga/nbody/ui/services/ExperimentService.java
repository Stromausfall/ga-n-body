package net.matthiasauer.ga.nbody.ui.services;

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;

public interface ExperimentService {
    Integer createExperiment(NBodyExperimentArgument experimentArgument);
}
