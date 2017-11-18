package net.matthiasauer.ga.calculation;

import net.matthiasauer.ga.nbody.calculation.NBodyAllele;

import java.util.Collection;
import java.util.List;

public interface FitnessAlgorithm<T extends Chromosome, S extends ExperimentArgument> {
    Collection<T> calculate(Collection<T> population, S experimentArgument);


    /*
        - this is called by the service to evaluate the "fittest" and get the steps
                - the service then returns another data structure using ONLY the positions of the bodies to the controller
        - the frontend then only needs to display the steps
        - frontend needs to center in the 'schwerpunkt of all bodies'
    */
    List<List<NBodyAllele>> getIterationSteps(T chromosome, S experimentArgument);
}