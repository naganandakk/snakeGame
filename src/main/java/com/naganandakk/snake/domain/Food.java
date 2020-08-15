package com.naganandakk.snake.domain;

import com.naganandakk.snake.exceptions.InvalidPositionException;

public class Food {
    private final Position position;

    public Food(Position position) {
        if (position == null) {
            throw new InvalidPositionException();
        }

        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
