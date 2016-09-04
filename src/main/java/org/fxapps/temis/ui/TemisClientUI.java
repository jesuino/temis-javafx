package org.fxapps.temis.ui;

import org.fxapps.temis.model.Alderman;
import org.fxapps.temis.service.TemisClientService;
import org.fxapps.temis.ui.cell.AldermanListCell;

import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class TemisClientUI extends StackPane {
	
	private final static String BASE_URL = "http://temis-server.herokuapp.com/";

	private TemisClientService service = TemisClientService.get(BASE_URL);
	
	ListView<Alderman> aldermenList;

	public TemisClientUI() {
		super();
		buildAll();
	}

	private void buildAll() {
		// build each node of the app view
		buildAldermenList();
		
		// add to the pane - soon we will control the navigation
		getChildren().add(aldermenList);
		
	}

	private void buildAldermenList() {
		aldermenList = new ListView<>();
		aldermenList.setCellFactory(param -> new AldermanListCell());
		aldermenList.getItems().setAll(service.aldermen());
	}
	
	
	
}
