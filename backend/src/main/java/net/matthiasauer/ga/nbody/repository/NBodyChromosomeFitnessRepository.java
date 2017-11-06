package net.matthiasauer.ga.nbody.repository;

import net.matthiasauer.ga.nbody.calculation.NBodyChromosome;

public interface NBodyChromosomeFitnessRepository {
    void add(NBodyChromosome chromosome);
    NBodyChromosome getFittest();
}
