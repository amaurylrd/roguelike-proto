import java.io.PrintStream;

import engine.stage.Stage;

public class Routine {
    private Thread thread;
    private volatile boolean running = false;
    private volatile boolean pause = false;

    public Routine(Stage stage) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int fps = 60, ups = 60; 
                long updateTime = 1000000000/ups;
                long renderTime = 1000000000/fps;

                int maxUpdates = 5;
                long lastUpdateTime = System.nanoTime();
                long lastRenderTime = System.nanoTime();
                int lastSecondTime = (int) (lastUpdateTime/1000000000);
        
                int updateCount, frameCount = 0;
                while (running) {
                    updateCount = 0;
                    long currentTime = System.nanoTime();
                    
                    if (!pause) {
                        while (currentTime - lastUpdateTime > updateTime && updateCount < maxUpdates) {
                            //System.out.println("update");
                            //TODO: stage.update();
                            lastUpdateTime += updateTime;
                            updateCount++;
                        }

                        lastUpdateTime = currentTime - lastUpdateTime > updateTime ? currentTime - updateTime : lastUpdateTime;
                        float interpolation = Math.min(1f, (float) ((currentTime - lastUpdateTime)/updateTime));
                        
                        //System.out.println("render");
                        //TODO: stage.render(interpolation);
                        frameCount++;
                        lastRenderTime = currentTime;

                        System.out.println("fps:"+fps);
                        int currentSecond = (int) (lastUpdateTime/1000000000);
                        if (currentSecond > lastSecondTime) {
                            fps = frameCount;
                            frameCount = 0;
                            lastSecondTime = currentSecond;
                        }

                        while (currentTime - lastRenderTime < renderTime && currentTime - lastUpdateTime < updateTime) {
                            Thread.yield();
                            try {
                                Thread.sleep(1);
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace(new PrintStream(System.err));
                            }
                            currentTime = System.nanoTime();
                        }
                    }
                }
            }
        });
    }

    public synchronized void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace(new PrintStream(System.err));
		}
    }

    public synchronized void pause() {
        pause = !pause;
    }
}