package Game;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Game {
    public static final int ROWS = 30;
    public static final int COLS = 15;
    public static final int FPS = 5;
    private final JPanel gameCanvas;
    public Block[] blocks = {};
    public ActiveBlock activeBlock;

    public Game(JPanel gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public void start() {
        gameLoop.start();
    }

    public void update() {
        if (blocks.length == 0) addBlock(null);

        if (!activeBlock.movable(0, 0, blocks)) {
            // game over
            System.out.println("game over");
            gameLoop.stop();
            return;
        }

        activeBlock.moveDown();
        activeBlock.update();

        if (!activeBlock.movable(0, 1, blocks)) {
            // downgrade current active block
            Collider.removeFullRows(blocks);
            addBlock(null);
        }
    }

    public void addBlock(Block block) {
        if (blocks.length > 0) blocks[blocks.length - 1] = activeBlock.copy();

        ActiveBlock newBlock;

        if (block == null) {
            newBlock = new ActiveBlock(Shape.Random(), this);
        } else {
            newBlock = block.toActiveBlock(this);
        }

        final int N = blocks.length;
        blocks = Arrays.copyOf(blocks, N + 1);
        blocks[N] = newBlock;

        activeBlock = newBlock;
    }

    public void clear() {
        gameCanvas.requestFocusInWindow();
        Graphics graphics = gameCanvas.getGraphics();
        int width = gameCanvas.getWidth();
        int height = gameCanvas.getHeight();

        graphics.clearRect(0, 0, width, height);

        int[] scaleFactors = getScaleFactors();

        int scaleX = scaleFactors[0];
        int scaleY = scaleFactors[1];

        // draw grid
        graphics.setColor(Color.BLACK);
        for (int col = 0; col <= COLS; col++) {
            graphics.drawLine(col * scaleX, 0, col * scaleX, height);
        }
        for (int row = 0; row <= ROWS; row++) {
            graphics.drawLine(0, row * scaleY, width, row * scaleY);
        }
    }

    private int[] getScaleFactors() {
        int width = gameCanvas.getWidth();
        int height = gameCanvas.getHeight();

        int scaleX = width / COLS;
        int scaleY = height / ROWS;

        return new int[]{scaleX, scaleY};
    }

    public void draw() {
        this.clear();

        gameCanvas.requestFocusInWindow();
        Graphics graphics = gameCanvas.getGraphics();

        int[] scaleFactors = getScaleFactors();

        int scaleX = scaleFactors[0];
        int scaleY = scaleFactors[1];

        // draw tetris blocks
        for (Block block : blocks) {
            block.draw(graphics, scaleX, scaleY);
        }
    }

    private final GameLoop gameLoop = new GameLoop() {
        @Override
        public void execute() {
            update();
            draw();
        }
    };
}
