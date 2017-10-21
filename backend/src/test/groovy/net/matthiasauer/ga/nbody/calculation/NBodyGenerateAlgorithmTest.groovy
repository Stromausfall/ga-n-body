package net.matthiasauer.ga.nbody.calculation

import net.matthiasauer.ga.calculation.RandomProvider
import spock.lang.Specification
import spock.lang.Unroll

class NBodyGenerateAlgorithmTest extends Specification {
    @Unroll
    def "test the that the generate method produces the exact number of chromosomes"() {
        given:
            RandomProvider randomProvider = Mock(RandomProvider)
            randomProvider.nextDouble() >> 0.5
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withPopulationSize(populationSize)
                            .build()


            NBodyGenerateAlgorithm generateAlgorithm = new NBodyGenerateAlgorithm(randomProvider)

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
            RandomProvider randomProvider = Mock(RandomProvider)
            randomProvider.nextDouble() >> 0.5
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withPopulationSize(2)
                            .withAllelesPerChromosome(allelesPereChromosome)
                            .build()

            NBodyGenerateAlgorithm generateAlgorithm = new NBodyGenerateAlgorithm(randomProvider)

        when:
            Collection<NBodyChromosome> chromosomes = generateAlgorithm.generate(experimentArgument)

        then:
            chromosomes[0].getAlleles().size() == allelesPereChromosome
            chromosomes[1].getAlleles().size() == allelesPereChromosome

        where:
            allelesPereChromosome << [5, 10, 20]
    }

    def "test that the alleles of a generated chromosome have random values"() {
        given:
            RandomProvider randomProvider = Mock(RandomProvider)
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withPopulationSize(1)
                            .withMinMass(10)
                            .withMaxMass(100)
                            .withMinPosXY(5)
                            .withMaxPosXY(50)
                            .withMinVelocityXY(1)
                            .withMaxVelocityYY(10)
                            .withAllelesPerChromosome(1)
                            .build()

            NBodyGenerateAlgorithm generateAlgorithm = new NBodyGenerateAlgorithm(randomProvider)

        when:
            Collection<NBodyChromosome> chromosomes = generateAlgorithm.generate(experimentArgument)
            NBodyAllele allele = chromosomes[0].getAlleles()[0]

        then:
            // random for mass
            1 * randomProvider.nextDouble(10, 100) >> 12.0d

            // random for posXY
            2 * randomProvider.nextDouble(5, 50) >> 13.0d

            // random for velocityXY
            2 * randomProvider.nextDouble(1, 10) >> 14.0d

            // check that the values are assigned correctly
            allele.mass == 12.0d
            allele.posX == 13.0d
            allele.posY == 13.0d
            allele.velocityX == 14.0d
            allele.velocityY == 14.0d
    }
}
