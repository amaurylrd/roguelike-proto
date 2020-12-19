package engine.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The class {@code Random} is used to generate pseudo-random numbers.
 */
public class Random {
    private static java.util.Random rand = ThreadLocalRandom.current();

    private Random() {}

    /**
     * Returns a double value between {@code min} and {@code max}.
     * 
     * @param min the lower bound
     * @param max the higher bound
     * @return a pseudo-random between {@code min} and {@code max}
     */
    public static double random(double min, double max) {
        if (min > max)
            throw new IllegalArgumentException("boundaries must be like min <= max");
        return min + (max - min)*rand.nextDouble();
    }
}
