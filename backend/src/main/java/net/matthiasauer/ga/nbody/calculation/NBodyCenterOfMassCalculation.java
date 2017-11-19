package net.matthiasauer.ga.nbody.calculation;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;

public interface NBodyCenterOfMassCalculation {
    Vector2D calculate(List<NBodyAllele> bodies);
}
