package club.sk1er.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class HttpClient {

	/**
	 * @author Sk1er (https://github.com/Sk1er)
	 */
	public static String rawWithAgent(String url) {
		url = url.replace(" ", "%20");
		try {
			URL u = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("GET");
			connection.setUseCaches(true);
			connection.addRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setReadTimeout(15000);
			connection.setConnectTimeout(15000);
			connection.setDoOutput(true);
			InputStream is = connection.getInputStream();
			return IOUtils.toString(is);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
