import javax.swing.*;

public class Window extends JFrame {
    public final static int PADDING = 10;
    public final static int WIDTH = 500;
    public final static int HEIGHT = 300;
    public final JPanel gameScreen = new JPanel();

    public Window() {
        setTitle("Tetris");
        setSize(WIDTH, HEIGHT + 80);
        setResizable(false);
        setVisible(true);
    }

    public void renderGameScreen() {
        requestFocusInWindow();
        int canvasWidth = WIDTH / 2 - 2 * PADDING;
        int canvasHeight = HEIGHT - 2 * PADDING;
        if ((WIDTH / 2) > HEIGHT) {
            canvasHeight = canvasWidth;
        } else if ((WIDTH / 2) < HEIGHT) {
            canvasWidth = canvasHeight;
        }

        System.out.printf("w: %d, h: %d", canvasWidth, canvasHeight);

        gameScreen.setBounds(PADDING, PADDING, canvasWidth, canvasHeight);
        add(gameScreen);
    }

    public void renderControls(InteractionHandler interactionHandler) {
        requestFocusInWindow();
        JButton AddNewStoneButton = new JButton();
        AddNewStoneButton.setText("Start");
        AddNewStoneButton.setBounds(gameScreen.getWidth() + 2 * PADDING, PADDING, 100, 40);
        AddNewStoneButton.addActionListener((event) -> {
            interactionHandler.dispatch(Interaction.StartGame);
        });
        add(AddNewStoneButton);
    }
}
