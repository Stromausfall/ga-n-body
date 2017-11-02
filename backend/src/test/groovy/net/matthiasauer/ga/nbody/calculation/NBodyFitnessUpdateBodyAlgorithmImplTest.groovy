package net.matthiasauer.ga.nbody.calculation

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import spock.lang.Specification

class NBodyFitnessUpdateBodyAlgorithmImplTest extends Specification {
    void "test that the bodies position is correctly changed"() {
        given:
            NBodyExperimentArgument experimentArgument =
                    new NBodyExperimentArgument.Builder()
                            .withFitnessGravityConstant(0.26)
                            .build()
            NBodyFitnessUpdateBodyAlgorithm updateBodyAlgorithm = new NBodyFitnessUpdateBodyAlgorithmImpl()
            NBodyAllele body1 = new NBodyAllele(2, 2, 1, 2, 2)
            NBodyAllele body2 = new NBodyAllele(5, 5, 0.5, -2, -2)

            Vector2D v = new Vector2D(body2.posX - body1.posX, body2.posY - body1.posY)
            double r = Math.sqrt(
                    Math.pow(v.getX(), 2) +
                            Math.pow(v.getY(), 2))
            Vector2D vUnit = v.normalize()
            double f = ((body1.mass * body2.mass) / r) * experimentArgument.fitnessGravityConstant
            Vector2D resultPart = vUnit.scalarMultiply(f)
        when:
            NBodyAllele result = updateBodyAlgorithm.updatetBody(body1, [body1, body2], experimentArgument)

        then:
            // mass should not be changed
            result.mass == body1.mass

            // we should now have a new velocity
            result.velocityX == body1.velocityX + resultPart.x
            result.velocityY == body1.velocityY + resultPart.y

            // the position also changed
            result.posX == body1.posX + result.velocityX
            result.posY == body1.posY + result.velocityY
    }
}
