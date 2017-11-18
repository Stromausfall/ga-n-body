package net.matthiasauer.ga.calculation;

import java.util.Collection;

public abstract class AbstractExperiment<T extends Chromosome<S, R>, S extends ExperimentArgument, R extends Allele> implements Experiment<S> {
    private final GenerateAlgorithm<T, S> generateAlgorithm;
    private final FitnessAlgorithm<T, S, R> fitnessAlgorithm;
    private final SelectionAlgorithm<T, S> selectionAlgorithm;
    private final CrossoverAlgorithm<T, S, R> crossoverAlgorithm;
    private final MutationAlgorithm<T, S> mutationAlgorithm;
    private final ReplaceAlgorithm<T, S> replaceAlgorithm;
    private final TerminationAlgorithm<T, S> terminationAlgorithm;

    public AbstractExperiment(
            GenerateAlgorithm<T, S> generateAlgorithm,
            FitnessAlgorithm<T, S, R> fitnessAlgorithm,
            SelectionAlgorithm<T, S> selectionAlgorithm,
            CrossoverAlgorithm<T, S, R> crossoverAlgorithm,
            MutationAlgorithm<T, S> mutationAlgorithm,
            ReplaceAlgorithm<T, S> replaceAlgorithm,
            TerminationAlgorithm<T, S> terminationAlgorithm) {
        this.generateAlgorithm = generateAlgorithm;
        this.fitnessAlgorithm = fitnessAlgorithm;
        this.selectionAlgorithm = selectionAlgorithm;
        this.crossoverAlgorithm = crossoverAlgorithm;
        this.mutationAlgorithm = mutationAlgorithm;
        this.replaceAlgorithm = replaceAlgorithm;
        this.terminationAlgorithm = terminationAlgorithm;
    }

    public void execute(final S experimentArgument) {
        // see http://www.obitko.com/tutorials/genetic-algorithms/ga-basic-description.php

        // START - create a new population
        Collection<T> population = this.generateAlgorithm.generate(experimentArgument);

        // FITNESS - calculate the fitness for the initial population
        population = this.fitnessAlgorithm.calculate(population, experimentArgument);

        int currentIteration = 0;

        do {
            // SELECTION - select parents from the population
            Collection<ParentChromosomes<T>> parents = this.selectionAlgorithm.selectParents(population, experimentArgument);

            // CROSSOVER - create children from the parents
            Collection<T> children = this.crossoverAlgorithm.createOffspring(parents, experimentArgument);

            // MUTATE - mutate the children
            Collection<T> mutatedChildren = this.mutationAlgorithm.mutate(children, experimentArgument);

            // FITNESS - calculate fitness of the mutated children (we already calculated the fitness of the population)
            Collection<T> mutatedChildrenWithFitness = this.fitnessAlgorithm.calculate(mutatedChildren, experimentArgument);

            // REPLACE - merge the old and new population
            population = this.replaceAlgorithm.newPopulation(population, mutatedChildrenWithFitness, experimentArgument);

            // increase the currentIteration counter
            currentIteration++;

            // FINISH - check the termination criteria
        } while (!this.terminationAlgorithm.terminate(population, experimentArgument, currentIteration));
    }
}
