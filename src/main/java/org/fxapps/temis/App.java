package org.fxapps.temis;

import org.fxapps.temis.ui.TemisClientUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class App extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Temis JavaFX Client");

		Label lblTitle = new Label("Temis - Leis dos vereadores de São José dos Campos");
		lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		VBox root = new VBox(10, lblTitle, new TemisClientUI());
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setWidth(1200);
		stage.setHeight(900);
		stage.show();
	}

}
