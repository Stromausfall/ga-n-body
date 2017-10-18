package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.ExperimentArgument;

public class NBodyExperimentArgument implements ExperimentArgument {
    private final int populationSize;
    private final int allelesPerChromosome;
    private final double crossOverReturnsParentLikelihood;

    public NBodyExperimentArgument(int populationSize, int allelesPerChromosome, double crossOverReturnsParentLikelihood) {
        this.populationSize = populationSize;
        this.allelesPerChromosome = allelesPerChromosome;
        this.crossOverReturnsParentLikelihood = crossOverReturnsParentLikelihood;
    }

    private NBodyExperimentArgument(Builder builder) {
        populationSize = builder.populationSize;
        allelesPerChromosome = builder.allelesPerChromosome;
        crossOverReturnsParentLikelihood = builder.crossOverReturnsParentLikelihood;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getAllelesPerChromosome() {
        return allelesPerChromosome;
    }

    public double getCrossOverReturnsParentLikelihood() {
        return crossOverReturnsParentLikelihood;
    }


    public static final class Builder {
        private int populationSize;
        private int allelesPerChromosome;
        private double crossOverReturnsParentLikelihood;

        public Builder() {
        }

        public Builder withPopulationSize(int populationSize) {
            this.populationSize = populationSize;
            return this;
        }

        public Builder withAllelesPerChromosome(int allelesPerChromosome) {
            this.allelesPerChromosome = allelesPerChromosome;
            return this;
        }

        public Builder withCrossOverReturnsParentLikelihood(double crossOverReturnsParentLikelihood) {
            this.crossOverReturnsParentLikelihood = crossOverReturnsParentLikelihood;
            return this;
        }

        public NBodyExperimentArgument build() {
            return new NBodyExperimentArgument(this);
        }
    }
}
