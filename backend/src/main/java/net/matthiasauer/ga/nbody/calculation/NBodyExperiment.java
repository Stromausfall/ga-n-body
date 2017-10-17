package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NBodyExperiment extends AbstractExperiment<NBodyChromosome, NBodyExperimentArgument> {
    @Autowired
    public NBodyExperiment(
            GenerateAlgorithm<NBodyChromosome, NBodyExperimentArgument> generateAlgorithm,
            SelectionAlgorithm<NBodyChromosome, NBodyExperimentArgument> selectionAlgorithm,
            CrossoverAlgorithm<NBodyChromosome, NBodyExperimentArgument> crossoverAlgorithm,
            MutationAlgorithm<NBodyChromosome> mutationAlgorithm,
            ReplaceAlgorithm<NBodyChromosome> replaceAlgorithm,
            TerminationAlgorithm<NBodyChromosome> terminationAlgorithm) {
        super(
                generateAlgorithm,
                selectionAlgorithm,
                crossoverAlgorithm,
                mutationAlgorithm,
                replaceAlgorithm,
                terminationAlgorithm);
    }
}
