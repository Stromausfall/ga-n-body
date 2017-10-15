package net.matthiasauer.ga.calculation.domain;

import java.util.Collection;

public interface MutationAlgorithm<T extends Chromosome> {
    Collection<T> mutate(Collection<T> chromosome);
}
