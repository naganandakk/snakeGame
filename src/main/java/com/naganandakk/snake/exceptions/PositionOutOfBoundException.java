package com.naganandakk.snake.exceptions;

public class PositionOutOfBoundException extends RuntimeException {
    public PositionOutOfBoundException(String message) {
        super(message);
    }
}
