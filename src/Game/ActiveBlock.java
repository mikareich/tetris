package Game;

public class ActiveBlock extends Block {
    private final Game game;
    public int[] speed = {0, 0, 0};

    public ActiveBlock(Shape shape, Game game) {
        super(shape);
        this.game = game;
    }

    public void update() {
        // split move x and y to check for collision independently
        move(speed[0], 0, game.blocks);
        move(0, speed[1], game.blocks);
        if (speed[2] == 1) rotate(game.blocks);

        // reset speed
        speed = new int[]{0, 0, 0};
    }

    public void moveLeft() {
        speed[0] = -1;
    }

    public void moveRight() {
        speed[0] = 1;
    }

    public void moveDown() {
        speed[1] = 1;
    }

    public void drop() {
        while (movable(0, 1, game.blocks)) {
            move(0, 1, game.blocks);
        }
    }

    public void rotate() {
        speed[2] = 1;
    }
}
