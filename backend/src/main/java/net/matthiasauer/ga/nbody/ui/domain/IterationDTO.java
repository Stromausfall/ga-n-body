package net.matthiasauer.ga.nbody.ui.domain;

import net.matthiasauer.ga.nbody.calculation.NBodyAllele;
import net.matthiasauer.ga.nbody.calculation.NBodyChromosome;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class IterationDTO {
    private List<NBodyAlleleDTO> bodies;

    public List<NBodyAlleleDTO> getBodies() {
        return bodies;
    }

    public void setBodies(List<NBodyAlleleDTO> bodies) {
        this.bodies = bodies;
    }

    public static IterationDTO from(List<NBodyAllele> iteration) {
        IterationDTO dto = new IterationDTO();
        dto.setBodies(
                iteration.stream()
                .map(NBodyAlleleDTO::from)
                .collect(Collectors.toList())
        );

        return dto;
    }
}
