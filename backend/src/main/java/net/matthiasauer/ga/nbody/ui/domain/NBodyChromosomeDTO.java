package net.matthiasauer.ga.nbody.ui.domain;

import net.matthiasauer.ga.nbody.calculation.NBodyAllele;
import net.matthiasauer.ga.nbody.calculation.NBodyChromosome;

import java.util.stream.Collectors;

public class NBodyChromosomeDTO {
    private NBodyAlleleDTO[] alleles;
    private double fitness;

    public NBodyAlleleDTO[] getAlleles() {
        return alleles;
    }

    public void setAlleles(NBodyAlleleDTO[] alleles) {
        this.alleles = alleles;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public static NBodyChromosomeDTO from(NBodyChromosome nBodyChromosome) {
        if (nBodyChromosome == null) {
            return null;
        }

        NBodyChromosomeDTO dto = new NBodyChromosomeDTO();
        NBodyAlleleDTO[] alleles =
                nBodyChromosome.getAlleles().stream()
                        .map(NBodyAlleleDTO::from)
                        .collect(Collectors.toList())
                        .toArray(new NBodyAlleleDTO[0]);

        dto.setFitness(nBodyChromosome.getFitness());
        dto.setAlleles(alleles);

        return dto;
    }
}
