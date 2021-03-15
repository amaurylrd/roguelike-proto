package com.engine.application;

import com.engine.stage.Stage;

public interface Launchable {
    public void onstart();

    public void start(Stage primaryStage);

    public void onstop();

    public void stop();
}