package net.matthiasauer.ga.calculation;

import java.util.Collection;

public interface ReplaceAlgorithm<T extends Chromosome, S extends ExperimentArgument> {
    Collection<T> newPopulation(Collection<T> oldPopulation, Collection<T> newGeneratedPopulation, S experimentArgument);
}
