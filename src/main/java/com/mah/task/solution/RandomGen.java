package com.mah.task.solution;

import java.util.Random;

import static java.lang.String.format;

/**
 * {@link RandomGen} class generates random numbers based on given probabilities.
 *
 * @author angel.simeonov94@gmail.com
 */
public class RandomGen {
    public static final float INITIAL_CUMULATIVE_VALUE = 0.0f;
    public static final float MIN_PROBABILITY_VALUE = 0.0f;
    public static final float MAX_PROBABILITY_VALUE = 1.0f;
    public static final float SUM_OF_PROBABILITY_VALUES = 1.0f;
    public static final float INITIAL_PROBABILITY_SUM_VALUE = 0.0f;
    public static final String INVALID_INPUTS_TEMPLATE = "Invalid inputs: %s";
    private final int[] randomNums;
    private final float[] probabilities;
    private final Random random;

    public RandomGen(int[] randomNums, float[] probabilities) {
        validateInputs(randomNums, probabilities);
        this.randomNums = randomNums;
        this.probabilities = probabilities;
        this.random = new Random();
    }

    /**
     * Generates a random number based on the initialized probabilities.
     *
     * @return a random number from the randomNums array.
     */
    public int nextNum() {
        final float randomNumber = random.nextFloat();
        float cumulativeProbability = INITIAL_CUMULATIVE_VALUE;

        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomNumber < cumulativeProbability) {
                return randomNums[i];
            }
        }

        return randomNums[randomNums.length - 1];
    }

    private void validateInputs(int[] randomNums, float[] probabilities) {
        if (randomNums == null || probabilities == null || randomNums.length != probabilities.length) {
            throw new IllegalArgumentException(format(INVALID_INPUTS_TEMPLATE, "randomNums and probabilities must have the same length."));
        }

        float sumProbabilities = INITIAL_PROBABILITY_SUM_VALUE;
        for (float probability : probabilities) {
            if (probability < MIN_PROBABILITY_VALUE || probability > MAX_PROBABILITY_VALUE) {
                throw new IllegalArgumentException(format(INVALID_INPUTS_TEMPLATE, "probabilities must be between 0.0 and 1.0 (inclusive)."));
            }
            sumProbabilities += probability;
        }

        if (sumProbabilities != SUM_OF_PROBABILITY_VALUES) {
            throw new IllegalArgumentException(format(INVALID_INPUTS_TEMPLATE, "sum of probabilities must be 1.0."));
        }
    }
}