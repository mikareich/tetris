import Core.Game;

class Tetris {
    public static void main(String[] args) {
        Window window = new Window();
        Game game = new Game(window.gameScreen);
        InteractionHandler interactionHandler = new InteractionHandler(game);

        window.renderGameScreen();
        window.renderControls(interactionHandler);
    }
}
