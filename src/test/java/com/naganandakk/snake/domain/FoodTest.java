package com.naganandakk.snake.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.naganandakk.snake.exceptions.InvalidPositionException;
import org.junit.jupiter.api.Test;

public class FoodTest {

    @Test
    public void shouldCreateFoodWithGivenPosition() {
        Position position = new Position(1, 1);

        Food food = new Food(position);

        assertEquals(position, food.getPosition());
    }

    @Test
    public void shouldThrowExceptionWhenPositionIsNull() {
        assertThrows(InvalidPositionException.class, () -> {
           new Food(null);
        });
    }
}
