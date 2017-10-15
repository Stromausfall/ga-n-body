package net.matthiasauer.ga.calculation.domain;

import java.math.BigDecimal;

public interface Chromosome {
    BigDecimal calculateFitness();
}
