package net.matthiasauer.ga.calculation;

import java.util.Collection;

public interface GenerateAlgorithm<T extends Chromosome, S extends ExperimentArgument> {
    Collection<T> generate(S experimentArgument);
}
