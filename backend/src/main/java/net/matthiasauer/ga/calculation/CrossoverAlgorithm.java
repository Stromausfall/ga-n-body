package net.matthiasauer.ga.calculation;

import java.util.Collection;

public interface CrossoverAlgorithm<T extends Chromosome<S>, S extends ExperimentArgument> {
    Collection<T> createOffspring(Collection<ParentChromosomes<T>> parents, S experimentArgument);
}