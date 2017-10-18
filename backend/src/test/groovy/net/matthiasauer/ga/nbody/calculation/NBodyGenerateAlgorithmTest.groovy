package net.matthiasauer.ga.nbody.calculation

import spock.lang.Specification
import spock.lang.Unroll

class NBodyGenerateAlgorithmTest extends Specification {
    @Unroll
    def "test the that the generate method produces the exact number of chromosomes"() {
        given:
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withPopulationSize(populationSize)
                            .build()


            NBodyGenerateAlgorithm generateAlgorithm = new NBodyGenerateAlgorithm()

        when:
            Collection<NBodyChromosome> chromosomes = generateAlgorithm.generate(experimentArgument)

        then:
            chromosomes.size() == populationSize

        where:
            populationSize << [2, 5, 10]
    }

    @Unroll
    def "test that each generated chromosome has the requested number of alleles"() {
        given:
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withPopulationSize(2)
                            .withAllelesPerChromosome(allelesPereChromosome)
                            .build()

            NBodyGenerateAlgorithm generateAlgorithm = new NBodyGenerateAlgorithm()

        when:
            Collection<NBodyChromosome> chromosomes = generateAlgorithm.generate(experimentArgument)

        then:
            chromosomes[0].getAlleles().size() == allelesPereChromosome
            chromosomes[1].getAlleles().size() == allelesPereChromosome

        where:
            allelesPereChromosome << [5, 10, 20]
    }
}
