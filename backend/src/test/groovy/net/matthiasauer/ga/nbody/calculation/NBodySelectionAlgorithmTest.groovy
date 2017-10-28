package net.matthiasauer.ga.nbody.calculation

import net.matthiasauer.ga.calculation.ParentChromosomes
import net.matthiasauer.ga.calculation.RandomProvider
import net.matthiasauer.ga.calculation.ThreadSafeRandomProvider
import spock.lang.Specification
import spock.lang.Unroll

class NBodySelectionAlgorithmTest extends Specification {
    void "test that if there is only one chromosome - then an exception is raised"() {
        given:
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withNewPopulationSize(100)
                            .build()
            RandomProvider randomProvider = Mock(RandomProvider)

            NBodySelectionAlgorithm classUnderTest = new NBodySelectionAlgorithm(randomProvider)
            Collection<NBodyChromosome> population = [
                    new NBodyChromosome([])
            ]

        when:
            classUnderTest.selectParents(population, experimentArgument)

        then:
            thrown IllegalArgumentException
    }

    @Unroll
    void "test that parents are note the same (test for a population of 2 and a request for #newPopulationSize parents)"() {
        given:
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withNewPopulationSize(newPopulationSize)
                            .build()
            RandomProvider randomProvider = new ThreadSafeRandomProvider()

            NBodySelectionAlgorithm classUnderTest = new NBodySelectionAlgorithm(randomProvider)
            Collection<NBodyChromosome> population = [
                    new NBodyChromosome([]),
                    new NBodyChromosome([])
            ]

        when:
            Collection<ParentChromosomes<NBodyChromosome>> parents = classUnderTest.selectParents(population, experimentArgument)

        then:
            // first assert that the correct population number has been generated
            parents.size() == newPopulationSize

            // then ensure that parents are not identical !
            for (ParentChromosomes<NBodyChromosome> parent in parents) {
                assert parent.parentA != parent.parentB
            }

        where:
            newPopulationSize << [1, 5, 10, 50]
    }

    @Unroll
    void "test that the parents are picked using a random generator"() {
        given:
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withNewPopulationSize(50)
                            .build()
            RandomProvider randomProvider = new ThreadSafeRandomProvider()

            NBodySelectionAlgorithm classUnderTest = new NBodySelectionAlgorithm(randomProvider)
            Collection<NBodyChromosome> population = [
                    new NBodyChromosome([]),
                    new NBodyChromosome([]),
                    new NBodyChromosome([]),
                    new NBodyChromosome([])
            ]

        when:
            Collection<ParentChromosomes<NBodyChromosome>> parents = classUnderTest.selectParents(population, experimentArgument)

        then:
            Set<NBodyChromosome> parentsUsed = new HashSet<>()

            for (ParentChromosomes<NBodyChromosome> parent in parents) {
                // ensure that parents are not identical !
                assert parent.parentA != parent.parentB

                parentsUsed.add(parent.parentA)
                parentsUsed.add(parent.parentB)
            }

            // this ensure that we don't always use the same two parents!
            parentsUsed.size() > 2
    }
}
