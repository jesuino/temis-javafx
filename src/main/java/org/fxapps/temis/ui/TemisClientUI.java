package org.fxapps.temis.ui;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.fxapps.temis.model.Alderman;
import org.fxapps.temis.model.Law;
import org.fxapps.temis.service.TemisClientService;
import org.fxapps.temis.ui.cell.AldermanListCell;

import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class TemisClientUI extends BorderPane {

	private final static String BASE_URL = "http://temis-server.herokuapp.com/";

	private TemisClientService service = TemisClientService.get(BASE_URL);

	ListView<Alderman> aldermenList;

	private ListView<Law> listLaws;

	private Label lblTitleLaw;

	private BooleanProperty loadingProperty;

	public TemisClientUI(BooleanProperty loadingSomething) {
		super();
		this.loadingProperty = loadingSomething;
		this.disableProperty().bind(loadingSomething);
		buildAll();
	}

	private void buildAll() {
		buildLawsList();
		buildAldermenList();
		setTop(aldermenList);
	}

	private void buildLawsList() {
		lblTitleLaw = new Label();
		listLaws = new ListView<>();
		listLaws.setPrefWidth(100);
		WebView lawText = new WebView();
		listLaws.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Law>() {

			@Override
			public void changed(ObservableValue<? extends Law> observable, Law oldValue, Law newValue) {
				if (newValue != null && newValue.getDesc() != null) {
					lawText.getEngine().loadContent(newValue.getDesc());
				}
			}
		});
		SplitPane paneLaws = new SplitPane(listLaws, lawText);
		VBox vbCenter = new VBox(paneLaws, lblTitleLaw);
		setCenter(vbCenter);
	}

	private void buildAldermenList() {
		aldermenList = new ListView<>();
		aldermenList.setCursor(Cursor.HAND);
		aldermenList.setOrientation(Orientation.HORIZONTAL);
		aldermenList.setCellFactory(param -> new AldermanListCell());
		aldermenList.setPrefHeight(220);
		aldermenList.getSelectionModel().selectedItemProperty().addListener((InvalidationListener) observable -> {
			Alderman alderman = aldermenList.getSelectionModel().getSelectedItem();
			loadLaws(alderman);
		});
		loadAldermen();
	}


	private void loadLaws(Alderman alderman) {
		doAsyncWork(() -> service.laws(alderman, 0, 10000), laws -> {
			fadeCenterPane();
			listLaws.getItems().setAll(laws);
			lblTitleLaw.setText("Foram " + laws.size() + " leis votadas ou criados por " + alderman.getName());
			listLaws.getSelectionModel().select(0);
		});
	}
	
	private void fadeCenterPane(){
		FadeTransition ft = new FadeTransition(Duration.seconds(1));
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.setAutoReverse(true);
		ft.setNode(getCenter());
		ft.play();
	}
	
	private void loadAldermen() {
		doAsyncWork(service::aldermen, aldermen -> {
			loadingProperty.set(false);
			fadeCenterPane();
			aldermenList.getItems().setAll(aldermen);
			aldermenList.getItems().stream().filter(a -> a.getName().equals("Mesa Diretora")).findFirst().ifPresent(a -> {
				aldermenList.getSelectionModel().select(a);
				aldermenList.scrollTo(a);
			});
		});
	}
	
	
	public <T extends Object> void doAsyncWork(Supplier<T> action, Consumer<T> success) {
		Task<T> tarefaCargaPg = new Task<T>() {
			@Override
			protected T call() throws Exception {
				loadingProperty.set(true);
				return action.get();
			}

			@Override
			protected void succeeded() {
				loadingProperty.set(false);
				success.accept(getValue());
			}

			@Override
			protected void failed() {
				loadingProperty.set(false);
				getException().printStackTrace();
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setTitle("Error");
				dialog.setHeaderText(null);
				dialog.setResizable(true);
				dialog.setContentText("Error acessando Têmis. Verifique se o mesmo está disponível.");
				dialog.showAndWait();
			}
		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
	}

}
