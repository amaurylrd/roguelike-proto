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
    public static double nextDouble(double min, double max) {
        if (min > max)
            throw new IllegalArgumentException("boundaries must be like min <= max");
        return min + (max - min)*rand.nextDouble();
    }

    public static double nextDouble() {
        return rand.nextDouble();
    }

    public static double nextDouble(double max) {
        return max*rand.nextDouble();
    }

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
        return min + (max - min)*rand.nextFloat();
    }

    public static float nextFloat() {
        return rand.nextFloat();
    }

    public static float nextFloat(float max) {
        return max*rand.nextFloat();
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
        return min + (max - min)*rand.nextInt();
    }

    public static int nextInt() {
        return rand.nextInt();
    }

    public static int nextInt(int max) {
        return (int) (max*rand.nextDouble());
    }
}
