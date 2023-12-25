package Core;

enum GameLoopStatus {
  IDLE,
  RUNNING,
  PAUSED,
  STOPPED
}

public abstract class GameLoop {
  public final int fps;
  private GameLoopStatus status = GameLoopStatus.IDLE;
  private final Runnable runnable =
      new Runnable() {
        @Override
        public void run() {
          if (status == GameLoopStatus.STOPPED) return;

          long deltaTime = 1000 /  fps;

          try {
            Thread.sleep(deltaTime);
          } catch (Exception ignored) {
            return;
          }

          execute();
          run();
        }
      };
  private final Thread thread = new Thread(runnable, "GameLoop");

    protected GameLoop(int fps) {
        this.fps = fps;
    }

    public abstract void execute();

  public void pause() {
    status = GameLoopStatus.PAUSED;
  }

  public void stop() {
    status = GameLoopStatus.STOPPED;
  }

  public void start() {
    if (status == GameLoopStatus.RUNNING) return;
    status = GameLoopStatus.RUNNING;
    thread.start();
  }
}
