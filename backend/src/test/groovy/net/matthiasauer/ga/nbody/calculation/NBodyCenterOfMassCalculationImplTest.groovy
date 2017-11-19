package net.matthiasauer.ga.nbody.calculation

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import spock.lang.Specification

class NBodyCenterOfMassCalculationImplTest extends Specification {
    void "test with 3 bodies"() {
        given:
            List<NBodyAllele> bodies = [
                    new NBodyAllele(100, 100, 15, 0, 0),
                    new NBodyAllele(100, -100, 100, 0, 0),
                    new NBodyAllele(-100, 100, 40, 0, 0),
            ]
            NBodyCenterOfMassCalculation classUnderTest = new NBodyCenterOfMassCalculationImpl()

        when:
            Vector2D result = classUnderTest.calculate(bodies)

        then:
            result.getX() == 48.387096774193544d
            result.getY() == -29.032258064516128
    }
}
