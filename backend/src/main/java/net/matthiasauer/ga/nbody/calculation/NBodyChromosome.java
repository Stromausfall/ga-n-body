package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.Chromosome;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NBodyChromosome implements Chromosome<NBodyExperimentArgument> {
    private final List<NBodyAllele> alleles;

    public NBodyChromosome(List<NBodyAllele> alleles) {
        this.alleles = Collections.unmodifiableList(new LinkedList<>(alleles));
    }

    //TODO: implement the fitness function
    @Override
    public BigDecimal calculateFitness(NBodyExperimentArgument experimentArgument) {
        return null;
    }

    public List<NBodyAllele> getAlleles() {
        return alleles;
    }
}
