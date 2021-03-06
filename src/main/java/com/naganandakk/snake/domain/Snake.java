package com.naganandakk.snake.domain;

import com.naganandakk.snake.enums.Direction;

import java.util.*;

public class Snake {
    private static final int HEAD_INDEX = 0;

    private Direction movementDirection;
    private final Integer speed;
    private final List<Position> body;

    public Snake(Position initialPosition, Direction movementDirection, Integer speed) {
        this.movementDirection = movementDirection;
        this.speed = speed;
        this.body = new LinkedList<>();
        this.body.add(initialPosition);
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public int getSpeed() {
        return speed;
    }

    public List<Position> getBody() {
        return body;
    }

    public void changeMovementDirection(Direction changeTowards) {
        Direction oppositeDirection = changeTowards.opposite();

        if (!movementDirection.equals(oppositeDirection)) {
            movementDirection = changeTowards;
        }
    }

    public void eat(int foodQuantity) {
        while(foodQuantity-- > 0) {
            Position tail = body.get(body.size() - 1);
            body.add(tail.next(movementDirection.opposite(), speed));
        }
    }

    public void move() {
        // shift snake body parts except head
        if (body.size() > 1) {
            for (int index = body.size() - 1; index >= 1; index--) {
                body.set(index, body.get(index - 1));
            }
        }

        Position head = body.get(HEAD_INDEX);
        Position newHead = head.next(movementDirection, speed);

        body.set(HEAD_INDEX, newHead);
    }

    public Position head() {
        return body.get(HEAD_INDEX);
    }

    public boolean crossedSelf() {
        boolean snakeCrossedSelf = false;

        for (int index = 1; index < body.size(); index++) {
            if ((head().getX().equals(body.get(index).getX())) && (head().getY().equals(body.get(index).getY()))) {
                snakeCrossedSelf = true;
                break;
            }
        }

        return snakeCrossedSelf;
    }
}
