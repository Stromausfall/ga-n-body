package net.matthiasauer.ga.calculation;

public interface Chromosome<T extends ExperimentArgument, R extends Allele> {
    double getFitness();
}
