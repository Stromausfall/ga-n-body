package net.matthiasauer.ga.calculation;

import java.math.BigDecimal;

public interface Chromosome<T extends ExperimentArgument> {
    BigDecimal calculateFitness(T experimentArgument);
}
