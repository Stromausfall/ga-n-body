package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.GenerateAlgorithm;
import net.matthiasauer.ga.calculation.RandomProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class NBodyGenerateAlgorithm implements GenerateAlgorithm<NBodyChromosome, NBodyExperimentArgument> {
    private final RandomProvider randomProvider;

    @Autowired
    public NBodyGenerateAlgorithm(RandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

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
                this.randomProvider.nextDouble(experimentArgument.getMinPosXY(), experimentArgument.getMaxPosXY()),
                this.randomProvider.nextDouble(experimentArgument.getMinPosXY(), experimentArgument.getMaxPosXY()),
                this.randomProvider.nextDouble(experimentArgument.getMinMass(), experimentArgument.getMaxMass()),
                this.randomProvider.nextDouble(experimentArgument.getMinVelocityXY(), experimentArgument.getMaxVelocityYY()),
                this.randomProvider.nextDouble(experimentArgument.getMinVelocityXY(), experimentArgument.getMaxVelocityYY()));
    }
}
