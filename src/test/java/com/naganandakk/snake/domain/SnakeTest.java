package com.naganandakk.snake.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {

    private static Object[][] shouldChangeMovementDirectionProvider() {
        return new Object[][]{
                { new Snake(0, 0, "RIGHT", 1), "UP"},
                { new Snake(0, 0, "RIGHT", 1), "DOWN"},
                { new Snake(0, 0, "LEFT", 1), "UP"},
                { new Snake(0, 0, "LEFT", 1), "DOWN"},
                { new Snake(0, 0, "UP", 1), "LEFT"},
                { new Snake(0, 0, "UP", 1), "RIGHT"},
                { new Snake(0, 0, "DOWN", 1), "LEFT"},
                { new Snake(0, 0, "DOWN", 1), "RIGHT"}
        };
    }

    private static Object[][] shouldNotChangeMovementDirectionToOppositeSideProvider() {
        return new Object[][]{
                { new Snake(0, 0, "RIGHT", 1), "LEFT"},
                { new Snake(0, 0, "LEFT", 1), "RIGHT"},
                { new Snake(0, 0, "UP", 1), "DOWN"},
                { new Snake(0, 0, "DOWN", 1), "UP"}
        };
    }

    private static Object[][] shouldGrowAfterEatingProvider() {
        return new Object[][]{
                { new Snake(0, 0, "RIGHT", 1), 2},
                { new Snake(0, 0, "LEFT", 1), 3},
                { new Snake(0, 0, "UP", 1), 4},
                { new Snake(0, 0, "DOWN", 1), 1}
        };
    }

    private static Object[][] shouldAddBodyPartsAfterEatingProvider() {
        return new Object[][]{
                {
                    new Snake(1, 0, "RIGHT", 1), 1,
                        Arrays.asList(Arrays.asList(1, 0), Arrays.asList(0, 0))
                },
                {
                        new Snake(1, 0, "LEFT", 1), 1,
                        Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 0))
                },
                {
                        new Snake(1, 1, "UP", 1), 1,
                        Arrays.asList(Arrays.asList(1, 1), Arrays.asList(1, 0))
                },
                {
                        new Snake(1, 1, "DOWN", 1), 1,
                        Arrays.asList(Arrays.asList(1, 1), Arrays.asList(1, 2))
                },
                {
                        new Snake(1, 0, "RIGHT", 2), 1,
                        Arrays.asList(Arrays.asList(1, 0), Arrays.asList(-1, 0))
                },
                {
                        new Snake(1, 0, "LEFT", 2), 1,
                        Arrays.asList(Arrays.asList(1, 0), Arrays.asList(3, 0))
                },
                {
                        new Snake(1, 1, "UP", 2), 1,
                        Arrays.asList(Arrays.asList(1, 1), Arrays.asList(1, -1))
                },
                {
                        new Snake(1, 1, "DOWN", 2), 1,
                        Arrays.asList(Arrays.asList(1, 1), Arrays.asList(1, 3))
                }
        };
    }

    @Test
    void shouldCreateSnakeObjectWithGivenArguments() {
        int posX = 0, posY = 0, speed = 1;
        String movementDirection = "RIGHT";

        Snake snake = new Snake(posX, posY, movementDirection, speed);

        assertEquals(posX, snake.getPosX());
        assertEquals(posY, snake.getPosY());
        assertEquals(movementDirection, snake.getMovementDirection());
        assertEquals(speed, snake.getSpeed());
        assertEquals(1, snake.getBody().size());
    }

    @ParameterizedTest
    @MethodSource("shouldChangeMovementDirectionProvider")
    void shouldChangeMovementDirection(Snake snake, String changeTowards) {
        snake.changeMovementDirection(changeTowards);

        assertEquals(changeTowards, snake.getMovementDirection());
    }

    @ParameterizedTest
    @MethodSource("shouldNotChangeMovementDirectionToOppositeSideProvider")
    void shouldNotChangeMovementDirectionToOppositeSide(Snake snake, String oppositeDirection) {
        snake.changeMovementDirection(oppositeDirection);

        assertNotEquals(oppositeDirection, snake.getMovementDirection());
    }

    @ParameterizedTest
    @MethodSource("shouldGrowAfterEatingProvider")
    void shouldGrowAfterEating(Snake snake, int foodQuantity) {
        int expectedBodySize = snake.getBody().size() + foodQuantity;
        snake.eat(foodQuantity);

        assertEquals(expectedBodySize, snake.getBody().size());
    }

    @ParameterizedTest
    @MethodSource("shouldAddBodyPartsAfterEatingProvider")
    void shouldAddBodyPartsAfterEating(Snake snake, int foodQuantity, List<List<Integer>> expectedBody) {
        snake.eat(foodQuantity);
        List<List<Integer>> actualBody = snake.getBody();

        assertEquals(expectedBody.size(), actualBody.size());

        for (int index = 0; index < expectedBody.size(); index++) {
            List<Integer> expectedBodyPart = expectedBody.get(index);
            List<Integer> actualBodyPart = actualBody.get(index);
            Integer expectedPosX = expectedBodyPart.get(0);
            Integer expectedPosY = expectedBodyPart.get(1);
            Integer actualPosX = actualBodyPart.get(0);
            Integer actualPosY = actualBodyPart.get(1);

            if ((!expectedPosX.equals(actualPosX)) || (expectedPosY != actualPosY)) {
                fail("Invalid snake body");
            }
        }
    }
}
