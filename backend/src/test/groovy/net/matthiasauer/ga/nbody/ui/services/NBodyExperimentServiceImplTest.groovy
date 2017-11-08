package net.matthiasauer.ga.nbody.ui.services

import net.matthiasauer.ga.calculation.ExperimentArgument
import net.matthiasauer.ga.nbody.calculation.NBodyChromosome
import net.matthiasauer.ga.nbody.calculation.NBodyExperiment
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument
import net.matthiasauer.ga.nbody.repository.NBodyChromosomeFitnessRepository
import spock.lang.Specification

class NBodyExperimentServiceImplTest extends Specification {
    def "test that the createService method starts an experiment with the given arguments"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            ExperimentArgument experimentArgument = new NBodyExperimentArgument.Builder().build()
            NBodyChromosomeFitnessRepository repository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment, repository)

        when:
            classUnderTest.createExperiment(experimentArgument)

            // give some time for the executor to start the experiment
            Thread.sleep(100)

        then:
            1 * experiment.execute(experimentArgument)
    }

    def "test that the getFittest method returns the fittest from the repository"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            NBodyChromosomeFitnessRepository repository = Mock(NBodyChromosomeFitnessRepository)
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment, repository)
            NBodyChromosome chromosome = new NBodyChromosome([], 2.0)

        when:
            NBodyChromosome fittest = classUnderTest.getFittestChromosome()

        then:
            // expect that the repo is called
            1 * repository.getFittest() >> chromosome

            // expect that the returned chromosome is the one returned by the repo
            fittest == chromosome
    }
}
