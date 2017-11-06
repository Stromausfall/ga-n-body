package net.matthiasauer.ga.nbody.ui.domain

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument
import spock.lang.Specification

class NBodyExperimentArgumentDTOTest extends Specification {
    void "ToNBodyExperimentArgument"() {
        given:
            NBodyExperimentArgumentDTO classUnderTest = new NBodyExperimentArgumentDTO()

            classUnderTest.populationSize = 1
            classUnderTest.newPopulationSize = 2
            classUnderTest.allelesPerChromosome = 3
            classUnderTest.crossOverReturnsParentLikelihood = 4
            classUnderTest.minPosXY = 5 
            classUnderTest.maxPosXY = 6
            classUnderTest.minMass = 7
            classUnderTest.maxMass = 8
            classUnderTest.minVelocityXY=9
            classUnderTest.maxVelocityYY=10
            classUnderTest.mutateNucleotideChance=11
            classUnderTest.fitnessMaxIterations=12
            classUnderTest.fitnessMaxDistanceBetweenBodies=13
            classUnderTest.fitnessMinDistanceBetweenBodies=14
            classUnderTest.fitnessGravityConstant=15
            classUnderTest.terminationMaxIterations=16
            classUnderTest.terminationTargetFitness=17

        when:
            NBodyExperimentArgument result = classUnderTest.toNBodyExperimentArgument()

        then:
            result.populationSize == 1
            result.newPopulationSize == 2
            result.allelesPerChromosome == 3
            result.crossOverReturnsParentLikelihood == 4
            result.minPosXY == 5
            result.maxPosXY == 6
            result.minMass == 7
            result.maxMass == 8
            result.minVelocityXY==9
            result.maxVelocityYY==10
            result.mutateNucleotideChance==11
            result.fitnessMaxIterations==12
            result.fitnessMaxDistanceBetweenBodies==13
            result.fitnessMinDistanceBetweenBodies==14
            result.fitnessGravityConstant==15
            result.terminationMaxIterations==16
            result.terminationTargetFitness==17
    }
}
