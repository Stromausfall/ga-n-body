package net.matthiasauer.ga.calculation;

import java.util.Collection;

public interface TerminationAlgorithm<T extends Chromosome, S extends ExperimentArgument> {
    boolean terminate(Collection<T> chromosomes, S experimentArgument, int currentIteration);
}
