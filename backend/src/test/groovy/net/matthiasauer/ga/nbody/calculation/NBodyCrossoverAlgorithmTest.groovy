package net.matthiasauer.ga.nbody.calculation

import net.matthiasauer.ga.calculation.ParentChromosomes
import net.matthiasauer.ga.calculation.RandomProvider
import spock.lang.Specification

class NBodyCrossoverAlgorithmTest extends Specification {
    def "test crossover produces the correct amount of children"() {
        given:
            RandomProvider randomProvider = Mock(RandomProvider)
            randomProvider.nextDouble() >> 0.25

            NBodyCrossoverAlgorithm classUnderTest = new NBodyCrossoverAlgorithm(randomProvider)
            NBodyChromosome parentA = new NBodyChromosome([
                new NBodyAllele(1.0, 1.0, 1.0, 1.0, 1.0),
                new NBodyAllele(2.0, 2.0, 2.0, 2.0, 2.0)
            ])
            NBodyChromosome parentB = new NBodyChromosome([
                    new NBodyAllele(11.0, 11.0, 11.0, 11.0, 11.0),
                    new NBodyAllele(22.0, 22.0, 22.0, 22.0, 22.0)
            ])

            Collection<ParentChromosomes<NBodyChromosome>> parents = [
                    new ParentChromosomes<NBodyChromosome>(parentA, parentB)
            ]
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withCrossOverReturnsParentLikelihood(2.0)
                            .build()

        when:
            Collection<NBodyChromosome> children = classUnderTest.createOffspring(parents, experimentArgument)

        then:
            children.size() == 1
    }

    def "test that crossover returns a parent with a certain probability"() {
        given:
            RandomProvider randomProvider = Mock(RandomProvider)

            NBodyCrossoverAlgorithm classUnderTest = new NBodyCrossoverAlgorithm(randomProvider)
            NBodyChromosome parentA = new NBodyChromosome([
                    new NBodyAllele(1.0, 1.0, 1.0, 1.0, 1.0),
                    new NBodyAllele(2.0, 2.0, 2.0, 2.0, 2.0)
            ])
            NBodyChromosome parentB = new NBodyChromosome([
                    new NBodyAllele(11.0, 11.0, 11.0, 11.0, 11.0),
                    new NBodyAllele(22.0, 22.0, 22.0, 22.0, 22.0)
            ])

            Collection<ParentChromosomes<NBodyChromosome>> parents = [
                    new ParentChromosomes<NBodyChromosome>(parentA, parentB)
            ]
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withCrossOverReturnsParentLikelihood(0.5)
                            .build()

        when:
            Collection<NBodyChromosome> children = classUnderTest.createOffspring(parents, experimentArgument)

        then:
            randomProvider.nextDouble >> 0.01
            (children[0] == parentA) || (children[0] == parentB)
    }

    def "test that crossover returns an offspring with alleles from both parents"() {
        given:
            RandomProvider randomProvider = Mock(RandomProvider)

            NBodyCrossoverAlgorithm classUnderTest = new NBodyCrossoverAlgorithm(randomProvider)
            NBodyChromosome parentA = new NBodyChromosome([
                    new NBodyAllele(1.0, 1.0, 1.0, 1.0, 1.0),
                    new NBodyAllele(2.0, 2.0, 2.0, 2.0, 2.0)
            ])
            NBodyChromosome parentB = new NBodyChromosome([
                    new NBodyAllele(11.0, 11.0, 11.0, 11.0, 11.0),
                    new NBodyAllele(22.0, 22.0, 22.0, 22.0, 22.0)
            ])

            Collection<ParentChromosomes<NBodyChromosome>> parents = [
                    new ParentChromosomes<NBodyChromosome>(parentA, parentB)
            ]
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withCrossOverReturnsParentLikelihood(0.5)
                            .build()

        when:
            Collection<NBodyChromosome> children = classUnderTest.createOffspring(parents, experimentArgument)

        then:
            randomProvider.nextDouble >> 0.99
            // offs pring is NOT a parent
            children[0] != parentA
            children[0] != parentB

            // check that the offspring is a mix of the parents alleles
            children[0].alleles[0] == parentA.alleles[0] || children[0].alleles[0] == parentB.alleles[0]
            children[0].alleles[1] == parentA.alleles[1] || children[0].alleles[1] == parentB.alleles[1]
    }
}
