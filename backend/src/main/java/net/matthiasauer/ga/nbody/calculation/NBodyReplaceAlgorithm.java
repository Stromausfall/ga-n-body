package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.ReplaceAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class NBodyReplaceAlgorithm implements ReplaceAlgorithm<NBodyChromosome, NBodyExperimentArgument> {
    @Override
    public Collection<NBodyChromosome> newPopulation(Collection<NBodyChromosome> oldPopulation, Collection<NBodyChromosome> newGeneratedPopulation, NBodyExperimentArgument experimentArgument) {
        return
                Stream.concat(
                        oldPopulation.stream(),
                        newGeneratedPopulation.stream()
                )
                        .sorted(Comparator.comparingDouble(NBodyChromosome::getFitness).reversed())
                        .limit(experimentArgument.getPopulationSize())
                        .collect(Collectors.toList());
    }
}
