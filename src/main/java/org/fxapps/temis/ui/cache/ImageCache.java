package org.fxapps.temis.ui.cache;

import java.util.HashMap;
import java.util.Map;

import org.fxapps.temis.ui.cell.AldermanListCell;

import javafx.scene.image.Image;

public class ImageCache {
	
	private static final String NOPHOTO = "nophoto";
	private static final String NO_PHOTO_IMG = "/images/nophoto.png";
	
	private static Map<String, Image> imageCache;
	
	static {
		imageCache = new HashMap<>();
		String noPhotoPath = AldermanListCell.class.getResource(NO_PHOTO_IMG).toString();
		imageCache.put(NOPHOTO, new Image(noPhotoPath, true));
	}
	
	public static Image noPhoto() {
		return imageCache.get(NOPHOTO);
	}
	
	public static Image saveImageIfAbsent(String key, String imageUrl) {
		return imageCache.computeIfAbsent(key, (k) -> new Image(imageUrl, true));
	}

}
