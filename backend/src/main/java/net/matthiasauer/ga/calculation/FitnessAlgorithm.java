package net.matthiasauer.ga.calculation;

import java.util.Collection;
import java.util.List;

public interface FitnessAlgorithm<T extends Chromosome<S, R>, S extends ExperimentArgument, R extends Allele> {
    Collection<T> calculate(Collection<T> population, S experimentArgument);
    List<List<R>> getIterationSteps(T chromosome, S experimentArgument);
}