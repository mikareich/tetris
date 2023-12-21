package Game;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    public static final int ROWS = 20;
    public static final int COLS = 20;
    public static final int FPS = 1;
    private final JPanel gameCanvas;
    private final Timer timer = new Timer();
    private final int speedFactor = 1;
    public Block[] blocks = {};
    public Block activeBlock;
    private int score;
    private GameStatus status = GameStatus.IDLE;

    public Game(JPanel gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public int getScore() {
        return score;
    }

    public void start() {
        if (status == GameStatus.RUNNING) return;
        status = GameStatus.RUNNING;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
                draw();
            }
        }, 0, 1000 / FPS);
    }

    private void update() {
        if (blocks.length == 0) addBlock(Shape.Random());

        activeBlock.move(0, 1, blocks);
    }

    public void addBlock(Shape shape) {
        Block newBlock = new Block(shape);

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

        int scaleX = width / COLS;
        int scaleY = height / ROWS;

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

        // draw tetris blocks
        for (Block block : blocks) {
            block.draw(graphics, scaleFactors[0], scaleFactors[1]);
        }
    }
}
