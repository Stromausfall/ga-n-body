package net.matthiasauer.ga.nbody.ui.domain;

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;

public class NBodyExperimentArgumentDTO {
    public NBodyExperimentArgument toNBodyExperimentArgument() {
        return new NBodyExperimentArgument.Builder().build();
    }
}
