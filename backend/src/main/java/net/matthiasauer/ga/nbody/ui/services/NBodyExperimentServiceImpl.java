package net.matthiasauer.ga.nbody.ui.services;

import net.matthiasauer.ga.nbody.calculation.NBodyExperiment;
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NBodyExperimentServiceImpl implements NBodyExperimentService {
    private final NBodyExperiment experiment;
    private final ExecutorService executorService;

    @Autowired
    public NBodyExperimentServiceImpl(NBodyExperiment experiment) {
        this.experiment = experiment;
        this.executorService =
                Executors.newSingleThreadExecutor();
    }

    @Override
    public synchronized void createExperiment(NBodyExperimentArgument experimentArgument) {
        // start the experiment on the thread
        this.executorService.submit(() -> this.experiment.execute(experimentArgument));
    }
}
