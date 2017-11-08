package net.matthiasauer.ga.nbody.ui.domain

import net.matthiasauer.ga.nbody.calculation.NBodyAllele
import spock.lang.Specification

class NBodyAlleleDTOTest extends Specification {
    void "FromNBodyAllele"() {
        given:
            NBodyAllele allele = new NBodyAllele(1, 2, 3, 4, 5)

        when:
            NBodyAlleleDTO classUnderTest = NBodyAlleleDTO.from(allele)

        then:
            classUnderTest.posX == 1
            classUnderTest.posY == 2
            classUnderTest.mass == 3
            classUnderTest.velocityX == 4
            classUnderTest.velocityY == 5
    }
}
