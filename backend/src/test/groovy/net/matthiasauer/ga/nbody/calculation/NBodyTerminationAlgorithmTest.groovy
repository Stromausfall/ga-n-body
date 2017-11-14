package net.matthiasauer.ga.nbody.calculation

import net.matthiasauer.ga.nbody.repositories.NBodyChromosomeFitnessRepository
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformationRepository
import spock.lang.Specification
import spock.lang.Unroll

class NBodyTerminationAlgorithmTest extends Specification {
    @Unroll
    void "check that if the maximum amount of iterations is reached - the algorithm is stopped"() {
        given:
            NBodyChromosomeFitnessRepository repository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
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
                    new NBodyTerminationAlgorithm(repository, experimentInformationRepository)

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
            NBodyChromosomeFitnessRepository repository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
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
                    new NBodyTerminationAlgorithm(repository, experimentInformationRepository)

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

    void "check that all chromosomes are added to the nbodyChromosomeFitnessRepository"() {
        given:
            NBodyChromosomeFitnessRepository repository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withTerminationMaxIterations(100)
                            .withTerminationTargetFitness(25)
                            .build()
            Collection<NBodyChromosome> population = [
                    new NBodyChromosome([], 2.0),
                    new NBodyChromosome([], 0.0)
            ]
            NBodyTerminationAlgorithm classUnderTest =
                    new NBodyTerminationAlgorithm(repository, experimentInformationRepository)

        when:
            classUnderTest.terminate(population, experimentArgument, 2)

        then:
            1 * repository.add(population[0])
            1 * repository.add(population[1])
            1 * experimentInformationRepository.store(experimentArgument, 2)
    }

    void "check that all chromosomes are added to the nbodyChromosomeFitnessRepository (even if we have reached the max iteration)"() {
        given:
            NBodyChromosomeFitnessRepository repository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withTerminationMaxIterations(100)
                            .withTerminationTargetFitness(25)
                            .build()
            Collection<NBodyChromosome> population = [
                    new NBodyChromosome([], 2.0),
                    new NBodyChromosome([], 0.0)
            ]
            NBodyTerminationAlgorithm classUnderTest =
                    new NBodyTerminationAlgorithm(repository, experimentInformationRepository)

        when:
            classUnderTest.terminate(population, experimentArgument, 200)

        then:
            1 * repository.add(population[0])
            1 * repository.add(population[1])
            1 * experimentInformationRepository.store(experimentArgument, 200)
    }
}
