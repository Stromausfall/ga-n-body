package net.matthiasauer.ga.calculation;

import java.util.Collection;

public abstract class AbstractExperiment<T extends Chromosome<S>, S extends ExperimentArgument> implements Experiment<S> {
    private final GenerateAlgorithm<T, S> generateAlgorithm;
    private final SelectionAlgorithm<T, S> selectionAlgorithm;
    private final CrossoverAlgorithm<T, S> crossoverAlgorithm;
    private final MutationAlgorithm<T> mutationAlgorithm;
    private final ReplaceAlgorithm<T> replaceAlgorithm;
    private final TerminationAlgorithm<T> terminationAlgorithm;

    public AbstractExperiment(
            GenerateAlgorithm<T, S> generateAlgorithm,
            SelectionAlgorithm<T, S> selectionAlgorithm,
            CrossoverAlgorithm<T, S> crossoverAlgorithm,
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

    public void execute(final S experimentArgument) {
        // see http://www.obitko.com/tutorials/genetic-algorithms/ga-basic-description.php

        // START - create a new population
        Collection<T> population = this.generateAlgorithm.generate(experimentArgument);

        // FITNESS + TEST
        do {
            // SELECTION - select parents from the population
            Collection<ParentChromosomes<T>> parents = this.selectionAlgorithm.selectParents(population, experimentArgument);

            // CROSSOVER - create children from the parents
            Collection<T> children = this.crossoverAlgorithm.createOffspring(parents, experimentArgument);

            // MUTATE - mutate the children
            Collection<T> mutatedChildren = this.mutationAlgorithm.mutate(children);

            // FITNESS
            population.parallelStream().forEach(
                    (T element) -> element.calculateFitness(experimentArgument));
            mutatedChildren.parallelStream().forEach(
                    (T element) -> element.calculateFitness(experimentArgument));


            // REPLACE - merge the old and new population
            population = this.replaceAlgorithm.newPopulation(population, mutatedChildren);

            // FINISH - check the termination criteria
        } while (this.terminationAlgorithm.terminate(population));
    }
}
