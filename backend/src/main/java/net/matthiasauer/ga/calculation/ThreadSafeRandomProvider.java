package net.matthiasauer.ga.calculation;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class ThreadSafeRandomProvider implements RandomProvider {

    @Override
    public double getNextDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    @Override
    public boolean getNextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
