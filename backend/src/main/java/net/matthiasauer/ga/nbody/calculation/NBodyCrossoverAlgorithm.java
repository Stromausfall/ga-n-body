package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.CrossoverAlgorithm;
import net.matthiasauer.ga.calculation.ParentChromosomes;
import net.matthiasauer.ga.calculation.RandomProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class NBodyCrossoverAlgorithm implements CrossoverAlgorithm<NBodyChromosome, NBodyExperimentArgument, NBodyAllele> {
    private final RandomProvider random;

    @Autowired
    public NBodyCrossoverAlgorithm(RandomProvider random) {
        this.random = random;
    }

    private NBodyChromosome getOffspring(
            NBodyChromosome parentA,
            NBodyChromosome parentB,
            NBodyExperimentArgument experimentArgument) {
        // there is a chance that no crossover happens
        if (this.random.nextDouble() < experimentArgument.getCrossOverReturnsParentLikelihood()) {
            if (this.random.nextBoolean()) {
                return parentA;
            } else {
                return parentB;
            }
        }

        List<NBodyAllele> offspringAlleles = new ArrayList<>();

        // make sure the prents have the same number of alleles
        assert parentA.getAlleles().size() == parentB.getAlleles().size();

        for (int i = 0; i < parentA.getAlleles().size(); i++) {
            if (this.random.nextBoolean()) {
                offspringAlleles.add(parentA.getAlleles().get(i));
            } else {
                offspringAlleles.add(parentB.getAlleles().get(i));
            }
        }

        return new NBodyChromosome(offspringAlleles);
    }

    @Override
    public Collection<NBodyChromosome> createOffspring(
            Collection<ParentChromosomes<NBodyChromosome>> parents,
            NBodyExperimentArgument experimentArgument) {
        Collection<NBodyChromosome> offspring = new ArrayList<>();

        for (ParentChromosomes<NBodyChromosome> parentChromosomes : parents) {
            NBodyChromosome child =
                    getOffspring(
                            parentChromosomes.getParentA(),
                            parentChromosomes.getParentB(),
                            experimentArgument);

            offspring.add(child);
        }

        return offspring;
    }
}
