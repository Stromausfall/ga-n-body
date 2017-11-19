package net.matthiasauer.ga.nbody.ui.services

import net.matthiasauer.ga.nbody.calculation.*
import net.matthiasauer.ga.nbody.repositories.NBodyChromosomeFitnessRepository
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformation
import net.matthiasauer.ga.nbody.repositories.NBodyExperimentInformationRepository
import net.matthiasauer.ga.nbody.ui.domain.CenterOfMassDTO
import net.matthiasauer.ga.nbody.ui.domain.NBodyExperimentArgumentDTO
import net.matthiasauer.ga.nbody.ui.domain.NBodyIterationInformationDTO
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import spock.lang.Specification
import spock.lang.Unroll

class NBodyExperimentServiceImplTest extends Specification {
    def "test that the createService method starts an experiment with the given arguments (and clears the repositories)"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            NBodyExperimentArgumentDTO experimentArgument = new NBodyExperimentArgumentDTO().with {
                it.populationSize = 234

                return it
            }
            NBodyChromosomeFitnessRepository fitnessRepository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
            NBodyFitnessAlgorithm fitnessAlgorithm = Mock(NBodyFitnessAlgorithm)
            NBodyCenterOfMassCalculation centerOfMassCalculation = Mock(NBodyCenterOfMassCalculation)
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment, fitnessRepository, experimentInformationRepository, fitnessAlgorithm, centerOfMassCalculation)
        when:
            classUnderTest.createExperiment(experimentArgument)

            // give some time for the executor to start the experiment
            Thread.sleep(100)

        then:
            1 * fitnessRepository.clear()
            1 * experimentInformationRepository.clear()
        then:
            1 * experiment.execute(_) >> {
                it ->
                    assert it.arguments[0] instanceof NBodyExperimentArgument
                    assert ((NBodyExperimentArgument) it.arguments[0]).populationSize == 234
            }
    }

    def "test that the getFittest method returns the fittest from the repository (and the iteration steps are returned)"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            NBodyChromosomeFitnessRepository fitnessRepository = Mock(NBodyChromosomeFitnessRepository)
            NBodyFitnessAlgorithm fitnessAlgorithm = Mock(NBodyFitnessAlgorithm)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
            NBodyCenterOfMassCalculation centerOfMassCalculation = new NBodyCenterOfMassCalculationImpl()
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment, fitnessRepository, experimentInformationRepository, fitnessAlgorithm, centerOfMassCalculation)
            NBodyExperimentArgument experimentArgument = new NBodyExperimentArgument.Builder().withPopulationSize(10).build()
            NBodyExperimentInformation experimentInformation = new NBodyExperimentInformation().with {
                it.setCurrentIteration(24)
                it.setNBodyExperimentArgument(experimentArgument)

                return it
            }

            NBodyChromosome chromosome = new NBodyChromosome([], 2.0)
            List<List<NBodyAllele>> iterationSteps = [
                    [new NBodyAllele(0, 0, 0, 0, 0), new NBodyAllele(0, 0, 0, 0, 0)],
                    [new NBodyAllele(0, 0, 0, 0, 0), new NBodyAllele(0, 0, 0, 0, 0)]
            ]

        when:
            NBodyIterationInformationDTO data = classUnderTest.getCurrentIteration()

        then:
            // expect that the repos are queried
            1 * fitnessRepository.getFittest() >> chromosome
            1 * experimentInformationRepository.getLatest() >> experimentInformation

            // expecte that the fitnessAlgorithm is called to get the iteration steps for the fittest chromosome
            1 * fitnessAlgorithm.getIterationSteps(chromosome, experimentArgument) >> iterationSteps

            // expect that the returned data is the one returned by the repo
            data.iteration == 24
            data.experimentArgument.getPopulationSize() == 10
            data.fittest.fitness == 2.0d
            data.fittest.iterations.size() == 2
    }

    def "test that the center of mass is calculated for each iteration"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            NBodyChromosomeFitnessRepository fitnessRepository = Mock(NBodyChromosomeFitnessRepository)
            NBodyFitnessAlgorithm fitnessAlgorithm = Mock(NBodyFitnessAlgorithm)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
            NBodyCenterOfMassCalculation centerOfMassCalculation = Mock(NBodyCenterOfMassCalculation)
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment, fitnessRepository, experimentInformationRepository, fitnessAlgorithm, centerOfMassCalculation)
            NBodyExperimentArgument experimentArgument = new NBodyExperimentArgument.Builder().withPopulationSize(10).build()
            NBodyExperimentInformation experimentInformation = new NBodyExperimentInformation().with {
                it.setCurrentIteration(24)
                it.setNBodyExperimentArgument(experimentArgument)

                return it
            }

            NBodyChromosome chromosome = new NBodyChromosome([], 2.0)
            List<List<NBodyAllele>> iterationSteps = [
                    [new NBodyAllele(0, 0, 0, 0, 0), new NBodyAllele(0, 0, 0, 0, 0)],
                    [new NBodyAllele(0, 0, 0, 0, 0), new NBodyAllele(0, 0, 0, 0, 0)]
            ]
            Vector2D iteration1CenterOfMass = new Vector2D(2, 3)
            Vector2D iteration2CenterOfMass = new Vector2D(6, 4)

        when:
            NBodyIterationInformationDTO data = classUnderTest.getCurrentIteration()

        then:
            // expect that the repos are queried
            1 * fitnessRepository.getFittest() >> chromosome
            1 * experimentInformationRepository.getLatest() >> experimentInformation

            // expecte that the fitnessAlgorithm is called to get the iteration steps for the fittest chromosome
            1 * fitnessAlgorithm.getIterationSteps(chromosome, experimentArgument) >> iterationSteps

            // the calculation should be called
            1 * centerOfMassCalculation.calculate(iterationSteps[0]) >> iteration1CenterOfMass
            1 * centerOfMassCalculation.calculate(iterationSteps[1]) >> iteration2CenterOfMass

            // ensure the center of mass is stored in each iteration
            data.fittest.fitness == 2.0d
            data.fittest.iterations[0].centerOfMass.x == 2
            data.fittest.iterations[0].centerOfMass.y == 3
            data.fittest.iterations[1].centerOfMass.x == 6
            data.fittest.iterations[1].centerOfMass.y == 4
    }

    @Unroll
    def "test that if at least one of the repositories returns null - null is returned"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            NBodyChromosomeFitnessRepository fitnessRepository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentInformationRepository experimentInformationRepository = Mock(NBodyExperimentInformationRepository)
            NBodyFitnessAlgorithm fitnessAlgorithm = Mock(NBodyFitnessAlgorithm)
            NBodyCenterOfMassCalculation centerOfMassCalculation = Mock(NBodyCenterOfMassCalculation)
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment, fitnessRepository, experimentInformationRepository, fitnessAlgorithm, centerOfMassCalculation)

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
