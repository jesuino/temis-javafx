package org.fxapps.temis;

import org.fxapps.temis.ui.TemisClientUI;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class App extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Têmis JavaFX Client");
		ProgressIndicator loadingSomething = new ProgressIndicator();
		loadingSomething.setMaxSize(200, 200);
		loadingSomething.setVisible(false);
		BooleanProperty visibleProperty = loadingSomething.visibleProperty();
		Label lblTitle = new Label("Temis - Leis dos vereadores de São José dos Campos");
		lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		lblTitle.setEffect(new javafx.scene.effect.DropShadow(2, Color.DARKBLUE));
		lblTitle.setTextFill(Color.BLUE);
		VBox root = new VBox(10, lblTitle, new TemisClientUI(visibleProperty));
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(new StackPane(root, loadingSomething));
		
		stage.setScene(scene);
		stage.setWidth(1200);
		stage.setHeight(900);
		stage.show();
	}

}
