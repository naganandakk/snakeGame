package com.naganandakk.snake.domain;

import com.naganandakk.snake.enums.Direction;

import java.util.*;

public class Snake {

    private Direction movementDirection;
    private Integer speed;
    private List<Position> body;

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
        Map<Direction, Direction> oppositeDirections = new HashMap<>();
        oppositeDirections.put(Direction.LEFT, Direction.RIGHT);
        oppositeDirections.put(Direction.RIGHT, Direction.LEFT);
        oppositeDirections.put(Direction.UP, Direction.DOWN);
        oppositeDirections.put(Direction.DOWN, Direction.UP);

        Direction oppositeDirection = oppositeDirections.getOrDefault(changeTowards, null);

        if (!oppositeDirection.equals(movementDirection)) {
            movementDirection = changeTowards;
        }
    }

    public void eat(int foodQuantity) {
        while(foodQuantity-- > 0) {
            Position tail = body.get(body.size() - 1);
            Integer tailX = tail.getX();
            Integer tailY = tail.getY();

            switch (movementDirection) {
                case RIGHT:
                    tailX -= speed;
                    break;
                case LEFT:
                    tailX += speed;
                    break;
                case UP:
                    tailY -= speed;
                    break;
                case DOWN:
                    tailY += speed;
                    break;
            }

            body.add(new Position(tailX, tailY));
        }
    }

    public void move() {
        // shift snake body except head
        if (body.size() > 1) {
            for (int index = body.size() - 1; index >= 1; index--) {
                body.set(index, body.get(index - 1));
            }
        }

        Position head = body.get(0);
        Integer headX = head.getX();
        Integer headY = head.getY();

        switch (movementDirection) {
            case RIGHT:
                headX += speed;
                break;
            case LEFT:
                headX -= speed;
                break;
            case UP:
                headY += speed;
                break;
            case DOWN:
                headY -= speed;
                break;
        }

        Position newHead = new Position(headX, headY);
        body.set(0, newHead);
    }
}
