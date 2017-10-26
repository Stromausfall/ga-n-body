package net.matthiasauer.ga.calculation

import spock.lang.Specification

class AbstractExperimentTest extends Specification {

    void "test experiment calls the correct Algorithms (perform one full iteration of the experiment)"() {
        given:
            GenerateAlgorithm<Chromosome, ExperimentArgument> generateAlgorithm = Mock(GenerateAlgorithm)
            SelectionAlgorithm<Chromosome, ExperimentArgument> selectionAlgorithm = Mock(SelectionAlgorithm)
            CrossoverAlgorithm<Chromosome, ExperimentArgument> crossoverAlgorithm = Mock(CrossoverAlgorithm)
            MutationAlgorithm<Chromosome, ExperimentArgument> mutationAlgorithm = Mock(MutationAlgorithm)
            ReplaceAlgorithm<Chromosome> replaceAlgorithm = Mock(ReplaceAlgorithm)
            TerminationAlgorithm<Chromosome> terminationAlgorithm = Mock(TerminationAlgorithm)

            AbstractExperiment experiment = new AbstractExperiment(
                    generateAlgorithm,
                    selectionAlgorithm,
                    crossoverAlgorithm,
                    mutationAlgorithm,
                    replaceAlgorithm,
                    terminationAlgorithm) {}

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
            ExperimentArgument experimentArgument = Mock(ExperimentArgument)

        when:
            experiment.execute(experimentArgument)

        then:
            // first the generate algorithm should be called
            1 * generateAlgorithm.generate(experimentArgument) >> generatedPopulation

        then:
            // perform the selection algorithm to create the parents for the new chromosomes
            1 * selectionAlgorithm.selectParents(generatedPopulation, experimentArgument) >> parentChromosomes

        then:
            // perform crossover
            1 * crossoverAlgorithm.createOffspring(parentChromosomes, experimentArgument) >> childPopulation

        then:
            // perform mutation
            1 * mutationAlgorithm.mutate(childPopulation, experimentArgument) >> mutatedChildPopulation

        then:
            // the fitness of each chromosome should be evaluated
            1 * generatedPopulation[0].calculateFitness(experimentArgument)
            1 * generatedPopulation[1].calculateFitness(experimentArgument)

        then:
            // select
            1 * replaceAlgorithm.newPopulation(generatedPopulation, mutatedChildPopulation) >> replacedPopulation

        then:
            // check if the experiment can be terminated
            1 * terminationAlgorithm.terminate(replacedPopulation) >> true

        then:
            // make sure that the experiment is terminated
            0 * selectionAlgorithm.selectParents(_ as Collection<Chromosome>, _ as ExperimentArgument)
            0 * crossoverAlgorithm.createOffspring(_ as Collection<ParentChromosomes<Chromosome>>, _ as ExperimentArgument)
            0 * mutationAlgorithm.mutate(_ as Collection<Chromosome>, _ as ExperimentArgument)
            0 * replaceAlgorithm.newPopulation(_ as Collection<Chromosome>, _ as Collection<Chromosome>)
            0 * terminationAlgorithm.terminate(_ as Collection<Chromosome>)
            0 * generateAlgorithm.generate(_ as ExperimentArgument)
    }
}
