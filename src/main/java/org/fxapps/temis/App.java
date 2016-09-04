package org.fxapps.temis;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Temis JavaFX Client");
		stage.setWidth(200);
		stage.setHeight(200);
		stage.show();
	}

}
