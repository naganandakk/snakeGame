package com.naganandakk.snake.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.naganandakk.snake.exceptions.InvalidPositionException;
import org.junit.jupiter.api.Test;

class FoodTest {

    @Test
    void shouldCreateFoodWithGivenPosition() {
        Position position = new Position(1, 1);

        Food food = new Food(position);

        assertEquals(position, food.getPosition());
    }

    @Test
    void shouldThrowExceptionWhenPositionIsNull() {
        assertThrows(InvalidPositionException.class, () -> {
           new Food(null);
        });
    }
}
