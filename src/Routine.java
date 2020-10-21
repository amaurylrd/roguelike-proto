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
                            stage.getScene().update(); //update
                            lastUpdateTime += updateTime;
                            updateCount++;
                        }

                        lastUpdateTime = currentTime - lastUpdateTime > updateTime ? currentTime - updateTime : lastUpdateTime;
                        float interpolation = Math.min(1f, (float) ((currentTime - lastUpdateTime)/updateTime));
                        
                        stage.getScene().render(interpolation); //draw
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
                            } catch (InterruptedException exception) {
                                throw new RuntimeException("Error: Fails to sleep, the main loop has been interrupted by another thread.", exception);
                            }
                            currentTime = System.nanoTime();
                        }
                    }
                }
            }
        });
    }

    public synchronized void start() {
        if (!running) {
            running = true;
            try {
                thread.start();
            } catch (IllegalThreadStateException exception) {
                throw new RuntimeException("Error: Occurs when trying to start a thread already running.", exception);
            }
        }
    }

    public synchronized void stop() {
        running = false;
        try {
			thread.join();
		} catch (InterruptedException exception) {
            throw new RuntimeException("Error: Fails to join, the main loop has been interrupted by another thread.", exception);
		}
    }

    public synchronized void pause() {
        pause = !pause;
    }
}