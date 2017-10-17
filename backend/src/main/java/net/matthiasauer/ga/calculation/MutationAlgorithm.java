package net.matthiasauer.ga.calculation;

import java.util.Collection;

public interface MutationAlgorithm<T extends Chromosome> {
    Collection<T> mutate(Collection<T> chromosome);
}
