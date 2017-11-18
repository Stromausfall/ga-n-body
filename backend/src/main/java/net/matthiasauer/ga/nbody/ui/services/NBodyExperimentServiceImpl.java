package net.matthiasauer.ga.nbody.ui.services;

import net.matthiasauer.ga.nbody.calculation.NBodyChromosome;
import net.matthiasauer.ga.nbody.calculation.NBodyExperiment;
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;
import net.matthiasauer.ga.nbody.repositories.NBodyChromosomeFitnessRepository;
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformation;
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformationRepository;
import net.matthiasauer.ga.nbody.ui.domain.NBodyChromosomeDTO;
import net.matthiasauer.ga.nbody.ui.domain.NBodyExperimentArgumentDTO;
import net.matthiasauer.ga.nbody.ui.domain.NBodyIterationInformationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NBodyExperimentServiceImpl implements NBodyExperimentService {
    private final NBodyExperiment experiment;
    private final ExecutorService executorService;
    private final NBodyChromosomeFitnessRepository fitnessRepository;
    private final NBodyExperimentInformationRepository experimentInformationRepository;

    @Autowired
    public NBodyExperimentServiceImpl(NBodyExperiment experiment, NBodyChromosomeFitnessRepository fitnessRepository, NBodyExperimentInformationRepository experimentInformationRepository) {
        this.experiment = experiment;
        this.fitnessRepository = fitnessRepository;
        this.experimentInformationRepository = experimentInformationRepository;
        this.executorService =
                Executors.newSingleThreadExecutor();
    }

    private void startExperiment(NBodyExperimentArgument experimentArgument) {
        // first clear all repositories
        this.fitnessRepository.clear();
        this.experimentInformationRepository.clear();

        // then start the experiment
        this.experiment.execute(experimentArgument);
    }

    @Override
    public synchronized void createExperiment(NBodyExperimentArgumentDTO experimentArgument) {
        // start the experiment on the thread
        this.executorService.submit(() -> this.startExperiment(experimentArgument.toNBodyExperimentArgument()));
    }

    @Override
    public NBodyIterationInformationDTO getCurrentIteration() {
        NBodyExperimentInformation experimentInformation = this.experimentInformationRepository.getLatest();
        NBodyChromosome fittest = this.fitnessRepository.getFittest();

        if ((experimentInformation == null) || (fittest == null)) {
            return null;
        }

        NBodyIterationInformationDTO result = new NBodyIterationInformationDTO();
        result.setFittest(NBodyChromosomeDTO.from(fittest));
        result.setIteration(experimentInformation.getCurrentIteration());
        result.setExperimentArgument(NBodyExperimentArgumentDTO.from(experimentInformation.getNBodyExperimentArgument()));

        return result;
    }
}
