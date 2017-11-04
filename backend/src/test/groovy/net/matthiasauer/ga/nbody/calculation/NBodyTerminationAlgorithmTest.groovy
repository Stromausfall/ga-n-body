package net.matthiasauer.ga.nbody.calculation

import spock.lang.Specification
import spock.lang.Unroll

class NBodyTerminationAlgorithmTest extends Specification {
    @Unroll
    void "check that if the maximum amount of iterations is reached - the algorithm is stopped"() {
        given:
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withTerminationMaxIterations(100)
                            .withTerminationTargetFitness(25)
                            .build()
            Collection<NBodyChromosome> population = [
                    new NBodyChromosome([], 0.0),
                    new NBodyChromosome([], 0.0)
            ]
            NBodyTerminationAlgorithm classUnderTest =
                    new NBodyTerminationAlgorithm()

        when:
            boolean result = classUnderTest.terminate(population, experimentArgument, iteration)

        then:
            result == expectedResult

        where:
            iteration || expectedResult
            10        || false
            99        || false
            100       || false
            101       || true
            1000      || true
    }

    @Unroll
    void "check that if a chromosome has reached the required fitness - the algorithm is terminated"() {
        given:
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withTerminationMaxIterations(100)
                            .withTerminationTargetFitness(25)
                            .build()
            Collection<NBodyChromosome> population = [
                    new NBodyChromosome([], fitness),
                    new NBodyChromosome([], 0.0)
            ]
            NBodyTerminationAlgorithm classUnderTest =
                    new NBodyTerminationAlgorithm()

        when:
            boolean result = classUnderTest.terminate(population, experimentArgument, 2)

        then:
            result == expectedResult

        where:
            fitness    || expectedResult
            Double.NaN || false
            0          || false
            5          || false
            24         || false
            25         || false
            26         || true
            100        || true
    }
}
