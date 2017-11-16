package net.matthiasauer.ga.nbody.ui.services

import net.matthiasauer.ga.calculation.ExperimentArgument
import net.matthiasauer.ga.nbody.calculation.NBodyChromosome
import net.matthiasauer.ga.nbody.calculation.NBodyExperiment
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument
import net.matthiasauer.ga.nbody.repositories.NBodyChromosomeFitnessRepository
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformation
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformationRepository
import net.matthiasauer.ga.nbody.ui.domain.NBodyIterationInformationDTO
import spock.lang.Specification
import spock.lang.Unroll

class NBodyExperimentServiceImplTest extends Specification {
    def "test that the createService method starts an experiment with the given arguments (and clears the repositories)"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            ExperimentArgument experimentArgument = new NBodyExperimentArgument.Builder().build()
            NBodyChromosomeFitnessRepository fitnessRepository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment, fitnessRepository, experimentInformationRepository)
        when:
            classUnderTest.createExperiment(experimentArgument)

            // give some time for the executor to start the experiment
            Thread.sleep(100)

        then:
            1 * fitnessRepository.clear()
            1 * experimentInformationRepository.clear()
        then:
            1 * experiment.execute(experimentArgument)
    }

    def "test that the getFittest method returns the fittest from the repository"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            NBodyChromosomeFitnessRepository fitnessRepository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment, fitnessRepository, experimentInformationRepository)
            NBodyExperimentArgument experimentArgument = new NBodyExperimentArgument.Builder().withPopulationSize(10).build()
            NBodyExperimentInformation experimentInformation = new NBodyExperimentInformation().with {
                it.setCurrentIteration(24)
                it.setNBodyExperimentArgument(experimentArgument)

                return it
            }

            NBodyChromosome chromosome = new NBodyChromosome([], 2.0)

        when:
            NBodyIterationInformationDTO data = classUnderTest.getCurrentIteration()

        then:
            // expect that the repos are queried
            1 * fitnessRepository.getFittest() >> chromosome
            1 * experimentInformationRepository.getLatest() >> experimentInformation

            // expect that the returned data is the one returned by the repo
            data.iteration == 24
            data.experimentArgument.getPopulationSize() == 10
            data.fittest.fitness == 2.0d
            data.fittest.alleles.size() == 0
    }

    @Unroll
    def "test that if at least one of the repositories returns null - null is returned"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            NBodyChromosomeFitnessRepository fitnessRepository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment, fitnessRepository, experimentInformationRepository)

            fitnessRepository.getFittest() >> chromosome
            experimentInformationRepository.getLatest() >> experimentInformation

        when:
            NBodyIterationInformationDTO data = classUnderTest.getCurrentIteration()

        then:
            noExceptionThrown()

            data == null

        where:
            chromosome                   | experimentInformation
            null                         | null
            new NBodyChromosome([], 2.0) | null
            null                         | new NBodyExperimentInformation()
    }
}
