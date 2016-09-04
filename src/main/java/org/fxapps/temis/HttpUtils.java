package org.fxapps.temis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {

	public static String loadUrl(String addr) throws Exception {
		StringBuilder content = new StringBuilder();
		URL url = new URL(addr);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("accept", "application/json");
		InputStreamReader isReader = new InputStreamReader(conn.getInputStream());
		BufferedReader in = new BufferedReader(isReader);
		String line;
		while ((line = in.readLine()) != null) {
			content.append(line);
		}
		return content.toString();
	}

}
