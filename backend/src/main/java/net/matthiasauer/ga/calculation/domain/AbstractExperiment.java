package net.matthiasauer.ga.calculation.domain;

import java.util.Collection;

public abstract class AbstractExperiment<T extends Chromosome, S extends ExperimentArgument> implements Experiment<S> {
    private final GenerateAlgorithm<T, S> generateAlgorithm;
    private final SelectionAlgorithm<T, S> selectionAlgorithm;
    private final CrossoverAlgorithm<T> crossoverAlgorithm;
    private final MutationAlgorithm<T> mutationAlgorithm;
    private final ReplaceAlgorithm<T> replaceAlgorithm;
    private final TerminationAlgorithm<T> terminationAlgorithm;

    public AbstractExperiment(
            GenerateAlgorithm<T, S> generateAlgorithm,
            SelectionAlgorithm<T, S> selectionAlgorithm,
            CrossoverAlgorithm<T> crossoverAlgorithm,
            MutationAlgorithm<T> mutationAlgorithm,
            ReplaceAlgorithm<T> replaceAlgorithm,
            TerminationAlgorithm<T> terminationAlgorithm) {
        this.generateAlgorithm = generateAlgorithm;
        this.selectionAlgorithm = selectionAlgorithm;
        this.crossoverAlgorithm = crossoverAlgorithm;
        this.mutationAlgorithm = mutationAlgorithm;
        this.replaceAlgorithm = replaceAlgorithm;
        this.terminationAlgorithm = terminationAlgorithm;
    }

    private boolean isExperimentTerminated(Collection<T> population) {
        // FITNESS - calculate the fitness of each chromosome (in parallel)
        population.parallelStream().forEach(Chromosome::calculateFitness);

        // TEST - test whether the termination criterion has been met
        return this.terminationAlgorithm.terminate(population);
    }

    public void execute(final S experimentArgument) {
        // see http://www.obitko.com/tutorials/genetic-algorithms/ga-basic-description.php

        // START - create a new population
        Collection<T> population = this.generateAlgorithm.generate(experimentArgument);

        // FITNESS + TEST
        while (!isExperimentTerminated(population)) {
            // SELECTION - select parents from the population
            Collection<ParentChromosomes<T>> parents = this.selectionAlgorithm.selectParents(population, experimentArgument);

            // CROSSOVER - create children from the parents
            Collection<T> children = this.crossoverAlgorithm.createOffspring(parents);

            // MUTATE - mutate the children
            Collection<T> mutatedChildren = this.mutationAlgorithm.mutate(children);

            // REPLACE - merge the old and new population
            population = this.replaceAlgorithm.newPopulation(population, mutatedChildren);
        }
    }
}
