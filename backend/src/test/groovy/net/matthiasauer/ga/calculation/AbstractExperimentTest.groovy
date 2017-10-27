package net.matthiasauer.ga.calculation

import spock.lang.Specification

class AbstractExperimentTest extends Specification {

    void "test experiment calls the correct Algorithms (perform one full iteration of the experiment)"() {
        given:
            GenerateAlgorithm<Chromosome, ExperimentArgument> generateAlgorithm = Mock(GenerateAlgorithm)
            FitnessAlgorithm<Chromosome, ExperimentArgument> fitnessAlgorithm = Mock(FitnessAlgorithm)
            SelectionAlgorithm<Chromosome, ExperimentArgument> selectionAlgorithm = Mock(SelectionAlgorithm)
            CrossoverAlgorithm<Chromosome, ExperimentArgument> crossoverAlgorithm = Mock(CrossoverAlgorithm)
            MutationAlgorithm<Chromosome, ExperimentArgument> mutationAlgorithm = Mock(MutationAlgorithm)
            ReplaceAlgorithm<Chromosome, ExperimentArgument> replaceAlgorithm = Mock(ReplaceAlgorithm)
            TerminationAlgorithm<Chromosome, ExperimentArgument> terminationAlgorithm = Mock(TerminationAlgorithm)

            AbstractExperiment experiment = new AbstractExperiment(
                    generateAlgorithm,
                    fitnessAlgorithm,
                    selectionAlgorithm,
                    crossoverAlgorithm,
                    mutationAlgorithm,
                    replaceAlgorithm,
                    terminationAlgorithm) {}

            Collection<Chromosome> generatedPopulation = [
                    Mock(Chromosome), Mock(Chromosome)
            ]
            Collection<Chromosome> generatedPopulationWithFitness = [
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
            Collection<Chromosome> mutatedChildPopulationWIthFitness = [Mock(Chromosome)]
            Collection<Chromosome> replacedPopulation = [generatedPopulation[0], mutatedChildPopulationWIthFitness[0]]
            ExperimentArgument experimentArgument = Mock(ExperimentArgument)

        when:
            experiment.execute(experimentArgument)

        then:
            // first the generate algorithm should be called
            1 * generateAlgorithm.generate(experimentArgument) >> generatedPopulation

        then:
            // after creating the initial population -> evaluate its fitness
            1 * fitnessAlgorithm.calculate(generatedPopulation, experimentArgument) >> generatedPopulationWithFitness

        then:
            // perform the selection algorithm to create the parents for the new chromosomes
            1 * selectionAlgorithm.selectParents(generatedPopulationWithFitness, experimentArgument) >> parentChromosomes

        then:
            // perform crossover
            1 * crossoverAlgorithm.createOffspring(parentChromosomes, experimentArgument) >> childPopulation

        then:
            // perform mutation
            1 * mutationAlgorithm.mutate(childPopulation, experimentArgument) >> mutatedChildPopulation

        then:
            // the fitness of the chromosomes should be evaluated
            1 * fitnessAlgorithm.calculate(mutatedChildPopulation, experimentArgument) >> mutatedChildPopulationWIthFitness

        then:
            // select
            1 * replaceAlgorithm.newPopulation(generatedPopulationWithFitness, mutatedChildPopulationWIthFitness, experimentArgument) >> replacedPopulation

        then:
            // check if the experiment can be terminated
            1 * terminationAlgorithm.terminate(replacedPopulation, experimentArgument) >> true

        then:
            // make sure that the experiment is terminated
            0 * selectionAlgorithm.selectParents(_ as Collection<Chromosome>, _ as ExperimentArgument)
            0 * fitnessAlgorithm.calculate(_ as Collection<Chromosome>, _ as ExperimentArgument)
            0 * crossoverAlgorithm.createOffspring(_ as Collection<ParentChromosomes<Chromosome>>, _ as ExperimentArgument)
            0 * mutationAlgorithm.mutate(_ as Collection<Chromosome>, _ as ExperimentArgument)
            0 * replaceAlgorithm.newPopulation(_ as Collection<Chromosome>, _ as Collection<Chromosome>, _ as ExperimentArgument)
            0 * terminationAlgorithm.terminate(_ as Collection<Chromosome>, _ as ExperimentArgument)
            0 * generateAlgorithm.generate(_ as ExperimentArgument)
    }
}
