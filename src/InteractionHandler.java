import Core.Game;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.event.KeyEvent;
import java.util.HashMap;

enum Interaction {
    StartGame, PauseGame, MoveLeft, MoveRight, Drop, Rotate
}

public class InteractionHandler {
    private final HashMap<Interaction, Runnable> interactionMap = new HashMap<>();

    private final Game game;

    public InteractionHandler(Game game) {
        this.game = game;

        // set up interaction references for game
        interactionMap.put(Interaction.StartGame, this::startGame);
        interactionMap.put(Interaction.PauseGame, this::pauseGame);
        // ... for the active block
        interactionMap.put(Interaction.MoveLeft, this::moveLeft);
        interactionMap.put(Interaction.MoveRight, this::moveRight);
        interactionMap.put(Interaction.Drop, this::drop);
        interactionMap.put(Interaction.Rotate, this::rotate);

        registerKeyboardListener();
    }

    private void startGame() {
        game.start();
    }

    private void pauseGame() {
        game.pause();
    }

    private void moveLeft() {
        game.activeBlock.moveLeft();
    }

    private void moveRight() {
        System.out.println("move right");
        game.activeBlock.moveRight();
    }

    private void drop() {
        System.out.println("dropping");
        game.activeBlock.drop();
    }

    private void rotate() {
        game.activeBlock.rotate();
    }


    public void dispatch(Interaction interaction) {
        if (interactionMap.containsKey(interaction)) {
            interactionMap.get(interaction).run();
        }
    }

    private void registerKeyboardListener() {
        NativeKeyListener keyListener = new NativeKeyListener() {
            public void nativeKeyPressed(NativeKeyEvent event) {
                String keyText = NativeKeyEvent.getKeyText(event.getKeyCode());
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(keyText.charAt(0));

                switch (keyCode) {
                    case KeyEvent.VK_A:
                        dispatch(Interaction.MoveLeft);
                        break;

                    case KeyEvent.VK_D:
                        dispatch(Interaction.MoveRight);
                        break;

                    case KeyEvent.VK_W:
                        dispatch(Interaction.Rotate);
                        break;

                    case KeyEvent.VK_S:
                        dispatch(Interaction.Drop);
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
}
