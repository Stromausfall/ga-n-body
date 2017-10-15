package net.matthiasauer.ga.calculation.domain;

public interface Experiment<S extends ExperimentArgument> {
    void execute(final S arguments);
}
