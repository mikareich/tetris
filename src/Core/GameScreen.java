package Core;

import javax.swing.*;
import java.awt.*;

class GameScreen {
    public final JPanel screen;
    public final int FPS = 30;
    private final Graphics graphics;
    private final int width;
    private final int height;
    private final int scalingX;
    private final int scalingY;
    private final Block[] blocks;
    private final GameLoop gameLoop = new GameLoop(FPS) {
        @Override
        public void execute() {
            draw();
        }
    };

    public GameScreen(JPanel screen, Block[] blocks) {
        this.screen = screen;
        this.blocks = blocks;

        graphics = screen.getGraphics();

        width = screen.getWidth();
        height = screen.getHeight();

        scalingX = width / Game.COLS;
        scalingY = height / Game.ROWS;

        gameLoop.start();
    }

    public void clear() {
        screen.requestFocusInWindow();
        graphics.clearRect(0, 0, width, height);


        // draw grid
        graphics.setColor(Color.BLACK);
        for (int col = 0; col < Game.COLS; col++) {
            graphics.drawLine(col * scalingX, 0, col * scalingX, height);
        }

        for (int row = 0; row < Game.ROWS; row++) {
            graphics.drawLine(0, row * scalingY, width, row * scalingY);
        }

        graphics.drawLine(width - 1, 0, width - 1, height);
        graphics.drawLine(0, height - 1, width, height - 1);
    }

    public void draw() {
        clear();

        // draw tetris blocks
        for (Block block : blocks) {
            graphics.setColor(block.color);
            for (int[] edge : block.shape.getAbsoluteShape(block.position)) {
                int x = edge[0] * scalingX;
                int y = edge[1] * scalingY;
                graphics.fillRect(x, y, scalingX, scalingY);
            }
        }
    }
}
