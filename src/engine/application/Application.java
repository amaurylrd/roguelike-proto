package engine.application;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import engine.stage.Stage;

@SuppressWarnings("unchecked")
public abstract class Application implements Launchable {
	/**
	 * The stage of this {@code Application}.
	 */
	private Stage primaryStage = Stage.create();

	/**
	 * Calls the method launch with no arguments.
	 * 
	 * @see launch(String[] args)
	 */
	public static void launch() {
		Application.launch(null);
	}

	/**
	 * Instanciates the main thread and calls the method launchApplication of class
	 * {@code Plateform}.
	 * 
	 * @param args the command-line arguments
	 */
	public static void launch(String[] args) {
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		String classdName = Application.class.getName();
		Plateform.trace("Debug: Method " + methodName + " in " + classdName + " was called.");

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		boolean classFound = false;
		for (int i = 0; i < stackTrace.length || !classFound; ++i) {
			StackTraceElement e = stackTrace[i];
			if (classFound = e.getClassName().equals(classdName) && e.getMethodName().equals(methodName)) {
				try {
					Class<?> caller = Class.forName(stackTrace[++i].getClassName(), false, Thread.currentThread().getContextClassLoader());
					if (Application.class.isAssignableFrom(caller))
						Plateform.launchApplication((Class<? extends Application>) caller, args);
					else
						throw new RuntimeException("Error: " + caller + " is not a subclass of " + classdName + ".");
				} catch (ClassNotFoundException exception) {
					throw new RuntimeException("Error: Failed to return the class object representing the " + classdName + " super-class.", exception);
				}
			}
		}
		if (!classFound)
			throw new RuntimeException("Error: Unable to determine application class.");
	}

	/**
	 * This method is called by {@code Plateform} to initiliaze the
	 * {@code Application}. This is also where are done preloads.
	 * 
	 * @see init
	 * @see onstart
	 */
	protected void setup() {
		Plateform.trace("Debug: The application reached setup functions.");
		Properties.load();
		Ressources.preload();
		init();
		onstart();
	}

	/**
	 * This is the first method to be called to setup the {@code Application}.
	 */
	protected abstract void init();

	/**
	 * This method is called right after init to start and show the {@code stage}.
	 */
	@Override
	public void onstart() {
		primaryStage.setTitle(Properties.property("project.name"));
		primaryStage.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				onstop();
			}
		});
		start(primaryStage);
		primaryStage.start();
		Plateform.trace("Debug: The game loop starts now.");
		primaryStage.pack();
		primaryStage.requestFocusInWindow();
		primaryStage.requestFocus();
		primaryStage.toFront();
		primaryStage.setVisible(true);
		Plateform.trace("Debug: The frame is now visible.");
	}

	/**
	 * This method is called to exit the {@code Application} and release the
	 * {@code Stage}. This is also where the game state is serialized.
	 */
	@Override
	public void onstop() {
		stop();
		primaryStage.setVisible(false);
		Properties.save();
		// primaryStage.save();
		primaryStage.dispose();
		System.exit(0);
	}
}