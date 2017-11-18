package net.matthiasauer.ga.nbody.calculation

import spock.lang.Specification

class NBodyFitnessAlgorithmImplTest extends Specification {

    void "test one iteration"() {
        given:
            NBodyFitnessTerminationAlgorithm terminationAlgorithm = Mock(NBodyFitnessTerminationAlgorithm)
            NBodyFitnessUpdateBodyAlgorithm updateBodyAlgorithm = Mock(NBodyFitnessUpdateBodyAlgorithm)
            NBodyFitnessAlgorithmImpl classUnderTest = new NBodyFitnessAlgorithmImpl(updateBodyAlgorithm, terminationAlgorithm)
            NBodyAllele allele1 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele2 = new NBodyAllele(0, 0, 1, 2.5, 2.5)
            Collection<NBodyChromosome> population = [
                    new NBodyChromosome([allele1, allele2])
            ]
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withFitnessMaxIterations(10)
                            .build()
            NBodyAllele allele3 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele4 = new NBodyAllele(0, 0, 1, 2.5, 2.5)

        when:
            Collection<NBodyChromosome> result = classUnderTest.calculate(population, experimentArgument)

        then:
            1 * updateBodyAlgorithm.updatetBody(allele1, [allele1, allele2], experimentArgument) >> allele3
            1 * updateBodyAlgorithm.updatetBody(allele2, [allele1, allele2], experimentArgument) >> allele4
            1 * terminationAlgorithm.areBodiesInCorrectDistanceToEachOther([allele3, allele4], experimentArgument) >> false

            result[0].getFitness() == 0.0d
            result[0].getAlleles() == [allele1, allele2]
    }

    void "test three iterations"() {
        given:
            NBodyFitnessTerminationAlgorithm terminationAlgorithm = Mock(NBodyFitnessTerminationAlgorithm)
            NBodyFitnessUpdateBodyAlgorithm updateBodyAlgorithm = Mock(NBodyFitnessUpdateBodyAlgorithm)
            NBodyFitnessAlgorithmImpl classUnderTest = new NBodyFitnessAlgorithmImpl(updateBodyAlgorithm, terminationAlgorithm)
            NBodyAllele allele1 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele2 = new NBodyAllele(0, 0, 1, 2.5, 2.5)
            Collection<NBodyChromosome> population = [
                    new NBodyChromosome([allele1, allele2])
            ]
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withFitnessMaxIterations(10)
                            .build()
            NBodyAllele allele3 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele4 = new NBodyAllele(0, 0, 1, 2.5, 2.5)
            NBodyAllele allele5 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele6 = new NBodyAllele(0, 0, 1, 2.5, 2.5)
            NBodyAllele allele7 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele8 = new NBodyAllele(0, 0, 1, 2.5, 2.5)

        when:
            Collection<NBodyChromosome> result = classUnderTest.calculate(population, experimentArgument)

        then:
            // first iteration
            1 * updateBodyAlgorithm.updatetBody(allele1, [allele1, allele2], experimentArgument) >> allele3
            1 * updateBodyAlgorithm.updatetBody(allele2, [allele1, allele2], experimentArgument) >> allele4
            1 * terminationAlgorithm.areBodiesInCorrectDistanceToEachOther([allele3, allele4], experimentArgument) >> true

        then:
            // second iteration
            1 * updateBodyAlgorithm.updatetBody(allele3, [allele3, allele4], experimentArgument) >> allele5
            1 * updateBodyAlgorithm.updatetBody(allele4, [allele3, allele4], experimentArgument) >> allele6
            1 * terminationAlgorithm.areBodiesInCorrectDistanceToEachOther([allele5, allele6], experimentArgument) >> true

        then:
            // third iteration
            1 * updateBodyAlgorithm.updatetBody(allele5, [allele5, allele6], experimentArgument) >> allele7
            1 * updateBodyAlgorithm.updatetBody(allele6, [allele5, allele6], experimentArgument) >> allele8
            1 * terminationAlgorithm.areBodiesInCorrectDistanceToEachOther([allele7, allele8], experimentArgument) >> false

        then:
            result[0].getFitness() == 2.0d
            result[0].getAlleles() == [allele1, allele2]
    }

    void "test the getIterationSteps method"() {
        given:
            NBodyFitnessTerminationAlgorithm terminationAlgorithm = Mock(NBodyFitnessTerminationAlgorithm)
            NBodyFitnessUpdateBodyAlgorithm updateBodyAlgorithm = Mock(NBodyFitnessUpdateBodyAlgorithm)
            NBodyFitnessAlgorithmImpl classUnderTest = new NBodyFitnessAlgorithmImpl(updateBodyAlgorithm, terminationAlgorithm)
            NBodyAllele allele1 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele2 = new NBodyAllele(0, 0, 1, 2.5, 2.5)
            NBodyChromosome chromosome = new NBodyChromosome([allele1, allele2])
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withFitnessMaxIterations(10)
                            .build()
            NBodyAllele allele3 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele4 = new NBodyAllele(0, 0, 1, 2.5, 2.5)
            NBodyAllele allele5 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele6 = new NBodyAllele(0, 0, 1, 2.5, 2.5)
            NBodyAllele allele7 = new NBodyAllele(100, 100, 1, 2.5, 2.5)
            NBodyAllele allele8 = new NBodyAllele(0, 0, 1, 2.5, 2.5)

        when:
            List<List<NBodyAllele>> result = classUnderTest.getIterationSteps(chromosome, experimentArgument)

        then:
            // first iteration
            1 * updateBodyAlgorithm.updatetBody(allele1, [allele1, allele2], experimentArgument) >> allele3
            1 * updateBodyAlgorithm.updatetBody(allele2, [allele1, allele2], experimentArgument) >> allele4
            1 * terminationAlgorithm.areBodiesInCorrectDistanceToEachOther([allele3, allele4], experimentArgument) >> true

        then:
            // second iteration
            1 * updateBodyAlgorithm.updatetBody(allele3, [allele3, allele4], experimentArgument) >> allele5
            1 * updateBodyAlgorithm.updatetBody(allele4, [allele3, allele4], experimentArgument) >> allele6
            1 * terminationAlgorithm.areBodiesInCorrectDistanceToEachOther([allele5, allele6], experimentArgument) >> true

        then:
            // third iteration
            1 * updateBodyAlgorithm.updatetBody(allele5, [allele5, allele6], experimentArgument) >> allele7
            1 * updateBodyAlgorithm.updatetBody(allele6, [allele5, allele6], experimentArgument) >> allele8
            1 * terminationAlgorithm.areBodiesInCorrectDistanceToEachOther([allele7, allele8], experimentArgument) >> false

        then:
            result == [
                    [allele1, allele2],
                    [allele3, allele4],
                    [allele5, allele6]
            ]
    }
}
