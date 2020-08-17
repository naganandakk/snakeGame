package com.naganandakk.snake;

import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Timer;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import com.naganandakk.snake.domain.Board;
import com.naganandakk.snake.domain.Food;
import com.naganandakk.snake.domain.Position;
import com.naganandakk.snake.domain.Snake;
import com.naganandakk.snake.enums.Direction;
import com.naganandakk.snake.exceptions.FoodNotFoundException;
import com.naganandakk.snake.exceptions.PositionOutOfBoundException;


public class Game extends JPanel implements ActionListener {

    private static final int POSITION_PADDING = 20;
    private static final int TIMER_DELAY = 1000;
    private static final Font fontSmall = new Font("Helvetica", Font.BOLD, 14);
    private static final Font fontMedium = new Font("Helvetica", Font.BOLD, 18);
    private final transient Board board;
    private final transient Snake snake;
    private int score;
    private boolean aborted = false;
    private Timer timer;
    private transient Graphics graphics;

    private Random random;

    public Game(Board board, Snake snake) {
        this.board = board;
        this.snake = snake;
        this.score = 0;
        init();
        start();
    }

    private void init() {
        initRandomGenerator();
        addWindowEventHandlers();
        addKeyListener(new GameKeyAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(board.getWidth(), board.getHeight()));
        addFoodToBoard();
    }

    private void initRandomGenerator() {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
        }
    }

    private void addWindowEventHandlers() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                board.setWidth(getWidth());
                board.setHeight(getHeight());
            }
        });
    }

    private void addFoodToBoard() {
        int x = Math.max(
                random.nextInt(board.getWidth() - POSITION_PADDING),
                POSITION_PADDING
        );
        int y = Math.max(
                random.nextInt(board.getHeight() - POSITION_PADDING),
                POSITION_PADDING
        );

        x = x - (x % snake.getSpeed());
        y = y - (y % snake.getSpeed());

        board.addFood(new Food(new Position(x, y)));
    }

    private void start() {
        timer = new Timer(TIMER_DELAY / snake.getSpeed(), this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        graphics = g;

        if (continueGame()) {
            displayStats();
            drawSnake();
            drawFood();

            Toolkit.getDefaultToolkit().sync();
        } else {
            endGame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (continueGame()) {
            feedFoodToSnake();
            snake.move();
        }
        repaint();
    }



    private boolean continueGame() {
        boolean snakeInsideBoard = true;
        try {
            board.checkPositionForOutOfBoundary(snake.head());
        } catch (PositionOutOfBoundException e) {
            e.printStackTrace();
            snakeInsideBoard = false;
        }

        return snakeInsideBoard && (!aborted) && (!snake.crossedSelf());
    }

    private void displayStats() {
        String stats = "Score : " + score;

        int x = POSITION_PADDING / 2;
        int y = POSITION_PADDING / 2;
        drawString(stats, x, y, Color.green, fontSmall);
    }

    private void drawSnake() {
        drawSnakeHead();
        drawSnakeBody();
    }

    private void drawSnakeHead() {
        Position snakeHeadPosition = snake.getBody().get(0);
        int x = snakeHeadPosition.getX() + POSITION_PADDING;
        int y = snakeHeadPosition.getY() + POSITION_PADDING;
        drawString("*", x, y, Color.white, fontMedium);
    }

    private void drawSnakeBody() {
        for (Position position : snake.getBody().subList(1, snake.getBody().size())) {
            int x = position.getX() + POSITION_PADDING;
            int y = position.getY() + POSITION_PADDING;
            drawString("*", x, y, Color.green, fontSmall);
        }
    }

    private void drawFood() {
        for (Food food : board.getAvailableFood()) {
            int x = food.getPosition().getX() + POSITION_PADDING;
            int y = food.getPosition().getY() + POSITION_PADDING;
            drawString("*", x, y, Color.red, fontMedium);
        }
    }

    private void drawString(String message, int x, int y, Color color, Font font) {
        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString(message, x, y);
    }

    private void feedFoodToSnake() {
        try {
            Food foodAtPosition = board.getFoodAtPosition(snake.head());
            if (foodAtPosition != null) {
                snake.eat(1);
                board.clearFood(foodAtPosition);
                score += 1;
                addFoodToBoard();
            }
        }
        catch (FoodNotFoundException ignored) {
            // Ignore
        }
    }

    private void endGame() {
        FontMetrics metrics = getFontMetrics(fontSmall);

        String gameOverMsg = "Game Over";
        String scoreMsg = "Score : " + score;

        int gameOverX = (board.getWidth() - metrics.stringWidth(gameOverMsg)) / 2;
        int gameOverY = board.getHeight() / 2;
        drawString(gameOverMsg, gameOverX, gameOverY, Color.white, fontSmall);

        int scoreX = (board.getWidth() - metrics.stringWidth(scoreMsg)) / 2;
        int scoreY = gameOverY + 20;
        drawString(scoreMsg, scoreX, scoreY, Color.white, fontSmall);

        timer.stop();
    }

    private class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            int key = event.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    snake.changeMovementDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    snake.changeMovementDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    // Y-axis 0 starts from top
                    snake.changeMovementDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_DOWN:
                    // Y-axis max value ends at bottom of screen
                    snake.changeMovementDirection(Direction.UP);
                    break;
                case KeyEvent.VK_P:
                    timer.stop();
                    break;
                case KeyEvent.VK_R:
                    timer.restart();
                    break;
                case KeyEvent.VK_Q:
                    aborted = true;
                    break;
                default:
                    break;
            }
        }
    }
}