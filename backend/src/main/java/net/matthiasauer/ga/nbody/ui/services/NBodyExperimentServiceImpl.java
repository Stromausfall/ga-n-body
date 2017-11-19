package net.matthiasauer.ga.nbody.ui.services;

import net.matthiasauer.ga.nbody.calculation.*;
import net.matthiasauer.ga.nbody.repositories.NBodyChromosomeFitnessRepository;
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformation;
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformationRepository;
import net.matthiasauer.ga.nbody.ui.domain.CalculationResultDTO;
import net.matthiasauer.ga.nbody.ui.domain.NBodyExperimentArgumentDTO;
import net.matthiasauer.ga.nbody.ui.domain.NBodyIterationInformationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NBodyExperimentServiceImpl implements NBodyExperimentService {
    private final NBodyExperiment experiment;
    private final ExecutorService executorService;
    private final NBodyChromosomeFitnessRepository fitnessRepository;
    private final NBodyFitnessAlgorithm fitnessAlgorithm;
    private final NBodyExperimentInformationRepository experimentInformationRepository;
    private final NBodyCenterOfMassCalculation centerOfMassCalculation;

    @Autowired
    public NBodyExperimentServiceImpl(
            NBodyExperiment experiment,
            NBodyChromosomeFitnessRepository fitnessRepository,
            NBodyExperimentInformationRepository experimentInformationRepository,
            NBodyFitnessAlgorithm fitnessAlgorithm,
            NBodyCenterOfMassCalculation centerOfMassCalculation) {
        this.experiment = experiment;
        this.fitnessAlgorithm = fitnessAlgorithm;
        this.fitnessRepository = fitnessRepository;
        this.centerOfMassCalculation = centerOfMassCalculation;
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

        List<List<NBodyAllele>> iterationSteps =
                this.fitnessAlgorithm.getIterationSteps(fittest, experimentInformation.getNBodyExperimentArgument());

        NBodyIterationInformationDTO result = new NBodyIterationInformationDTO();
        result.setFittest(CalculationResultDTO.from(fittest.getFitness(), iterationSteps, this.centerOfMassCalculation));
        result.setIteration(experimentInformation.getCurrentIteration());
        result.setExperimentArgument(NBodyExperimentArgumentDTO.from(experimentInformation.getNBodyExperimentArgument()));

        return result;
    }
}
