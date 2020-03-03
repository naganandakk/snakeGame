package com.naganandakk.snake.domain;

import com.naganandakk.snake.enums.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PositionTest {

    private static Object[][] shouldReturnValidNextPositionProvider() {
        return new Object[][]{
                {new Position(0, 0), Direction.RIGHT, 1, new Position(1, 0)},
                {new Position(0, 0), Direction.LEFT, 1, new Position(-1, 0)},
                {new Position(0, 0), Direction.UP, 1, new Position(0, 1)},
                {new Position(0, 0), Direction.DOWN, 1, new Position(0, -1)},
                {new Position(0, 0), Direction.RIGHT, 2, new Position(2, 0)},
                {new Position(0, 0), Direction.LEFT, 2, new Position(-2, 0)},
                {new Position(0, 0), Direction.UP, 2, new Position(0, 2)},
                {new Position(0, 0), Direction.DOWN, 2, new Position(0, -2)}
        };
    }

    private static Object[][] shouldThrowExceptionWhenNextIsCalledWithInvalidArgumentsProvider() {
        return new Object[][]{
                {new Position(0, 0), null, 1, IllegalArgumentException.class},
                {new Position(0, 0), Direction.RIGHT, -1, IllegalArgumentException.class}
        };
    }

    @ParameterizedTest
    @MethodSource("shouldReturnValidNextPositionProvider")
    void shouldReturnValidNextPosition(
            Position currentPosition, Direction movementDirection,
            int moveByUnit, Position expectedPosition
    ) {
        Position actualPosition = currentPosition.next(movementDirection, moveByUnit);

        assertEquals(expectedPosition, actualPosition);
    }

    @ParameterizedTest
    @MethodSource("shouldThrowExceptionWhenNextIsCalledWithInvalidArgumentsProvider")
    void shouldThrowExceptionWhenNextIsCalledWithInvalidArguments(
            Position currentPosition, Direction movementDirection, int moveByUnit,
            Class<Throwable> expectedErrClass
    ) {
        assertThrows(expectedErrClass, ()-> {
            currentPosition.next(movementDirection, moveByUnit);
        });
    }
}
