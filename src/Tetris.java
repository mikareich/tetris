import Game.*;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Tetris {
    private final JPanel gameCanvas = new JPanel();
    private final Game game = new Game(gameCanvas);
    private final JFrame window = new JFrame("tetris");

    public Tetris() {
        renderWindow(500, 250);
//        registerKeyboardListener();
//        game.addBlock(new Block(Shape.L()));
//        game.activeBlock.setPosition(5,5);
//        game.activeBlock.rotate();
//        game.update();
//        game.draw();
        registerKeyboardListener();
        game.start();
    }

    public static void main(String[] args) {
        new Tetris();
    }

    private void registerKeyboardListener() {
        NativeKeyListener keyListener = new NativeKeyListener() {
            public void nativeKeyPressed(NativeKeyEvent event) {
                String keyText = NativeKeyEvent.getKeyText(event.getKeyCode());
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(keyText.charAt(0));

                switch (keyCode) {
                    case KeyEvent.VK_A:
                        game.activeBlock.moveLeft();
                        break;

                    case KeyEvent.VK_D:
                        game.activeBlock.moveRight();
                        break;

                    case KeyEvent.VK_W:
                        game.activeBlock.rotate();
                        break;

                    case KeyEvent.VK_S:
                        game.activeBlock.drop();
                        break;
                }
            }
        };

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ignored) {
        }

        GlobalScreen.addNativeKeyListener(keyListener);
    }

    private void renderWindow(int width, int height) {
        window.setSize(width, height + 30);
        window.setVisible(true);
        window.setResizable(false);
        window.setFocusable(true);

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
