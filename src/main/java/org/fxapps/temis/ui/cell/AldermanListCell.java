package org.fxapps.temis.ui.cell;

import org.fxapps.temis.model.Alderman;
import org.fxapps.temis.ui.cache.ImageCache;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AldermanListCell extends ListCell<Alderman> {

	@Override
	protected void updateItem(Alderman item, boolean empty) {
		super.updateItem(item, empty);
		Image image = ImageCache.noPhoto();
		if (item == null) {
			setGraphic(photo(image));
			setText("");
			return;
		}
		if (item.getPhoto() != null) {
			image = ImageCache.saveImageIfAbsent(item.getEmail(), item.getPhoto());
		}
		setGraphic(photo(image));
		setText(item.getName());
		setGraphicTextGap(10);
		setAlignment(Pos.CENTER);
		setContentDisplay(ContentDisplay.TOP);
		setPrefWidth(200);
	}

	static final ImageView photo(Image image) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(120);
		imageView.setFitHeight(150);
		imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,255,0.8), 30, 0, 0, 0);");
		return imageView;
	}

}
