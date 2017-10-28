package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.FitnessAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class NBodyFitnessAlgorithm implements FitnessAlgorithm<NBodyChromosome, NBodyExperimentArgument> {

    @Override
    public Collection<NBodyChromosome> calculate(Collection<NBodyChromosome> population, NBodyExperimentArgument experimentArgument) {
        return null;
    }
}
