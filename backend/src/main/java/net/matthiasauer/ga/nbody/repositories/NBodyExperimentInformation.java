package net.matthiasauer.ga.nbody.repositories;

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;

public class NBodyExperimentInformation {
    private int currentIteration;
    private NBodyExperimentArgument NBodyExperimentArgument;

    public int getCurrentIteration() {
        return currentIteration;
    }

    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }

    public net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument getNBodyExperimentArgument() {
        return NBodyExperimentArgument;
    }

    public void setNBodyExperimentArgument(net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument NBodyExperimentArgument) {
        this.NBodyExperimentArgument = NBodyExperimentArgument;
    }
}
