package net.matthiasauer.ga.calculation;

public interface RandomProvider {
    int nextInt(int origin, int bound);
    double nextDouble(double origin, double bound);
    double nextDouble();
    boolean nextBoolean();
}
