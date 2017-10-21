package net.matthiasauer.ga.calculation;

public interface RandomProvider {
    double nextDouble(double origin, double bound);
    double nextDouble();
    boolean nextBoolean();
}
