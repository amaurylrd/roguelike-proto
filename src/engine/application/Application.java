package engine.application;

import engine.stage.Stage;

@SuppressWarnings("unchecked")
public abstract class Application implements Launchable {
	private Stage primaryStage = new Stage();

	public static void launch() {
		Application.launch(null);
	}

	public static void launch(String[] args) {
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		String classdName = Application.class.getName();
		System.out.println("Debug: Method "+methodName+" in "+classdName+" was called.");
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
						throw new RuntimeException("Error: "+caller+" is not a subclass of "+classdName+".");
				} catch (ClassNotFoundException exception) {
					throw new RuntimeException("Error: Failed to return the class object representing the "+classdName+" super-class.", exception);
				}
			}
		}
		if (!classFound)
			throw new RuntimeException("Error: Unable to determine application class.");
	}
	
	protected void setup() {
		System.out.println("Debug: The application reached setup functions.");
		init();
		onstart();
	}

	protected abstract void init();

	//auto show the window
	@Override
	public void onstart() {
		start(primaryStage);
		primaryStage.setVisible(true);
	}

	@Override
	public void onstop() {
		stop();
		primaryStage.dispose();
		System.exit(0);
	}
}