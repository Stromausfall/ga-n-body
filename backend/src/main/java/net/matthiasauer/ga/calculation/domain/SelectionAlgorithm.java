package net.matthiasauer.ga.calculation.domain;

import java.util.Collection;

public interface SelectionAlgorithm<T extends Chromosome, S extends ExperimentArgument> {
    Collection<ParentChromosomes<T>> selectParents(Collection<T> chromosomes, S experimentArgument);
}
