package org.fxapps.temis;

import org.fxapps.temis.ui.TemisClientUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Temis JavaFX Client");
		Scene scene = new Scene(new TemisClientUI());
		stage.setScene(scene);
		stage.setWidth(800);
		stage.setHeight(600);
		stage.show();
	}

}
