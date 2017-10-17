package net.matthiasauer.ga.calculation;

public interface Experiment<S extends ExperimentArgument> {
    void execute(final S arguments);
}
