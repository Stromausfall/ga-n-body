package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.TerminationAlgorithm;
import net.matthiasauer.ga.nbody.repository.NBodyChromosomeFitnessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class NBodyTerminationAlgorithm implements TerminationAlgorithm<NBodyChromosome, NBodyExperimentArgument> {
    private final NBodyChromosomeFitnessRepository fitnessRepository;

    @Autowired
    public NBodyTerminationAlgorithm(NBodyChromosomeFitnessRepository fitnessRepository) {
        this.fitnessRepository = fitnessRepository;
    }

    @Override
    public boolean terminate(Collection<NBodyChromosome> chromosomes, NBodyExperimentArgument experimentArgument, int currentIteration) {
        // add all chromosomes to the fitnessRepository
        chromosomes.forEach(this.fitnessRepository::add);

        // if we have already performed too many iterations
        if (currentIteration > experimentArgument.getTerminationMaxIterations()) {
            return true;
        }

        // if a chromosome reached the required minimum fitness - terminate
        return chromosomes.stream().anyMatch(chromosome -> chromosome.getFitness() > experimentArgument.getTerminationTargetFitness());
    }
}
