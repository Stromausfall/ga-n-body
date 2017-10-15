package net.matthiasauer.ga.calculation.domain;

import java.util.Collection;

public interface GenerateAlgorithm<T extends Chromosome> {
    Collection<T> generate(int toGenerate);
}
