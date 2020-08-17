package com.naganandakk.snake;

import com.naganandakk.snake.domain.Board;
import com.naganandakk.snake.domain.Position;
import com.naganandakk.snake.domain.Snake;
import com.naganandakk.snake.enums.Direction;

import javax.swing.JFrame;
import java.awt.EventQueue;4
import javax.swing.WindowConstants;

public class Window extends JFrame {

    public Window() {
        startGame();
    }

    private void startGame() {
        Board board = new Board(500, 500);
        Snake snake = new Snake(
                new Position(0, 0),
                Direction.UP,
                10
        );
        add(new Game(board, snake));
        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new Window();
            frame.setVisible(true);
        });
    }
}
