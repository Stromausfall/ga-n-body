package net.matthiasauer.ga.calculation;

import java.util.Collection;

public interface CrossoverAlgorithm<T extends Chromosome<S, R>, S extends ExperimentArgument, R extends Allele> {
    Collection<T> createOffspring(Collection<ParentChromosomes<T>> parents, S experimentArgument);
}