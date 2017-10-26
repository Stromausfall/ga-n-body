package net.matthiasauer.ga.nbody.calculation

import net.matthiasauer.ga.calculation.RandomProvider
import spock.lang.Specification

class NBodyMutationAlgorithmTest extends Specification {
    void "test that the mutationChance is used to decide whether a property of an allele is modified"() {
        given:
            RandomProvider randomProvider = Mock(RandomProvider)
            NBodyMutationAlgorithm classUnderTest = new NBodyMutationAlgorithm(randomProvider)
            Collection<NBodyChromosome> chromosomes = [
                    new NBodyChromosome([
                            new NBodyAllele(1.0d, 2.0d, 3.0d, 4.0d, 5.0d),
                            new NBodyAllele(6.0d, 7.0d, 8.0d, 9.0d, 10.0d)
                    ])
            ]
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withMinPosXY(0.0d)
                            .withMaxPosXY(10.0d)
                            .withMinMass(5.0d)
                            .withMaxMass(15.0d)
                            .withMinVelocityXY(10.0d)
                            .withMaxVelocityYY(20.0d)
                            .withMutateNucleotideChance(0.5d).build()

        when:
            Collection<NBodyChromosome> mutated = classUnderTest.mutate(chromosomes, experimentArgument)

        then:
            // skip the first allele
            5 * randomProvider.nextDouble() >> 0.99d

        then:
            // modify all nucleotides of the second allele
            5 * randomProvider.nextDouble() >> 0.2d
            // modify posX and posY of the second allele
            2 * randomProvider.nextDouble(0.0d, 10.0d) >> 9.9d
            // modify mass of the second allele
            1 * randomProvider.nextDouble(5.0d, 15.0d) >> 11.1d
            // modify velocityX and velocityY of the second allele
            2 * randomProvider.nextDouble(10.0d, 20.0d) >> 12.2d

        then:
            // first allele should be unchanged
            mutated[0].alleles[0].getPosX() == 1.0d
            mutated[0].alleles[0].getPosY() == 2.0d
            mutated[0].alleles[0].getMass() == 3.0d
            mutated[0].alleles[0].getVelocityX() == 4.0d
            mutated[0].alleles[0].getVelocityY() == 5.0d

            // second allele unchanged except for the position
            mutated[0].alleles[1].getPosX() == 9.9d
            mutated[0].alleles[1].getPosY() == 9.9d
            mutated[0].alleles[1].getMass() == 11.1d
            mutated[0].alleles[1].getVelocityX() == 12.2d
            mutated[0].alleles[1].getVelocityY() == 12.2d
    }
}
