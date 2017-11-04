package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.TerminationAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class NBodyTerminationAlgorithm implements TerminationAlgorithm<NBodyChromosome, NBodyExperimentArgument> {
    @Override
    public boolean terminate(Collection<NBodyChromosome> chromosomes, NBodyExperimentArgument experimentArgument, int currentIteration) {
        // if we have already performed too many iterations
        if (currentIteration > experimentArgument.getTerminationMaxIterations()) {
            return true;
        }

        // if a chromosome reached the required minimum fitness - terminate
        return chromosomes.stream().anyMatch(chromosome -> chromosome.getFitness() > experimentArgument.getTerminationTargetFitness());
    }
}
