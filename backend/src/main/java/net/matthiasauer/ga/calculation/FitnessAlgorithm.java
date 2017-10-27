package net.matthiasauer.ga.calculation;

import java.util.Collection;

public interface FitnessAlgorithm<T extends Chromosome, S extends ExperimentArgument> {
    Collection<T> calculate(Collection<T> population, S experimentArgument);
}
