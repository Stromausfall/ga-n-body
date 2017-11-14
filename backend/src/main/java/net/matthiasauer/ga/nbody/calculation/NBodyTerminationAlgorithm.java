package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.TerminationAlgorithm;
import net.matthiasauer.ga.nbody.repositories.NBodyChromosomeFitnessRepository;
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class NBodyTerminationAlgorithm implements TerminationAlgorithm<NBodyChromosome, NBodyExperimentArgument> {
    private final NBodyChromosomeFitnessRepository fitnessRepository;
    private final NBodyExperimentInformationRepository experimentInformationRepository;

    @Autowired
    public NBodyTerminationAlgorithm(
            NBodyChromosomeFitnessRepository fitnessRepository,
            NBodyExperimentInformationRepository experimentInformationRepository) {
        this.fitnessRepository = fitnessRepository;
        this.experimentInformationRepository = experimentInformationRepository;
    }

    @Override
    public boolean terminate(Collection<NBodyChromosome> chromosomes, NBodyExperimentArgument experimentArgument, int currentIteration) {
        // add all chromosomes to the fitnessRepository
        chromosomes.forEach(this.fitnessRepository::add);

        // also store experiment information in the corresponding repository
        this.experimentInformationRepository.store(experimentArgument, currentIteration);

        // if we have already performed too many iterations
        if (currentIteration > experimentArgument.getTerminationMaxIterations()) {
            return true;
        }

        // if a chromosome reached the required minimum fitness - terminate
        return chromosomes.stream().anyMatch(chromosome -> chromosome.getFitness() > experimentArgument.getTerminationTargetFitness());
    }
}
