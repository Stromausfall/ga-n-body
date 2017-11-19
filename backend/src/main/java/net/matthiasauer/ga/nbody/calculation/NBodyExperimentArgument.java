package net.matthiasauer.ga.nbody.calculation;

import net.matthiasauer.ga.calculation.ExperimentArgument;

public class NBodyExperimentArgument implements ExperimentArgument {
    private final int populationSize;
    private final int newPopulationSize;
    private final int allelesPerChromosome;
    private final double crossOverReturnsParentLikelihood;
    private final double minPosXY;
    private final double maxPosXY;
    private final double minMass;
    private final double maxMass;
    private final double minVelocityXY;
    private final double maxVelocityYY;
    private final double mutateNucleotideChance;
    private final double fitnessMaxDistanceBetweenBodies;
    private final double fitnessMinDistanceBetweenBodies;
    private final double fitnessGravityConstant;
    private final int terminationMaxIterations;
    private final double terminationTargetFitness;

    private NBodyExperimentArgument(Builder builder) {
        populationSize = builder.populationSize;
        newPopulationSize = builder.newPopulationSize;
        allelesPerChromosome = builder.allelesPerChromosome;
        crossOverReturnsParentLikelihood = builder.crossOverReturnsParentLikelihood;
        minPosXY = builder.minPosXY;
        maxPosXY = builder.maxPosXY;
        minMass = builder.minMass;
        maxMass = builder.maxMass;
        minVelocityXY = builder.minVelocityXY;
        maxVelocityYY = builder.maxVelocityYY;
        mutateNucleotideChance = builder.mutateNucleotideChance;
        fitnessMaxDistanceBetweenBodies = builder.fitnessMaxDistanceBetweenBodies;
        fitnessMinDistanceBetweenBodies = builder.fitnessMinDistanceBetweenBodies;
        fitnessGravityConstant = builder.fitnessGravityConstant;
        terminationMaxIterations = builder.terminationMaxIterations;
        terminationTargetFitness = builder.terminationTargetFitness;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getNewPopulationSize() {
        return newPopulationSize;
    }

    public int getAllelesPerChromosome() {
        return allelesPerChromosome;
    }

    public double getCrossOverReturnsParentLikelihood() {
        return crossOverReturnsParentLikelihood;
    }

    public double getMinPosXY() {
        return minPosXY;
    }

    public double getMaxPosXY() {
        return maxPosXY;
    }

    public double getMinMass() {
        return minMass;
    }

    public double getMaxMass() {
        return maxMass;
    }

    public double getMinVelocityXY() {
        return minVelocityXY;
    }

    public double getMaxVelocityYY() {
        return maxVelocityYY;
    }

    public double getMutateNucleotideChance() {
        return mutateNucleotideChance;
    }

    public double getFitnessMaxDistanceBetweenBodies() {
        return fitnessMaxDistanceBetweenBodies;
    }

    public double  () {
        return fitnessMinDistanceBetweenBodies;
    }

    public double getFitnessGravityConstant() {
        return fitnessGravityConstant;
    }

    public int getTerminationMaxIterations() {
        return terminationMaxIterations;
    }

    public double getTerminationTargetFitness() {
        return terminationTargetFitness;
    }

    public static final class Builder {
        private int populationSize;
        private int newPopulationSize;
        private int allelesPerChromosome;
        private double crossOverReturnsParentLikelihood;
        private double minPosXY;
        private double maxPosXY;
        private double minMass;
        private double maxMass;
        private double minVelocityXY;
        private double maxVelocityYY;
        private double mutateNucleotideChance;
        private double fitnessMaxDistanceBetweenBodies;
        private double fitnessMinDistanceBetweenBodies;
        private double fitnessGravityConstant;
        private int terminationMaxIterations;
        private double terminationTargetFitness;

        public Builder() {
        }

        public Builder withPopulationSize(int populationSize) {
            this.populationSize = populationSize;
            return this;
        }

        public Builder withNewPopulationSize(int newPopulationSize) {
            this.newPopulationSize = newPopulationSize;
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

        public Builder withMinPosXY(double minPosXY) {
            this.minPosXY = minPosXY;
            return this;
        }

        public Builder withMaxPosXY(double maxPosXY) {
            this.maxPosXY = maxPosXY;
            return this;
        }

        public Builder withMinMass(double minMass) {
            this.minMass = minMass;
            return this;
        }

        public Builder withMaxMass(double maxMass) {
            this.maxMass = maxMass;
            return this;
        }

        public Builder withMinVelocityXY(double minVelocityXY) {
            this.minVelocityXY = minVelocityXY;
            return this;
        }

        public Builder withMaxVelocityYY(double maxVelocityYY) {
            this.maxVelocityYY = maxVelocityYY;
            return this;
        }

        public Builder withMutateNucleotideChance(double mutateNucleotideChance) {
            this.mutateNucleotideChance = mutateNucleotideChance;
            return this;
        }

        public Builder withFitnessMaxDistanceBetweenBodies(double fitnessMaxDistanceBetweenBodies) {
            this.fitnessMaxDistanceBetweenBodies = fitnessMaxDistanceBetweenBodies;
            return this;
        }

        public Builder withFitnessMinDistanceBetweenBodies(double fitnessMinDistanceBetweenBodies) {
            this.fitnessMinDistanceBetweenBodies = fitnessMinDistanceBetweenBodies;
            return this;
        }

        public Builder withFitnessGravityConstant(double fitnessGravityConstant) {
            this.fitnessGravityConstant = fitnessGravityConstant;
            return this;
        }

        public Builder withTerminationMaxIterations(int terminationMaxIterations) {
            this.terminationMaxIterations = terminationMaxIterations;
            return this;
        }

        public Builder withTerminationTargetFitness(double terminationTargetFitness) {
            this.terminationTargetFitness = terminationTargetFitness;
            return this;
        }

        public NBodyExperimentArgument build() {
            return new NBodyExperimentArgument(this);
        }
    }
}
