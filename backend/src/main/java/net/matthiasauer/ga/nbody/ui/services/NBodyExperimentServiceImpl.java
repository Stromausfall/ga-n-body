package net.matthiasauer.ga.nbody.ui.services;

import net.matthiasauer.ga.nbody.calculation.NBodyExperiment;
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NBodyExperimentServiceImpl implements NBodyExperimentService {
    private final NBodyExperiment experiment;

    @Autowired
    public NBodyExperimentServiceImpl(NBodyExperiment experiment) {
        this.experiment = experiment;
    }

    @Override
    public void createExperiment(NBodyExperimentArgument experimentArgument) {
        this.experiment.execute(experimentArgument);
    }
}
