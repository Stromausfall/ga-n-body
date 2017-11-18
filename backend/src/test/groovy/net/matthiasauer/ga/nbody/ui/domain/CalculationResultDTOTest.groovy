package net.matthiasauer.ga.nbody.ui.domain

import net.matthiasauer.ga.nbody.calculation.NBodyAllele
import spock.lang.Specification

class CalculationResultDTOTest extends Specification {
    void "FromNBodyAllele"() {
        given:
            List<List<NBodyAllele>> iterations = [
                    [
                            new NBodyAllele(1, 2, 3, 4, 5)
                    ],
                    [
                           new NBodyAllele(2, 3, 4, 5, 6),
                           new NBodyAllele(7, 8, 9, 10, 11)
                    ]
            ]
            double fitness = 7.0d

        when:
            CalculationResultDTO classUnderTest = CalculationResultDTO.from(fitness, iterations)

        then:
            classUnderTest.getFitness() == fitness
            classUnderTest.getIterations()[0].bodies[0].posX == iterations[0][0].posX
            classUnderTest.getIterations()[0].bodies[0].posY == iterations[0][0].posY
            classUnderTest.getIterations()[0].bodies[0].mass == iterations[0][0].mass
            classUnderTest.getIterations()[0].bodies[0].velocityX == iterations[0][0].velocityX
            classUnderTest.getIterations()[0].bodies[0].velocityY == iterations[0][0].velocityY
            classUnderTest.getIterations()[1].bodies[0].posX == iterations[1][0].posX
            classUnderTest.getIterations()[1].bodies[0].posY == iterations[1][0].posY
            classUnderTest.getIterations()[1].bodies[0].mass == iterations[1][0].mass
            classUnderTest.getIterations()[1].bodies[0].velocityX == iterations[1][0].velocityX
            classUnderTest.getIterations()[1].bodies[0].velocityY == iterations[1][0].velocityY
            classUnderTest.getIterations()[1].bodies[1].posX == iterations[1][1].posX
            classUnderTest.getIterations()[1].bodies[1].posY == iterations[1][1].posY
            classUnderTest.getIterations()[1].bodies[1].mass == iterations[1][1].mass
            classUnderTest.getIterations()[1].bodies[1].velocityX == iterations[1][1].velocityX
            classUnderTest.getIterations()[1].bodies[1].velocityY == iterations[1][1].velocityY
    }
}
