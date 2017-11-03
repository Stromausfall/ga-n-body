package net.matthiasauer.ga.nbody.calculation

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import spock.lang.Specification
import spock.lang.Unroll

class NBodyAlleleTest extends Specification {
    @Unroll
    void "test the getVelocity method"() {
        given:
            NBodyAllele classUnderTest =
                    new NBodyAllele(0, 0, 0, velocityX, velocityY)

        when:
            Vector2D result = classUnderTest.getVelocity()

        then:
            result.x == velocityX
            result.y == velocityY

        where:
            velocityX | velocityY
            200       | 250
            1.5d      | 0.234d
    }

    @Unroll
    void "test the getPosition method"() {
        given:
            NBodyAllele classUnderTest =
                    new NBodyAllele(posX, posY, 0, 0, 0)

        when:
            Vector2D result = classUnderTest.getPosition()

        then:
            result.x == posX
            result.y == posY

        where:
            posX | posY
            200  | 250
            1.5d | 0.234d
    }
}
