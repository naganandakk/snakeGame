package com.naganandakk.snake.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SnakeTest {

    private static Object[][] shouldChangeMovementDirectionProvider() {
        return new Object[][]{
                { new Snake(0, 0, "RIGHT", 0), "UP"},
                { new Snake(0, 0, "RIGHT", 0), "DOWN"},
                { new Snake(0, 0, "LEFT", 0), "UP"},
                { new Snake(0, 0, "LEFT", 0), "DOWN"},
                { new Snake(0, 0, "UP", 0), "LEFT"},
                { new Snake(0, 0, "UP", 0), "RIGHT"},
                { new Snake(0, 0, "DOWN", 0), "LEFT"},
                { new Snake(0, 0, "DOWN", 0), "RIGHT"}
        };
    }

    private static Object[][] shouldNotChangeMovementDirectionToOppositeSideProvider() {
        return new Object[][]{
                { new Snake(0, 0, "RIGHT", 0), "LEFT"},
                { new Snake(0, 0, "LEFT", 0), "RIGHT"},
                { new Snake(0, 0, "UP", 0), "DOWN"},
                { new Snake(0, 0, "DOWN", 0), "UP"}
        };
    }

    private static Object[][] shouldGrowAfterEatingProvider() {
        return new Object[][]{
                { new Snake(0, 0, "RIGHT", 0), 2},
                { new Snake(0, 0, "LEFT", 0), 3},
                { new Snake(0, 0, "UP", 0), 4},
                { new Snake(0, 0, "DOWN", 0), 1}
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
}
