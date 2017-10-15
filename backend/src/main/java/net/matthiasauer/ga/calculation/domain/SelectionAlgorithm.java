package net.matthiasauer.ga.calculation.domain;

import java.util.Collection;

public interface SelectionAlgorithm<T extends Chromosome> {
    Collection<ParentChromosomes<T>> selectParents(Collection<T> chromosomes, int parentsToSelect);
}
