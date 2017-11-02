package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.FitnessAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NBodyFitnessAlgorithm implements FitnessAlgorithm<NBodyChromosome, NBodyExperimentArgument> {

    private final NBodyFitnessUpdateBodyAlgorithm updateBodyPosition;
    private final NBodyFitnessTerminationAlgorithm terminationAlgorithm;

    @Autowired
    public NBodyFitnessAlgorithm(NBodyFitnessUpdateBodyAlgorithm updateBodyPosition, NBodyFitnessTerminationAlgorithm terminationAlgorithm) {
        this.updateBodyPosition = updateBodyPosition;
        this.terminationAlgorithm = terminationAlgorithm;

    }

    private NBodyChromosome evaluate(NBodyChromosome chromosome, NBodyExperimentArgument experimentArgument) {
        double fitness = 0.0d;
        List<NBodyAllele> bodies = chromosome.getAlleles();

        for(int i = 0; i < experimentArgument.getFitnessMaxIterations(); i++) {
            // get a final reference on bodies (needed for the lambda)
            final List<NBodyAllele> allBodies = bodies;

            // move the bodies
            bodies = bodies.stream().map(body -> this.updateBodyPosition.updatetBody(body, allBodies, experimentArgument)).collect(Collectors.toList());

            if (this.terminationAlgorithm.isABodyTooFarAway(bodies, experimentArgument)) {
                // stop -  one of the bodies is too far away
                break;
            }

            // if this is reached - all bodies are still inside ! (therefore increase the fitness that this chromsome achieved
            fitness += 1;
        }

        return new NBodyChromosome(bodies, fitness);
    }

    @Override
    public Collection<NBodyChromosome> calculate(Collection<NBodyChromosome> population, NBodyExperimentArgument experimentArgument) {
        return population.stream().map(chromosome -> evaluate(chromosome, experimentArgument)).collect(Collectors.toList());
    }
}
