package com.naganandakk.snake.domain;

import java.util.*;

public class Snake {
    private int posX;
    private int posY;
    private String movementDirection;
    private int speed;
    private List<List<Integer>> body;

    public Snake(int posX, int posY, String movementDirection, int speed) {
        this.posX = posX;
        this.posY = posY;
        this.movementDirection = movementDirection;
        this.speed = speed;
        this.body = new LinkedList<>();
        this.body.add(Arrays.asList(posX, posY));
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getMovementDirection() {
        return movementDirection;
    }

    public int getSpeed() {
        return speed;
    }

    public List<List<Integer>> getBody() {
        return body;
    }

    public void changeMovementDirection(String changeTowards) {
        Map<String, String> oppositeDirections = new HashMap<>();
        oppositeDirections.put("LEFT", "RIGHT");
        oppositeDirections.put("RIGHT", "LEFT");
        oppositeDirections.put("UP", "DOWN");
        oppositeDirections.put("DOWN", "UP");

        String oppositeDirection = oppositeDirections.getOrDefault(changeTowards, "");

        if (oppositeDirection != movementDirection) {
            movementDirection = changeTowards;
        }
    }

    public void eat(int foodQuantity) {
        while(foodQuantity-- > 0) {
            body.add(Arrays.asList(0, 0));
        }
    }
}
