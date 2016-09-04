package org.fxapps.temis.ui.cell;

import org.fxapps.temis.model.Alderman;
import org.fxapps.temis.ui.cache.ImageCache;

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
	}

	static final ImageView photo(Image image) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(120);
		imageView.setFitHeight(150);
		return imageView;
	}

}
