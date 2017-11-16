package net.matthiasauer.ga.nbody.repositories;

import net.matthiasauer.ga.nbody.calculation.NBodyChromosome;

public interface NBodyChromosomeFitnessRepository {
    void add(NBodyChromosome chromosome);
    NBodyChromosome getFittest();
    void clear();
}
