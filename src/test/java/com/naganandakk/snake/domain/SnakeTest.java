package com.naganandakk.snake.domain;

import com.naganandakk.snake.enums.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {

    private static Object[][] shouldChangeMovementDirectionExceptOppositeDirectionProvider() {
        return new Object[][]{
                {new Snake(new Position(0, 0), Direction.RIGHT, 1), Direction.UP, Direction.UP},
                {new Snake(new Position(0, 0), Direction.RIGHT, 1), Direction.DOWN, Direction.DOWN},
                {new Snake(new Position(0, 0), Direction.LEFT, 1), Direction.UP, Direction.UP},
                {new Snake(new Position(0, 0), Direction.LEFT, 1), Direction.DOWN, Direction.DOWN},
                {new Snake(new Position(0, 0), Direction.UP, 1), Direction.LEFT, Direction.LEFT},
                {new Snake(new Position(0, 0), Direction.UP, 1), Direction.RIGHT, Direction.RIGHT},
                {new Snake(new Position(0, 0), Direction.DOWN, 1), Direction.LEFT, Direction.LEFT},
                {new Snake(new Position(0, 0), Direction.DOWN, 1), Direction.RIGHT, Direction.RIGHT},
                {new Snake(new Position(0, 0), Direction.RIGHT, 1), Direction.LEFT, Direction.RIGHT},
                {new Snake(new Position(0, 0), Direction.LEFT, 1), Direction.RIGHT, Direction.LEFT},
                {new Snake(new Position(0, 0), Direction.UP, 1), Direction.DOWN, Direction.UP},
                {new Snake(new Position(0, 0), Direction.DOWN, 1), Direction.UP, Direction.DOWN}
        };
    }

    private static Object[][] shouldGrowAfterEatingProvider() {
        return new Object[][]{
                {new Snake(new Position(0, 0), Direction.RIGHT, 1), 2},
                {new Snake(new Position(0, 0), Direction.LEFT, 1), 3},
                {new Snake(new Position(0, 0), Direction.UP, 1), 4},
                {new Snake(new Position(0, 0), Direction.DOWN, 1), 1}
        };
    }

    private static Object[][] shouldMoveInCurrentDirectionProvider() {
        Snake snakeRight = new Snake(new Position(0, 0), Direction.RIGHT, 1);
        Snake snakeLeft = new Snake(new Position(3, 0), Direction.LEFT, 1);
        Snake snakeUp = new Snake(new Position(0, 0), Direction.UP, 1);
        Snake snakeDown = new Snake(new Position(0, 3), Direction.DOWN, 1);
        Snake snakeRightSinglePart = new Snake(new Position(0, 0), Direction.RIGHT, 1);
        Snake snakeWithSpeedTwo = new Snake(new Position(0, 0), Direction.RIGHT, 2);

        snakeRight.eat(1);
        snakeLeft.eat(1);
        snakeDown.eat(1);
        snakeUp.eat(1);
        snakeWithSpeedTwo.eat(1);

        List<Position> snakeRightExpectedBody = Arrays.asList(new Position(1, 0), new Position(0, 0));
        List<Position> snakeLeftExpectedBody = Arrays.asList(new Position(2, 0), new Position(3, 0));
        List<Position> snakeUpExpectedBody = Arrays.asList(new Position(0, 1), new Position(0, 0));
        List<Position> snakeDownExpectedBody = Arrays.asList(new Position(0, 2), new Position(0, 3));
        List<Position> snakeRightSinglePartExpectedBody = Arrays.asList(new Position(1, 0));
        List<Position> snakeWithSpeedTwoExpectedBody = Arrays.asList(new Position(2, 0), new Position(0, 0));

        return new Object[][]{
                {snakeRight, snakeRightExpectedBody},
                {snakeLeft, snakeLeftExpectedBody},
                {snakeUp, snakeUpExpectedBody},
                {snakeDown, snakeDownExpectedBody},
                {snakeRightSinglePart, snakeRightSinglePartExpectedBody},
                {snakeWithSpeedTwo, snakeWithSpeedTwoExpectedBody}
        };
    }

    private static Object[][] shouldAddBodyPartsAfterEatingProvider() {
        return new Object[][]{
                {
                        new Snake(new Position(1, 0), Direction.RIGHT, 1), 1,
                        Arrays.asList(new Position(1, 0), new Position(0, 0))
                },
                {
                        new Snake(new Position(1, 0), Direction.LEFT, 1), 1,
                        Arrays.asList(new Position(1, 0), new Position(2, 0))
                },
                {
                        new Snake(new Position(1, 1), Direction.UP, 1), 1,
                        Arrays.asList(new Position(1, 1), new Position(1, 0))
                },
                {
                        new Snake(new Position(1, 1), Direction.DOWN, 1), 1,
                        Arrays.asList(new Position(1, 1), new Position(1, 2))
                },
                {
                        new Snake(new Position(1, 0), Direction.RIGHT, 2), 1,
                        Arrays.asList(new Position(1, 0), new Position(-1, 0))
                },
                {
                        new Snake(new Position(1, 0), Direction.LEFT, 2), 1,
                        Arrays.asList(new Position(1, 0), new Position(3, 0))
                },
                {
                        new Snake(new Position(1, 1), Direction.UP, 2), 1,
                        Arrays.asList(new Position(1, 1), new Position(1, -1))
                },
                {
                        new Snake(new Position(1, 1), Direction.DOWN, 2), 1,
                        Arrays.asList(new Position(1, 1), new Position(1, 3))
                }
        };
    }

    @Test
    void shouldCreateSnakeObjectWithGivenArguments() {
        Position position = new Position(0, 0);
        Integer speed = 1;
        Direction movementDirection = Direction.RIGHT;

        Snake snake = new Snake(position, movementDirection, speed);

        assertEquals(movementDirection, snake.getMovementDirection());
        assertEquals(speed, snake.getSpeed());
        assertEquals(1, snake.getBody().size());
    }

    @ParameterizedTest
    @MethodSource("shouldChangeMovementDirectionExceptOppositeDirectionProvider")
    void shouldChangeMovementDirectionExceptOppositeDirection(Snake snake, Direction changeTowards, Direction expected) {
        snake.changeMovementDirection(changeTowards);

        assertEquals(expected, snake.getMovementDirection());
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
    void shouldAddBodyPartsAfterEating(Snake snake, int foodQuantity, List<Position> expectedBody) {
        snake.eat(foodQuantity);
        assertEquals(expectedBody.size(), snake.getBody().size());
        checkSnakeBody(snake, expectedBody);
    }

    @ParameterizedTest
    @MethodSource("shouldMoveInCurrentDirectionProvider")
    void shouldMoveInCurrentDirection(Snake snake, List<Position> expectedBody) {
        snake.move();

        checkSnakeBody(snake, expectedBody);
    }

    private void checkSnakeBody(Snake snake, List<Position> expectedBody) {
        List<Position> actualBody = snake.getBody();
        for (int index = 0; index < expectedBody.size(); index++) {
            Position expectedBodyPart = expectedBody.get(index);
            Position actualBodyPart = actualBody.get(index);

            if (!expectedBodyPart.equals(actualBodyPart)) {
                fail("Invalid snake body");
            }
        }
    }
}
