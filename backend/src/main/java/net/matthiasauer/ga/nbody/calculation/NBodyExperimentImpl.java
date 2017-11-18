package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NBodyExperimentImpl extends AbstractExperiment<NBodyChromosome, NBodyExperimentArgument, NBodyAllele> implements NBodyExperiment {
    @Autowired
    public NBodyExperimentImpl(
            GenerateAlgorithm<NBodyChromosome, NBodyExperimentArgument> generateAlgorithm,
            FitnessAlgorithm<NBodyChromosome, NBodyExperimentArgument, NBodyAllele> fitnessAlgorithm,
            SelectionAlgorithm<NBodyChromosome, NBodyExperimentArgument> selectionAlgorithm,
            CrossoverAlgorithm<NBodyChromosome, NBodyExperimentArgument, NBodyAllele> crossoverAlgorithm,
            MutationAlgorithm<NBodyChromosome, NBodyExperimentArgument> mutationAlgorithm,
            ReplaceAlgorithm<NBodyChromosome, NBodyExperimentArgument> replaceAlgorithm,
            TerminationAlgorithm<NBodyChromosome, NBodyExperimentArgument> terminationAlgorithm) {
        super(
                generateAlgorithm,
                fitnessAlgorithm,
                selectionAlgorithm,
                crossoverAlgorithm,
                mutationAlgorithm,
                replaceAlgorithm,
                terminationAlgorithm);
    }
}
