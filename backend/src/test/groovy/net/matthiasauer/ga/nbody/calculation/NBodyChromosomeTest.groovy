package net.matthiasauer.ga.nbody.calculation

import spock.lang.Specification

class NBodyChromosomeTest extends Specification {
    def "test that the alleles collection passed in the constructor is copied in the constructor"() {
        given:
            Collection<NBodyAllele> alleles = [new NBodyAllele(0.0, 0.0, 0.0, 0.0, 0.0)]

            NBodyChromosome chromosome = new NBodyChromosome(alleles)

        when:
            alleles.clear()

        then:
            chromosome.getAlleles() != alleles
            chromosome.getAlleles().size() == 1
    }

    def "test that the getAlleles method returns a read only-view on the alleles collection"() {
        given:
            Collection<NBodyAllele> alleles = [new NBodyAllele(0.0, 0.0, 0.0, 0.0, 0.0)]

            NBodyChromosome chromosome = new NBodyChromosome(alleles)

        when:
            chromosome.getAlleles().clear()

        then:
            thrown UnsupportedOperationException
    }
}
