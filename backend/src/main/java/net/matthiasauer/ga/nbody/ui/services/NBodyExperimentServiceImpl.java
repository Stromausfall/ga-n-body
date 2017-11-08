package net.matthiasauer.ga.nbody.ui.services;

import net.matthiasauer.ga.nbody.calculation.NBodyChromosome;
import net.matthiasauer.ga.nbody.calculation.NBodyExperiment;
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;
import net.matthiasauer.ga.nbody.repository.NBodyChromosomeFitnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NBodyExperimentServiceImpl implements NBodyExperimentService {
    private final NBodyExperiment experiment;
    private final ExecutorService executorService;
    private final NBodyChromosomeFitnessRepository fitnessRepository;

    @Autowired
    public NBodyExperimentServiceImpl(NBodyExperiment experiment, NBodyChromosomeFitnessRepository fitnessRepository) {
        this.experiment = experiment;
        this.fitnessRepository = fitnessRepository;
        this.executorService =
                Executors.newSingleThreadExecutor();
    }

    @Override
    public synchronized void createExperiment(NBodyExperimentArgument experimentArgument) {
        // start the experiment on the thread
        this.executorService.submit(() -> this.experiment.execute(experimentArgument));
    }

    @Override
    public NBodyChromosome getFittestChromosome() {
        return this.fitnessRepository.getFittest();
    }
}
