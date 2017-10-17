package net.matthiasauer.ga.calculation;

public class ParentChromosomes<T extends Chromosome> {
    private final T parentA;
    private final T parentB;

    public ParentChromosomes(T parentA, T parentB) {
        this.parentA = parentA;
        this.parentB = parentB;
    }

    public T getParentA() {
        return this.parentA;
    }

    public T getParentB() {
        return this.parentB;
    }
}
