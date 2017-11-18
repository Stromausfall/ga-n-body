package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.FitnessAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        List<List<NBodyAllele>> iterationSteps = this.getIterationSteps(chromosome, experimentArgument);

        // remove the intial (uncalculated) step from the fitness
        int fitness = iterationSteps.size() - 1;

        // return the chromosome with a now calculated fitness value
        return new NBodyChromosome(chromosome.getAlleles(), fitness);
    }

    @Override
    public Collection<NBodyChromosome> calculate(Collection<NBodyChromosome> population, NBodyExperimentArgument experimentArgument) {
        return population.parallelStream().map(chromosome -> evaluate(chromosome, experimentArgument)).collect(Collectors.toList());
    }

    @Override
    public List<List<NBodyAllele>> getIterationSteps(NBodyChromosome chromosome, NBodyExperimentArgument experimentArgument) {
        List<List<NBodyAllele>> iterationSteps = new ArrayList<>();
        List<NBodyAllele> bodies = chromosome.getAlleles();

        // add the initial step
        iterationSteps.add(bodies);

        for(int i = 0; i < experimentArgument.getFitnessMaxIterations(); i++) {
            // get a final reference on bodies (needed for the lambda)
            final List<NBodyAllele> calculatedBodies = bodies;

            // move the bodies
            bodies = bodies.stream().map(body -> this.updateBodyPosition.updatetBody(body, calculatedBodies, experimentArgument)).collect(Collectors.toList());

            if (!this.terminationAlgorithm.areBodiesInCorrectDistanceToEachOther(bodies, experimentArgument)) {
                // stop -  one of the bodies is too far away
                break;
            }

            // add the calculated step
            iterationSteps.add(bodies);
        }

        return iterationSteps;
    }
}
