package com.naganandakk.snake.domain;

import com.naganandakk.snake.exceptions.FoodNotFoundException;
import com.naganandakk.snake.exceptions.PositionOutOfBoundException;

import java.util.HashSet;
import java.util.Set;

public class Board {

    private final int width;
    private final int height;
    private final Set<Food> availableFood = new HashSet<>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Set<Food> getAvailableFood() {
        return availableFood;
    }

    public void addFood(Food food) {
        checkPositionForOutOfBoundary(food.getPosition());
        availableFood.add(food);
    }

    public void clearFood(Food food) {
        availableFood.remove(food);
    }

    public Food getFoodAtPosition(Position position) {
        for (Food food: availableFood) {
            if (position.equals(food.getPosition())) {
                return food;
            }
        }

        throw new FoodNotFoundException("Food not found at given position");
    }

    private void checkPositionForOutOfBoundary(Position position) {
        int posX = position.getX();
        int posY = position.getY();

        if ((posX < 0) || (posX > width) || (posY < 0) || (posY > height)) {
            throw new PositionOutOfBoundException("Out of bound coordinates");
        }
    }
}
