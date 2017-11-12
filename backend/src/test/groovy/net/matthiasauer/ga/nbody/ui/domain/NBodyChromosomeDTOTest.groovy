package net.matthiasauer.ga.nbody.ui.domain

import net.matthiasauer.ga.nbody.calculation.NBodyAllele
import net.matthiasauer.ga.nbody.calculation.NBodyChromosome
import spock.lang.Specification

class NBodyChromosomeDTOTest extends Specification {
    void "FromNBodyAllele"() {
        given:
            NBodyAllele allele1 = new NBodyAllele(1, 2, 3, 4, 5)
            NBodyAllele allele2 = new NBodyAllele(2, 3, 4, 5, 6)
            NBodyChromosome chromosome = new NBodyChromosome([allele1, allele2], 7.0)

        when:
            NBodyChromosomeDTO classUnderTest = NBodyChromosomeDTO.from(chromosome)

        then:
            classUnderTest.getFitness() == 7.0d
            classUnderTest.getAlleles()[0].posX == 1
            classUnderTest.getAlleles()[0].posY == 2
            classUnderTest.getAlleles()[0].mass == 3
            classUnderTest.getAlleles()[0].velocityX == 4
            classUnderTest.getAlleles()[0].velocityY == 5
            classUnderTest.getAlleles()[1].posX == 2
            classUnderTest.getAlleles()[1].posY == 3
            classUnderTest.getAlleles()[1].mass == 4
            classUnderTest.getAlleles()[1].velocityX == 5
            classUnderTest.getAlleles()[1].velocityY == 6
    }

    void "FromNBodyAllele with null"() {
        expect:
            NBodyChromosomeDTO.from(null) == null
    }
}
