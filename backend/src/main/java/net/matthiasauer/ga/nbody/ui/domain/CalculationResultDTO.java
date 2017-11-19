package net.matthiasauer.ga.nbody.ui.domain;

import net.matthiasauer.ga.nbody.calculation.NBodyAllele;
import net.matthiasauer.ga.nbody.calculation.NBodyCenterOfMassCalculation;

import java.util.List;
import java.util.stream.Collectors;

public class CalculationResultDTO {
    private List<IterationDTO> iterations;
    private double fitness;

    public List<IterationDTO> getIterations() {
        return iterations;
    }

    public void setIterations(List<IterationDTO> iterations) {
        this.iterations = iterations;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public static CalculationResultDTO from(double fitness, List<List<NBodyAllele>> iterations, NBodyCenterOfMassCalculation centerOfMassCalculation) {
        CalculationResultDTO dto = new CalculationResultDTO();
        dto.setFitness(fitness);
        dto.setIterations(
                iterations.stream()
                        .map(iteration -> IterationDTO.from(iteration, centerOfMassCalculation))
                        .collect(Collectors.toList())

        );

        return dto;
    }
}
