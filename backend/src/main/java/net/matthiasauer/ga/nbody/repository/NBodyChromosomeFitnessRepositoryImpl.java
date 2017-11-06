package net.matthiasauer.ga.nbody.repository;

import net.matthiasauer.ga.nbody.calculation.NBodyChromosome;
import org.springframework.stereotype.Component;

@Component
public class NBodyChromosomeFitnessRepositoryImpl implements NBodyChromosomeFitnessRepository {
    private NBodyChromosome fittest = null;

    @Override
    public void add(NBodyChromosome chromosome) {
        // if there is no fittest yet
        if (this.getFittest() == null) {
            this.fittest = chromosome;
        } else {
            // otherwise only replace if the new one is fitter
            if (chromosome.getFitness() > this.getFittest().getFitness()) {
                this.fittest = chromosome;
            }
        }
    }

    @Override
    public NBodyChromosome getFittest() {
        return this.fittest;
    }
}
