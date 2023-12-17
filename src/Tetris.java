import Game.*;
import Game.Shape;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Tetris {
    private final JPanel gameCanvas = new JPanel();
    private final Game game = new Game(gameCanvas);
    private final JFrame window = new JFrame("tetris");

    public Tetris() {
        renderWindow(500, 250);
        game.addBlock(Shape.Random());
        game.draw();
        System.out.println(Collider.withGameBorder(game.activeBlock));
//        game.start();
    }

    public static void main(String[] args) {
        new Tetris();
    }

    private void renderWindow(int width, int height) {
        window.setSize(width, height);
        window.setVisible(true);
        window.setResizable(false);

        final int padding = 5;

        // add game screen
        int canvasWidth = width / 2 - 2 * padding;
        int canvasHeight = height - 2 * padding;
        if ((width / 2) > height) {
            canvasHeight = canvasWidth;
        } else if ((width / 2) < height) {
            canvasWidth = canvasHeight;
        }

        gameCanvas.setBounds(padding, padding, canvasWidth, canvasHeight);
        window.add(gameCanvas);

        // add controls
        JButton AddNewStoneButton = new JButton();
        AddNewStoneButton.setText("Start");
        AddNewStoneButton.setBounds(canvasWidth + 2 * padding, padding, 100, 40);
        AddNewStoneButton.addActionListener((event) -> {
            game.start();
        });
        window.add(AddNewStoneButton);

        window.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                AddNewStoneButton.requestFocusInWindow();
            }
        });
    }
}