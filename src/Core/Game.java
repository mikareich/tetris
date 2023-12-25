package Core;

import javax.swing.*;
import java.util.Arrays;

public class Game {
    public static final int ROWS = 20;
    public static final int COLS = 20;
    public static final int TPS = 10;
    public final GameScreen screen;
    public Block[] blocks = {};
    public ActiveBlock activeBlock;

    public Game(JPanel panel) {
        this.screen = new GameScreen(panel, blocks);
    }

    public void pause() {
        gameLoop.pause();
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



    private final GameLoop gameLoop = new GameLoop(Game.TPS) {
        @Override
        public void execute() {
            update();
        }
    };
}
