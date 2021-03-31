package com.engine.stage;

public interface Loopable extends Runnable {
    public final float TARGET_UPS = 60;
    public final float DELTA_TIME = 1000 / TARGET_UPS;
    public final float MAX_ACCUMULATOR = 5 * DELTA_TIME;
    
    public default void start() {
        Thread thread = new Thread(this, "game_loop_thread");
        thread.setDaemon(true);
        thread.start();
    }
}
