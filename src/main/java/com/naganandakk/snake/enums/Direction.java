package com.naganandakk.snake.enums;

public enum Direction {
    LEFT {
        @Override
        public Direction opposite() {
            return RIGHT;
        }
    },
    RIGHT {
        @Override
        public Direction opposite() {
            return LEFT;
        }
    },
    UP {
        @Override
        public Direction opposite() {
            return DOWN;
        }
    },
    DOWN {
        @Override
        public Direction opposite() {
            return UP;
        }
    };

    public abstract Direction opposite();
}
