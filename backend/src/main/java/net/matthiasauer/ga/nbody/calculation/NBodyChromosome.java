package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.Chromosome;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NBodyChromosome implements Chromosome<NBodyExperimentArgument, NBodyAllele> {
    private final List<NBodyAllele> alleles;
    private final double fitness;

    public NBodyChromosome(Collection<NBodyAllele> alleles) {
        this.alleles = Collections.unmodifiableList(new LinkedList<>(alleles));
        this.fitness = Double.NaN;
    }

    public NBodyChromosome(List<NBodyAllele> alleles, double fitness) {
        this.alleles = alleles;
        this.fitness = fitness;
    }

    public List<NBodyAllele> getAlleles() {
        return alleles;
    }

    @Override
    public double getFitness() {
        return fitness;
    }
}
