package Game;

import java.awt.*;
import java.util.UUID;

public class Block {
    public final Shape shape;
    private final Color color = Color.BLACK;
    public int[] position = {0, 0};
    public UUID id = UUID.randomUUID();

    public Block(Shape shape) {
        this.shape = shape;
    }

    public Block copy() {
        Block newBlock = new Block(shape);
        newBlock.setPosition(position[0], position[1]);
        newBlock.id = this.id;

        return newBlock;
    }

    // draws block on canvas
    public void draw(Graphics graphics, int scaleX, int scaleY) {
        graphics.setColor(color);
        for (int[] edge : shape.getAbsoluteShape(position)) {
            int x = edge[0] * scaleX;
            int y = edge[1] * scaleY;
            graphics.fillRect(x, y, scaleX, scaleY);
        }
    }

    public void setPosition(int x, int y) {
        position[0] = x;
        position[1] = y;
    }

    private boolean collided(Block[] blocks) {
        if (Collider.withGameBorder(this)) return true;
        for (Block block : blocks) {
            if (Collider.isCollided(this, block) && !block.id.equals(this.id)) return true;
        }

        return false;
    }

    public boolean rotatable(Block[] blocks) {
        // check if possible
        Block blockCopy = copy();
        blockCopy.shape.rotate();

        return !blockCopy.collided(blocks);
    }

    public void rotate(Block[] blocks) {
        if (!rotatable(blocks)) return;

        this.shape.rotate();
    }

    public boolean movable(int x, int y, Block[] blocks) {
        int newX = position[0] + x;
        int newY = position[1] + y;

        Block blockCopy = copy();
        blockCopy.setPosition(newX, newY);

        return !blockCopy.collided(blocks);
    }


    public void move(int x, int y, Block[] blocks) {
        if (!movable(x, y, blocks)) {
            return;
        }

        int newX = position[0] + x;
        int newY = position[1] + y;

        setPosition(newX, newY);
    }

    public ActiveBlock toActiveBlock(Game game) {
        ActiveBlock activeBlock = new ActiveBlock(shape, game);
        activeBlock.position = position;
        activeBlock.id = this.id;

        return activeBlock;
    }
}
