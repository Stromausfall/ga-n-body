package net.matthiasauer.ga.nbody.repositories

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument
import spock.lang.Specification

class NBodyExperimentInformationRepositoryImplTest extends Specification {
    void "test if nothing is stored - null is returned"() {
        given:
            NBodyExperimentInformationRepository classUnderTest = new NBodyExperimentInformationRepositoryImpl()

        when:
            NBodyExperimentInformation result = classUnderTest.getLatest()

        then:
            result == null
    }

    void "test that stored data can be retrieved"() {
        given:
            NBodyExperimentInformationRepository classUnderTest = new NBodyExperimentInformationRepositoryImpl()
            NBodyExperimentArgument experimentArgument = new NBodyExperimentArgument.Builder().build();

        when:
            classUnderTest.store(experimentArgument, 2)
            NBodyExperimentInformation result = classUnderTest.getLatest()

        then:
            result.getCurrentIteration() == 2
            result.getNBodyExperimentArgument() == experimentArgument
    }

    void "test that stored data can be updated"() {
        given:
            NBodyExperimentInformationRepository classUnderTest = new NBodyExperimentInformationRepositoryImpl()
            NBodyExperimentArgument experimentArgument = new NBodyExperimentArgument.Builder().build();
            NBodyExperimentArgument experimentArgument2 = new NBodyExperimentArgument.Builder().build();

        when:
            classUnderTest.store(experimentArgument, 2)
            classUnderTest.store(experimentArgument2, 3)
            NBodyExperimentInformation result = classUnderTest.getLatest()

        then:
            result.getCurrentIteration() == 3
            result.getNBodyExperimentArgument() == experimentArgument2
    }
}
