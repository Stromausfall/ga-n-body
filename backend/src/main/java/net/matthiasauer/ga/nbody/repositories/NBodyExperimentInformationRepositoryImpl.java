package net.matthiasauer.ga.nbody.repositories;

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class NBodyExperimentInformationRepositoryImpl implements NBodyExperimentInformationRepository {
    private final AtomicReference<NBodyExperimentInformation> value = new AtomicReference<>();

    @Override
    public void store(NBodyExperimentArgument experimentArgument, int currentIteration) {
        NBodyExperimentInformation experimentInformation = new NBodyExperimentInformation();
        experimentInformation.setCurrentIteration(currentIteration);
        experimentInformation.setNBodyExperimentArgument(experimentArgument);

        this.value.set(experimentInformation);
    }

    @Override
    public NBodyExperimentInformation getLatest() {
        return this.value.get();
    }

    @Override
    public void clear() {
        this.value.set(null);
    }
}
