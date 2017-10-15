package net.matthiasauer.ga.calculation.domain;

import java.util.Collection;

public interface TerminationAlgorithm<T extends Chromosome> {
    boolean terminate(Collection<T> chromosomes);
}
