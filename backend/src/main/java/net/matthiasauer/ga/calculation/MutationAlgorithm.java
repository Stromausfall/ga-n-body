package net.matthiasauer.ga.calculation;

import java.util.Collection;

public interface MutationAlgorithm<T extends Chromosome, S extends ExperimentArgument> {
    Collection<T> mutate(Collection<T> chromosome, S experimentArgument);
}
