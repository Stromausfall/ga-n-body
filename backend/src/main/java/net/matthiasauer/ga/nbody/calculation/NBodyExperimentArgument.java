package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.ExperimentArgument;

public class NBodyExperimentArgument implements ExperimentArgument {
    private final double crossOverReturnsParentLikelihood;

    public NBodyExperimentArgument(double crossOverReturnsParentLikelihood) {
        this.crossOverReturnsParentLikelihood = crossOverReturnsParentLikelihood;
    }

    public double getCrossOverReturnsParentLikelihood() {
        return crossOverReturnsParentLikelihood;
    }
}
