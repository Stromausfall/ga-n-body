package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.MutationAlgorithm;
import net.matthiasauer.ga.calculation.RandomProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class NBodyMutationAlgorithm implements MutationAlgorithm<NBodyChromosome, NBodyExperimentArgument> {

    private final RandomProvider randomProvider;

    @Autowired
    public NBodyMutationAlgorithm(RandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

    private double mutateNucleoid(double original, double min, double max, double chance) {
        // check whether the mutate
        if (this.randomProvider.nextDouble() <= chance) {
            // mutate
            return this.randomProvider.nextDouble(min, max);
        } else {
            // no mutation
            return original;
        }
    }

    private NBodyAllele mutateAllele(NBodyAllele unmutated, NBodyExperimentArgument experimentArgument) {
        // create a mutated allele
        return new NBodyAllele(
                mutateNucleoid(unmutated.getPosX(), experimentArgument.getMinPosXY(), experimentArgument.getMaxPosXY(), experimentArgument.getMutateNucleotideChance()),
                mutateNucleoid(unmutated.getPosY(), experimentArgument.getMinPosXY(), experimentArgument.getMaxPosXY(), experimentArgument.getMutateNucleotideChance()),
                mutateNucleoid(unmutated.getMass(), experimentArgument.getMinMass(), experimentArgument.getMaxMass(), experimentArgument.getMutateNucleotideChance()),
                mutateNucleoid(unmutated.getVelocityX(), experimentArgument.getMinVelocityXY(), experimentArgument.getMaxVelocityYY(), experimentArgument.getMutateNucleotideChance()),
                mutateNucleoid(unmutated.getVelocityY(), experimentArgument.getMinVelocityXY(), experimentArgument.getMaxVelocityYY(), experimentArgument.getMutateNucleotideChance())
        );
    }

    private NBodyChromosome mutateChromosome(NBodyChromosome unmutatedChromosome, NBodyExperimentArgument experimentArgument) {
        // mutate the chromsome by mutating all alleles
        Collection<NBodyAllele> mutatedAlleles =
                unmutatedChromosome.getAlleles().stream().map(
                        unmutatedAllele -> mutateAllele(unmutatedAllele, experimentArgument)
                ).collect(Collectors.toList());

        return new NBodyChromosome(mutatedAlleles);

    }

    @Override
    public Collection<NBodyChromosome> mutate(Collection<NBodyChromosome> chromosomes, NBodyExperimentArgument experimentArgument) {
        // mutate all chromsomes
        return chromosomes.stream().map(
                unmutatedChromosome -> mutateChromosome(unmutatedChromosome, experimentArgument)
        ).collect(Collectors.toList());
    }
}
