package com.naganandakk.snake.domain;

import com.naganandakk.snake.enums.Direction;

import java.util.Objects;

public class Position {

    private Integer x;
    private Integer y;

    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Position next(Direction movementDirection, int moveByUnit) {
        if (moveByUnit < 0) {
            throw new IllegalArgumentException("moveByUnit must be >= 0");
        } else if (movementDirection == null) {
            throw new IllegalArgumentException("Invalid movementDirection");
        }

        switch (movementDirection) {
            case RIGHT: return new Position(x + moveByUnit, y);
            case LEFT: return new Position(x - moveByUnit, y);
            case DOWN: return new Position(x, y - moveByUnit);
            case UP: return new Position(x, y + moveByUnit);
        }

        return new Position(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) &&
                Objects.equals(y, position.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
