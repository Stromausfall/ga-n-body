package net.matthiasauer.ga.calculation.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Experiment<T extends Chromosome> {
    private final GenerateAlgorithm<T> generateAlgorithm;
    private final SelectionAlgorithm<T> selectionAlgorithm;
    private final CrossoverAlgorithm<T> crossoverAlgorithm;
    private final MutationAlgorithm<T> mutationAlgorithm;
    private final ReplaceAlgorithm<T> replaceAlgorithm;
    private final TerminationAlgorithm<T> terminationAlgorithm;

    @Autowired
    public Experiment(
            GenerateAlgorithm<T> generateAlgorithm,
            SelectionAlgorithm<T> selectionAlgorithm,
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

    public void execute(final int populationCount, final int newPopulationCount) {
        // see http://www.obitko.com/tutorials/genetic-algorithms/ga-basic-description.php

        // START - create a new population
        Collection<T> population = this.generateAlgorithm.generate(populationCount);

        // FITNESS + TEST
        while(!isExperimentTerminated(population)) {
            // SELECTION - select parents from the population
            Collection<ParentChromosomes<T>> parents = this.selectionAlgorithm.selectParents(population, newPopulationCount);

            // CROSSOVER - create children from the parents
            Collection<T> children = this.crossoverAlgorithm.createOffspring(parents);

            // MUTATE - mutate the children
            Collection<T> mutatedChildren = this.mutationAlgorithm.mutate(children);

            // REPLACE - merge the old and new population
            population = this.replaceAlgorithm.newPopulation(population, mutatedChildren);
        }
    }
}
