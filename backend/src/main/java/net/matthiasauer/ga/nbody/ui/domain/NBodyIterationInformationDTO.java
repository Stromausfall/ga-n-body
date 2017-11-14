package net.matthiasauer.ga.nbody.ui.domain;

public class NBodyIterationInformationDTO {
    private int iteration;
    private NBodyChromosomeDTO fittest;
    private NBodyExperimentArgumentDTO experimentArgument;

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public NBodyChromosomeDTO getFittest() {
        return fittest;
    }

    public void setFittest(NBodyChromosomeDTO fittest) {
        this.fittest = fittest;
    }

    public NBodyExperimentArgumentDTO getExperimentArgument() {
        return experimentArgument;
    }

    public void setExperimentArgument(NBodyExperimentArgumentDTO experimentArgument) {
        this.experimentArgument = experimentArgument;
    }
}
