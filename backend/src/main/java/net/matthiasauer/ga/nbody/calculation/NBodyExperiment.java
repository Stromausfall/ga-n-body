package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NBodyExperiment extends AbstractExperiment<NBodyChromosome, NBodyExperimentArgument> {
    @Autowired
    public NBodyExperiment(
            GenerateAlgorithm<NBodyChromosome, NBodyExperimentArgument> generateAlgorithm,
            FitnessAlgorithm<NBodyChromosome, NBodyExperimentArgument> fitnessAlgorithm,
            SelectionAlgorithm<NBodyChromosome, NBodyExperimentArgument> selectionAlgorithm,
            CrossoverAlgorithm<NBodyChromosome, NBodyExperimentArgument> crossoverAlgorithm,
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
