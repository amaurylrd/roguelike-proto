package com.engine.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The class {@code Random} is used to generate pseudo-random numbers.
 */
public final class Random {
    private static java.util.Random rand = ThreadLocalRandom.current();

    private Random() {}

    /**
     * Returns a float value between {@code min} and {@code max}.
     * 
     * @param min the lower bound
     * @param max the higher bound
     * @return a pseudo-random between {@code min} and {@code max}
     */
    public static float nextFloat(float min, float max) {
        if (min > max)
            throw new IllegalArgumentException("boundaries must be like min <= max");
        return min + (max - min) * rand.nextFloat();
    }

    /**
     * Returns a float value between 0 and 1.
     * 
     * @return a pseudo-random between 0 and 1
     */
    public static float nextFloat() {
        return rand.nextFloat();
    }

    /**
     * Returns a float value between 0 and {@code max}.
     * 
     * @param max the value value
     * @return a pseudo-random between 0 and {@code max}
     */
    public static float nextFloat(float max) {
        return max * rand.nextFloat();
    }

    /**
     * Returns an integer between {@code min} and {@code max}.
     * 
     * @param min the lower bound
     * @param max the higher bound
     * @return a pseudo-random between {@code min} and {@code max}
     */
    public static int nextInt(int min, int max) {
        if (min > max)
            throw new IllegalArgumentException("boundaries must be like min <= max");
        return min + (max - min) * rand.nextInt();
    }

    /**
     * Returns an integer between 0 and 1.
     * 
     * @return a pseudo-random between 0 and 1
     */
    public static int nextInt() {
        return rand.nextInt();
    }

    /**
     * Returns an integer between 0 and {@code max}.
     * 
     * @param max the max value
     * @return a pseudo-random between 0 and {@code max}
     */
    public static int nextInt(int max) {
        return (int) (max * rand.nextDouble());
    }
}
