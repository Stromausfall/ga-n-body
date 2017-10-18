package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.GenerateAlgorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NBodyGenerateAlgorithm implements GenerateAlgorithm<NBodyChromosome, NBodyExperimentArgument> {
    @Override
    public Collection<NBodyChromosome> generate(NBodyExperimentArgument experimentArgument) {
        Collection<NBodyChromosome> chromosomes = new ArrayList<>();

        // for each chromosome to create
        for (int i = 0; i < experimentArgument.getPopulationSize(); i++) {
            List<NBodyAllele> alleles = new ArrayList<>();

            // for each allele of a chromosome to create
            for (int j = 0; j < experimentArgument.getAllelesPerChromosome(); j++) {
                alleles.add(createNBodyAllele(experimentArgument));
            }

            NBodyChromosome chromosome = new NBodyChromosome(alleles);

            chromosomes.add(chromosome);
        }

        return chromosomes;
    }

    private NBodyAllele createNBodyAllele(NBodyExperimentArgument experimentArgument) {
        return new NBodyAllele(
                BigDecimal.ONE,
                BigDecimal.ONE,
                BigDecimal.ONE,
                BigDecimal.ONE,
                BigDecimal.ONE);
    }
}
