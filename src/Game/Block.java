package Game;

import java.awt.*;

public class Block {
    public final Shape shape;
    private final Color color = Color.BLACK;
    public int[] position = {2, 2};

    public Block(Shape shape) {
        this.shape = shape;
    }

    // draws block on canvas
    public void draw(Graphics graphics, int scaleX, int scaleY) {
        graphics.setColor(color);
        for (int[] edge : shape.edges) {
            int x = (edge[0] + position[0]) * scaleX;
            int y = (edge[1] + position[1]) * scaleY;
            graphics.fillRect(x, y, scaleX, scaleY);
        }
    }

    public void setPosition(int x, int y) {
        position[0] = x;
        position[1] = y;
    }

    public void move(int x, int y, Block[] blocks) {
        int newX = position[0] + x;
        int newY = position[1] + y;

        // check if possible
        Block blockCopy = new Block(this.shape);
        blockCopy.setPosition(newX, newY);

        if (Collider.withGameBorder(blockCopy)) return;
        System.out.println(newY);
        for (Block block : blocks) {
            if (Collider.isCollided(blockCopy, block)) return;
        }

        System.out.println("set new position");
        setPosition(newX, newY);
    }
}