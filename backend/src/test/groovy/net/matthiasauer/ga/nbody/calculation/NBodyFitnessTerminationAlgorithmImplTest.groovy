package net.matthiasauer.ga.nbody.calculation

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import spock.lang.Specification
import spock.lang.Unroll

class NBodyFitnessTerminationAlgorithmImplTest extends Specification {
    private final static double MIN_DISTANCE = 10
    private final static double MAX_DISTANCE = 100

    @Unroll
    void "test that too the algorithm detects if two bodies are too close to each other (2 bodies)"() {
        given:
            Vector2D pos1 = new Vector2D(50, -50)
            Vector2D arrowFromPos1ToPos2 = new Vector2D(1, 1).normalize()
            Vector2D pos2 = pos1.add(arrowFromPos1ToPos2.scalarMultiply(distFrom1To2))

            NBodyAllele body1 = new NBodyAllele(pos1.x, pos1.y, 0, 0, 0)
            NBodyAllele body2 = new NBodyAllele(pos2.x, pos2.y, 0, 0, 0)

            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withFitnessMinDistanceBetweenBodies(MIN_DISTANCE)
                            .withFitnessMaxDistanceBetweenBodies(MAX_DISTANCE)
                            .build()

            NBodyFitnessTerminationAlgorithm classUnderTest =
                    new NBodyFitnessTerminationAlgorithmImpl()

        when:
            boolean result = classUnderTest.areBodiesInCorrectDistanceToEachOther([body1, body2], experimentArgument)

        then:
            result == expectedResult

        where:
            distFrom1To2     || expectedResult
            40               || true
            MIN_DISTANCE     || true
            MAX_DISTANCE     || true
            MIN_DISTANCE - 1 || false
            MAX_DISTANCE + 1 || false
    }

    @Unroll
    void "test that too the algorithm detects if two bodies are too close to each other (3 bodies)"() {
        given:
            Vector2D pos1 = new Vector2D(50, -50)
            Vector2D arrowFromPos1ToPos2 = new Vector2D(1, 1).normalize()
            Vector2D arrowFromPos1ToPos3 = new Vector2D(-1, -1).normalize()
            Vector2D pos2 = pos1.add(arrowFromPos1ToPos2.scalarMultiply(distFrom1To2))
            Vector2D pos3 = pos1.add(arrowFromPos1ToPos3.scalarMultiply(distFrom1To3))

            NBodyAllele body1 = new NBodyAllele(pos1.x, pos1.y, 0, 0, 0)
            NBodyAllele body2 = new NBodyAllele(pos2.x, pos2.y, 0, 0, 0)
            NBodyAllele body3 = new NBodyAllele(pos3.x, pos3.y, 0, 0, 0)

            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withFitnessMinDistanceBetweenBodies(MIN_DISTANCE)
                            .withFitnessMaxDistanceBetweenBodies(MAX_DISTANCE)
                            .build()

            NBodyFitnessTerminationAlgorithm classUnderTest =
                    new NBodyFitnessTerminationAlgorithmImpl()

        when:
            boolean result = classUnderTest.areBodiesInCorrectDistanceToEachOther([body1, body2, body3], experimentArgument)

        then:
            result == expectedResult

        where:
            distFrom1To2 | distFrom1To3 || expectedResult
            // both are correct
            50           | 50           || true
            50           | 25           || true
            50           | 10           || true
            // one is incorrect
            50           | 9            || false
            50           | 51           || false
            // both are incorrect
            9            | 9            || false
            101          | 101          || false
    }
}
