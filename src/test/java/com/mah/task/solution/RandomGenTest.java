package com.mah.task.solution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RandomGenTest {
    private RandomGen randomGen;

    @BeforeEach
    public void setup() {
        int[] randomNums = {-1, 0, 1, 2, 3};
        float[] probabilities = {0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
        randomGen = new RandomGen(randomNums, probabilities);
    }

    @Test
    public void testNextNumDistribution() {
        // Arrange
        int numIterations = 1000000;
        int[] counts = new int[5];
        float[] expectedProbabilities = {0.01f, 0.3f, 0.58f, 0.1f, 0.01f};

        // Act
        for (int i = 0; i < numIterations; i++) {
            int num = randomGen.nextNum();
            counts[num + 1]++;
        }

        // Assert
        float[] frequencies = new float[5];
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = (float) counts[i] / numIterations;
        }
        for (int i = 0; i < frequencies.length; i++) {
            Assertions.assertEquals(expectedProbabilities[i], frequencies[i], 0.01f);
        }
    }

    @Test
    public void testNextNumBounds() {
        // Arrange
        int numIterations = 1000000;

        // Act and Assert
        for (int i = 0; i < numIterations; i++) {
            int num = randomGen.nextNum();
            Assertions.assertTrue(num >= -1 && num <= 3);
        }
    }

    @Test
    public void testNextNumProbabilityEdgeCase() {
        // Arrange
        int[] randomNums = {0};
        float[] probabilities = {1.0f};
        randomGen = new RandomGen(randomNums, probabilities);
        int numIterations = 1000000;
        int count = 0;

        // Act
        for (int i = 0; i < numIterations; i++) {
            int num = randomGen.nextNum();
            if (num == 0) {
                count++;
            }
        }

        // Assert
        Assertions.assertEquals(numIterations, count);
    }

    @Test
    public void testNextNumSingleNumber() {
        // Arrange
        int[] randomNums = {42};
        float[] probabilities = {1.0f};
        randomGen = new RandomGen(randomNums, probabilities);
        int numIterations = 1000000;
        int count = 0;

        // Act
        for (int i = 0; i < numIterations; i++) {
            int num = randomGen.nextNum();
            if (num == 42) {
                count++;
            }
        }

        // Assert
        Assertions.assertEquals(numIterations, count);
    }

    @Test
    public void testValidInputs() {
        // Arrange
        int[] randomNums = {1, 2, 3};
        float[] probabilities = {0.2f, 0.3f, 0.5f};

        // Act and Assert
        Assertions.assertDoesNotThrow(() -> {
            new RandomGen(randomNums, probabilities);
        });
    }

    @Test
    public void testNullRandomNums() {
        // Arrange
        int[] randomNums = null;
        float[] probabilities = {0.2f, 0.3f, 0.5f};

        // Act and Assert
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));

        // Assert
        Assertions.assertEquals("Invalid inputs: randomNums and probabilities must have the same length.", exception.getMessage());
    }

    @Test
    public void testNullProbabilities() {
        // Arrange
        int[] randomNums = {1, 2, 3};
        float[] probabilities = null;

        // Act and Assert
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));

        // Assert
        Assertions.assertEquals("Invalid inputs: randomNums and probabilities must have the same length.", exception.getMessage());
    }

    @Test
    public void testDifferentLengths() {
        // Arrange
        int[] randomNums = {1, 2, 3};
        float[] probabilities = {0.2f, 0.3f, 0.5f, 0.1f};

        // Act and Assert
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));

        // Assert
        Assertions.assertEquals("Invalid inputs: randomNums and probabilities must have the same length.", exception.getMessage());
    }

    @Test
    public void testInvalidProbabilityRange() {
        // Arrange
        int[] randomNums = {1, 2, 3};
        float[] probabilities = {0.2f, -0.3f, 0.5f};

        // Act and Assert
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));

        // Assert
        Assertions.assertEquals("Invalid inputs: probabilities must be between 0.0 and 1.0 (inclusive).", exception.getMessage());
    }

    @Test
    public void testInvalidProbabilitySum() {
        // Arrange
        int[] randomNums = {1, 2, 3};
        float[] probabilities = {0.2f, 0.3f, 0.4f};

        // Act and Assert
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));

        // Assert
        Assertions.assertEquals("Invalid inputs: sum of probabilities must be 1.0.", exception.getMessage());
    }
}