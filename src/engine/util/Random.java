package engine.util;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
    private static java.util.Random rand = ThreadLocalRandom.current();

    private Random() {}

    public static double random(double min, double max) {
        if (min > max)
            throw new IllegalArgumentException("boundaries must be like min <= max");
        return min + (max - min)*rand.nextDouble();
    }
}
