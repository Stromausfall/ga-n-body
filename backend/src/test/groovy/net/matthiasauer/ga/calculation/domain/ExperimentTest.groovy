package net.matthiasauer.ga.calculation.domain

import spock.lang.Specification

class ExperimentTest extends Specification {

    void "test experiment calls the correct Algorithms (perform one full iteration of the experiment)"() {
        given:
            int populationCount = 2
            int newPopulationSize = 1

            GenerateAlgorithm<Chromosome> generateAlgorithm = Mock(GenerateAlgorithm)
            SelectionAlgorithm<Chromosome> selectionAlgorithm = Mock(SelectionAlgorithm)
            CrossoverAlgorithm<Chromosome> crossoverAlgorithm = Mock(CrossoverAlgorithm)
            MutationAlgorithm<Chromosome> mutationAlgorithm = Mock(MutationAlgorithm)
            ReplaceAlgorithm<Chromosome> replaceAlgorithm = Mock(ReplaceAlgorithm)
            TerminationAlgorithm<Chromosome> terminationAlgorithm = Mock(TerminationAlgorithm)

            Experiment experiment = new Experiment(
                    generateAlgorithm,
                    selectionAlgorithm,
                    crossoverAlgorithm,
                    mutationAlgorithm,
                    replaceAlgorithm,
                    terminationAlgorithm)


            Collection<Chromosome> generatedPopulation = [
                    Mock(Chromosome), Mock(Chromosome)
            ]
            Collection<ParentChromosomes<Chromosome>> parentChromosomes = [
                    new ParentChromosomes<Chromosome>(
                            generatedPopulation[0],
                            generatedPopulation[1]
                    )
            ]
            Collection<Chromosome> childPopulation = [Mock(Chromosome)]
            Collection<Chromosome> mutatedChildPopulation = [Mock(Chromosome)]
            Collection<Chromosome> replacedPopulation = [generatedPopulation[0], mutatedChildPopulation[0]]

        when:
            experiment.execute(populationCount, newPopulationSize)

        then:
            // first the generate algorithm should be called
            1 * generateAlgorithm.generate(populationCount) >> generatedPopulation

        then:
            // the fitness of each chromosome should be evaluated
            1 * generatedPopulation[0].calculateFitness()
            1 * generatedPopulation[1].calculateFitness()

        then:
            // check if the experiment can be terminated
            1 * terminationAlgorithm.terminate(generatedPopulation) >> false

        then:
            // perform the selection algorithm to create the parents for the new chromosomes
            1 * selectionAlgorithm.selectParents(generatedPopulation, newPopulationSize) >> parentChromosomes

        then:
            // perform crossover
            1 * crossoverAlgorithm.createOffspring(parentChromosomes) >> childPopulation

        then:
            // perform mutation
            1 * mutationAlgorithm.mutate(childPopulation) >> mutatedChildPopulation

        then:
            // select
            1 * replaceAlgorithm.newPopulation(generatedPopulation, mutatedChildPopulation) >> replacedPopulation

        then:
            // ONE iteration finished, now the next one is started
            // the fitness of each chromsome should be evaluated
            1 * replacedPopulation[0].calculateFitness()
            1 * replacedPopulation[1].calculateFitness()

        then:
            // check if the experiment can be terminated
            1 * terminationAlgorithm.terminate(replacedPopulation) >> true

        then:
            // make sure that the experiment is terminated
            0 * selectionAlgorithm.selectParents(_ as Collection<Chromosome>, _ as int)
            0 * crossoverAlgorithm.createOffspring(_ as Collection<ParentChromosomes<Chromosome>>)
            0 * mutationAlgorithm.mutate(_ as Collection<Chromosome>)
            0 * replaceAlgorithm.newPopulation(_ as Collection<Chromosome>, _ as Collection<Chromosome>, _ as int)
            0 * terminationAlgorithm.terminate(_ as Collection<Chromosome>)
            0 * generateAlgorithm.generate(_ as int)
    }
}