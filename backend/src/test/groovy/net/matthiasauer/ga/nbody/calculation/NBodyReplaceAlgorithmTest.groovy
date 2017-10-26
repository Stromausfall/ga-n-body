package net.matthiasauer.ga.nbody.calculation

import spock.lang.Specification
import spock.lang.Unroll

class NBodyReplaceAlgorithmTest extends Specification {

    @Unroll
    void "test that the replace algorithm replaces according to fitness"() {
        given:
            final int populationSize = 3
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder().withPopulationSize(populationSize).build()

            NBodyReplaceAlgorithm replaceAlgorithm =
                    new NBodyReplaceAlgorithm()

            Collection<NBodyChromosome> oldPopulation = [new NBodyChromosome([], 4), new NBodyChromosome([], 32)]
            Collection<NBodyChromosome> newGeneratedPopulation = [new NBodyChromosome([], fitness1), new NBodyChromosome([], fitness2)]

        when:
            Collection<NBodyChromosome> newPopulation = replaceAlgorithm.newPopulation(oldPopulation, newGeneratedPopulation, experimentArgument)
            double totalFitness = (double) newPopulation.sum { it.getFitness() }
        then:
            newPopulation.size() == populationSize
            totalFitness == expectedTotalFitness

        where:
            fitness1 | fitness2 || expectedTotalFitness
            2        | 8        || 4 + 8 + 32
            1        | 2        || 2 + 4 + 32
            64       | 128      || 32 + 64 + 128
    }
}
