package net.matthiasauer.ga.nbody.repositories

import net.matthiasauer.ga.nbody.calculation.NBodyChromosome
import spock.lang.Specification

class NBodyChromosomeFitnessRepositoryImplTest extends Specification {
    void "test that after adding multiple NBodyChromosomes - only the one with the highest fitness is stored"() {
        given:
            NBodyChromosome chromosomeFitness10 = new NBodyChromosome([], 10)
            NBodyChromosome chromosomeFitness20 = new NBodyChromosome([], 20)
            NBodyChromosome chromosomeFitness40 = new NBodyChromosome([], 40)
            NBodyChromosome chromosomeFitness50 = new NBodyChromosome([], 50)
            NBodyChromosomeFitnessRepository classUnderTest =
                    new NBodyChromosomeFitnessRepositoryImpl()

        when:
            NBodyChromosome result = classUnderTest.getFittest()

        then:
            result == null

        when:
            classUnderTest.add(chromosomeFitness20)

        then:
            classUnderTest.getFittest() == chromosomeFitness20

        when:
            classUnderTest.add(chromosomeFitness10)

        then:
            classUnderTest.getFittest() == chromosomeFitness20

        when:
            classUnderTest.add(chromosomeFitness50)

        then:
            classUnderTest.getFittest() == chromosomeFitness50

        when:
            classUnderTest.add(chromosomeFitness40)

        then:
            classUnderTest.getFittest() == chromosomeFitness50
    }
}
