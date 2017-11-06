package net.matthiasauer.ga.nbody.ui.services

import net.matthiasauer.ga.calculation.ExperimentArgument
import net.matthiasauer.ga.nbody.calculation.NBodyExperiment
import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument
import spock.lang.Specification

class NBodyExperimentImplServiceImplTest extends Specification {
    def "test that the createService method starts an experiment with the given arguments"() {
        given:
            NBodyExperiment experiment = Mock(NBodyExperiment)
            ExperimentArgument experimentArgument = new NBodyExperimentArgument.Builder().build()
            NBodyExperimentService classUnderTest = new NBodyExperimentServiceImpl(experiment)

        when:
            classUnderTest.createExperiment(experimentArgument)

        then:
            1 * experiment.execute(experimentArgument)
    }
}
