package net.matthiasauer.ga.calculation;

import java.util.Collection;

public interface ReplaceAlgorithm<T extends Chromosome> {
    Collection<T> newPopulation(Collection<T> oldPopulation, Collection<T> newGeneratedPopulation);
}
