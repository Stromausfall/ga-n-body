package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.ParentChromosomes;
import net.matthiasauer.ga.calculation.RandomProvider;
import net.matthiasauer.ga.calculation.SelectionAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NBodySelectionAlgorithm implements SelectionAlgorithm<NBodyChromosome, NBodyExperimentArgument> {

    private final RandomProvider randomProvider;

    @Autowired
    public NBodySelectionAlgorithm(RandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

    private <T> T getRandomAndRemoveFromCollection(List<T> list) {
        int randomIndex = this.randomProvider.nextInt(0, list.size());

        // remove a random element from the list
        T element = list.remove(randomIndex);

        return element;
    }

    @Override
    public Collection<ParentChromosomes<NBodyChromosome>> selectParents(Collection<NBodyChromosome> chromosomes, NBodyExperimentArgument experimentArgument) {
        if (chromosomes.size() < 2) {
            throw new IllegalArgumentException("There must be at least two chromosomes from which parents are selected!");
        }

        Collection<ParentChromosomes<NBodyChromosome>> parents = new ArrayList<>();

        for (int i = 0; i < experimentArgument.getNewPopulationSize(); i++) {
            List<NBodyChromosome> possibleParents = new ArrayList<>(chromosomes);

            NBodyChromosome parentA = getRandomAndRemoveFromCollection(possibleParents);
            NBodyChromosome parentB = getRandomAndRemoveFromCollection(possibleParents);

            parents.add(new ParentChromosomes<>(parentA, parentB));
        }

        return parents;
    }
}
