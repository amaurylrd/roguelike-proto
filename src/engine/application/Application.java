package engine.application;

import engine.stage.Stage;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("unchecked")
public abstract class Application implements Launchable {
	private Stage primaryStage = Stage.create();

	public static void launch() {
		Application.launch(null);
	}

	
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
	
	protected void setup() {
		Plateform.trace("Debug: The application reached setup functions.");
		Ressource.preload();
		init();
		onstart();
	}

	protected abstract void init();

	@Override
	public void onstart() {
		primaryStage.requestFocusInWindow();
		primaryStage.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				onstop();
			}
		});
		start(primaryStage);
		primaryStage.pack();
		primaryStage.setVisible(true);
	}

	@Override
	public void onstop() {
		stop();
		primaryStage.setVisible(false);
		//primaryStage.save();
		primaryStage.dispose();
		System.exit(0);
	}
}