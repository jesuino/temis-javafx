package org.fxapps.temis.ui;

import java.util.List;

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
		Task<List<Law>> loadLawsTask = new Task<List<Law>>() {

			@Override
			protected List<Law> call() throws Exception {
				loadingProperty.set(true);
				return service.laws(alderman, 0, 10000);
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				loadingProperty.set(false);
				fadeCenterPane();
				List<Law> laws = this.getValue();
				listLaws.getItems().setAll(laws);
				lblTitleLaw.setText("Foram " + laws.size() + " leis votadas ou criados por " + alderman.getName());
				listLaws.getSelectionModel().select(0);
			}

			@Override
			protected void failed() {
				loadingProperty.set(false);
				// TODO do something when it fails..
				super.failed();
			}

			@Override
			protected void done() {
				loadingProperty.set(false);
				super.done();
			}
		};

		new Thread(loadLawsTask).start();

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
		Task<List<Alderman>> loadAldermenTask = new Task<List<Alderman>>() {

			@Override
			protected List<Alderman> call() throws Exception {
				loadingProperty.set(true);
				return service.aldermen();
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				loadingProperty.set(false);
				fadeCenterPane();
				aldermenList.getItems().setAll(this.getValue());
				aldermenList.getItems().stream().filter(a -> a.getName().equals("Mesa Diretora")).findFirst().ifPresent(a -> {
					aldermenList.getSelectionModel().select(a);
					aldermenList.scrollTo(a);
				});
			}

			@Override
			protected void failed() {
				loadingProperty.set(false);
				// TODO do something when it fails..
				super.failed();
			}

			@Override
			protected void done() {
				loadingProperty.set(false);
				super.done();
			}
		};

		new Thread(loadAldermenTask).start();
	}

}
