package net.matthiasauer.ga.nbody.ui.domain;

import net.matthiasauer.ga.nbody.calculation.NBodyAllele;
import net.matthiasauer.ga.nbody.calculation.NBodyCenterOfMassCalculation;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;
import java.util.stream.Collectors;

public class IterationDTO {
    private List<NBodyAlleleDTO> bodies;
    private CenterOfMassDTO centerOfMass;
    public List<NBodyAlleleDTO> getBodies() {
        return bodies;
    }

    public CenterOfMassDTO getCenterOfMass() {
        return centerOfMass;
    }

    public void setCenterOfMass(CenterOfMassDTO centerOfMass) {
        this.centerOfMass = centerOfMass;
    }

    public void setBodies(List<NBodyAlleleDTO> bodies) {
        this.bodies = bodies;
    }

    public static IterationDTO from(List<NBodyAllele> iteration, NBodyCenterOfMassCalculation centerOfMassCalculation) {
        IterationDTO dto = new IterationDTO();
        dto.setBodies(
                iteration.stream()
                        .map(NBodyAlleleDTO::from)
                        .collect(Collectors.toList())
        );
        Vector2D centerOfMass = centerOfMassCalculation.calculate(iteration);
        dto.setCenterOfMass(
                CenterOfMassDTO.from(centerOfMass)
        );

        return dto;
    }
}
