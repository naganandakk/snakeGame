package com.naganandakk.snake.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.naganandakk.snake.exceptions.FoodNotFoundException;
import com.naganandakk.snake.exceptions.PositionOutOfBoundException;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void shouldCreateBoardWithValidWidthAndHeight() {
        int width = 10;
        int height = 10;

        Board board = new Board(width, height);

        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
    }

    @Test
    public void shouldCreateEmptySetOfFoodOnBoardCreation() {
        int width = 10;
        int height = 10;

        Board board = new Board(width, height);

        assertEquals(0, board.getAvailableFood().size());
    }

    @Test
    public void shouldAddGivenFoodToBoard() {
        Position position = new Position(0, 0);
        Food food = new Food(position);
        Board board = new Board(10, 10);

        board.addFood(food);

        assertTrue(board.getAvailableFood().contains(food));
    }

    @Test
    public void shouldClearGivenFoodFromBoard() {
        Position position = new Position(0, 0);
        Food food = new Food(position);
        Board board = new Board(10, 10);

        board.addFood(food);
        board.clearFood(food);

        assertFalse(board.getAvailableFood().contains(food));
    }

    @Test
    public void shouldReturnFoodAtGivenPosition() {
        Position position = new Position(0, 0);
        Food food = new Food(position);
        Board board = new Board(10, 10);

        board.addFood(food);

        assertEquals(food, board.getFoodAtPosition(position));
    }

    @Test
    public void shouldThrowFoodNotFoundExceptionWhenFoodIsNotPresentAtGivenPosition() {
        Position position = new Position(0, 0);
        Food food = new Food(position);
        Board board = new Board(10, 10);

        board.addFood(food);

        assertThrows(FoodNotFoundException.class, () -> {
            board.getFoodAtPosition(new Position(10, 10));
        });
    }

    @Test
    public void shouldThrowPositionOutOfBoundWhenAddFoodCalledWithInvalidPositionFood() {
        Position position = new Position(20, 0);
        Food food = new Food(position);
        Board board = new Board(10, 10);

        assertThrows(PositionOutOfBoundException.class, () -> {
           board.addFood(food);
        });
    }
}
