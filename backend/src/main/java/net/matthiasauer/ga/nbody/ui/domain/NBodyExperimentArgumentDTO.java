package net.matthiasauer.ga.nbody.ui.domain;

import net.matthiasauer.ga.nbody.calculation.NBodyExperimentArgument;

public class NBodyExperimentArgumentDTO {
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
    private int fitnessMaxIterations;
    private double fitnessMaxDistanceBetweenBodies;
    private double fitnessMinDistanceBetweenBodies;
    private double fitnessGravityConstant;
    private int terminationMaxIterations;
    private double terminationTargetFitness;

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getNewPopulationSize() {
        return newPopulationSize;
    }

    public void setNewPopulationSize(int newPopulationSize) {
        this.newPopulationSize = newPopulationSize;
    }

    public int getAllelesPerChromosome() {
        return allelesPerChromosome;
    }

    public void setAllelesPerChromosome(int allelesPerChromosome) {
        this.allelesPerChromosome = allelesPerChromosome;
    }

    public double getCrossOverReturnsParentLikelihood() {
        return crossOverReturnsParentLikelihood;
    }

    public void setCrossOverReturnsParentLikelihood(double crossOverReturnsParentLikelihood) {
        this.crossOverReturnsParentLikelihood = crossOverReturnsParentLikelihood;
    }

    public double getMinPosXY() {
        return minPosXY;
    }

    public void setMinPosXY(double minPosXY) {
        this.minPosXY = minPosXY;
    }

    public double getMaxPosXY() {
        return maxPosXY;
    }

    public void setMaxPosXY(double maxPosXY) {
        this.maxPosXY = maxPosXY;
    }

    public double getMinMass() {
        return minMass;
    }

    public void setMinMass(double minMass) {
        this.minMass = minMass;
    }

    public double getMaxMass() {
        return maxMass;
    }

    public void setMaxMass(double maxMass) {
        this.maxMass = maxMass;
    }

    public double getMinVelocityXY() {
        return minVelocityXY;
    }

    public void setMinVelocityXY(double minVelocityXY) {
        this.minVelocityXY = minVelocityXY;
    }

    public double getMaxVelocityYY() {
        return maxVelocityYY;
    }

    public void setMaxVelocityYY(double maxVelocityYY) {
        this.maxVelocityYY = maxVelocityYY;
    }

    public double getMutateNucleotideChance() {
        return mutateNucleotideChance;
    }

    public void setMutateNucleotideChance(double mutateNucleotideChance) {
        this.mutateNucleotideChance = mutateNucleotideChance;
    }

    public int getFitnessMaxIterations() {
        return fitnessMaxIterations;
    }

    public void setFitnessMaxIterations(int fitnessMaxIterations) {
        this.fitnessMaxIterations = fitnessMaxIterations;
    }

    public double getFitnessMaxDistanceBetweenBodies() {
        return fitnessMaxDistanceBetweenBodies;
    }

    public void setFitnessMaxDistanceBetweenBodies(double fitnessMaxDistanceBetweenBodies) {
        this.fitnessMaxDistanceBetweenBodies = fitnessMaxDistanceBetweenBodies;
    }

    public double getFitnessMinDistanceBetweenBodies() {
        return fitnessMinDistanceBetweenBodies;
    }

    public void setFitnessMinDistanceBetweenBodies(double fitnessMinDistanceBetweenBodies) {
        this.fitnessMinDistanceBetweenBodies = fitnessMinDistanceBetweenBodies;
    }

    public double getFitnessGravityConstant() {
        return fitnessGravityConstant;
    }

    public void setFitnessGravityConstant(double fitnessGravityConstant) {
        this.fitnessGravityConstant = fitnessGravityConstant;
    }

    public int getTerminationMaxIterations() {
        return terminationMaxIterations;
    }

    public void setTerminationMaxIterations(int terminationMaxIterations) {
        this.terminationMaxIterations = terminationMaxIterations;
    }

    public double getTerminationTargetFitness() {
        return terminationTargetFitness;
    }

    public void setTerminationTargetFitness(double terminationTargetFitness) {
        this.terminationTargetFitness = terminationTargetFitness;
    }

    public NBodyExperimentArgument toNBodyExperimentArgument() {
        return new NBodyExperimentArgument.Builder()
                .withAllelesPerChromosome(this.getAllelesPerChromosome())
                .withCrossOverReturnsParentLikelihood(this.getCrossOverReturnsParentLikelihood())
                .withFitnessGravityConstant(this.getFitnessGravityConstant())
                .withFitnessMaxDistanceBetweenBodies(this.getFitnessMaxDistanceBetweenBodies())
                .withFitnessMinDistanceBetweenBodies(this.getFitnessMinDistanceBetweenBodies())
                .withFitnessMaxIterations(this.getFitnessMaxIterations())
                .withMaxMass(this.getMaxMass())
                .withMaxPosXY(this.getMaxPosXY())
                .withMaxVelocityYY(this.getMaxVelocityYY())
                .withMinMass(this.getMinMass())
                .withMinPosXY(this.getMinPosXY())
                .withMinVelocityXY(this.getMinVelocityXY())
                .withMutateNucleotideChance(this.getMutateNucleotideChance())
                .withNewPopulationSize(this.getNewPopulationSize())
                .withPopulationSize(this.getPopulationSize())
                .withTerminationMaxIterations(this.getTerminationMaxIterations())
                .withTerminationTargetFitness(this.getTerminationTargetFitness())
                .build();
    }

    public static NBodyExperimentArgumentDTO from(NBodyExperimentArgument experimentArgument) {
        NBodyExperimentArgumentDTO result = new NBodyExperimentArgumentDTO();
        result.setAllelesPerChromosome(experimentArgument.getAllelesPerChromosome());
        result.setCrossOverReturnsParentLikelihood(experimentArgument.getCrossOverReturnsParentLikelihood());
        result.setFitnessGravityConstant(experimentArgument.getFitnessGravityConstant());
        result.setFitnessMaxDistanceBetweenBodies(experimentArgument.getFitnessMaxDistanceBetweenBodies());
        result.setFitnessMaxIterations(experimentArgument.getFitnessMaxIterations());
        result.setFitnessMinDistanceBetweenBodies(experimentArgument.getFitnessMinDistanceBetweenBodies());
        result.setMaxMass(experimentArgument.getMaxMass());
        result.setMinMass(experimentArgument.getMinMass());
        result.setMaxPosXY(experimentArgument.getMaxPosXY());
        result.setMaxVelocityYY(experimentArgument.getMaxVelocityYY());
        result.setMinPosXY(experimentArgument.getMinPosXY());
        result.setMinVelocityXY(experimentArgument.getMinVelocityXY());
        result.setMutateNucleotideChance(experimentArgument.getMutateNucleotideChance());
        result.setNewPopulationSize(experimentArgument.getNewPopulationSize());
        result.setPopulationSize(experimentArgument.getPopulationSize());
        result.setTerminationMaxIterations(experimentArgument.getTerminationMaxIterations());
        result.setTerminationTargetFitness(experimentArgument.getTerminationTargetFitness());

        return result;
    }
}
