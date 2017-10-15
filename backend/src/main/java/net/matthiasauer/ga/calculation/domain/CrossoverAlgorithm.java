package net.matthiasauer.ga.calculation.domain;

import java.util.Collection;

public interface CrossoverAlgorithm<T extends Chromosome> {
    Collection<T> createOffspring(Collection<ParentChromosomes<T>> parents);
}
