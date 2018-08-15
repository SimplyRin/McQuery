package net.simplyrin.mcquery;

import com.google.gson.JsonObject;

import club.sk1er.utils.HttpClient;
import net.simplyrin.jsonloader.JsonLoader;

/**
 * Created by SimplyRin on 2018/08/14.
 *
 * Copyright (c) 2018 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class ServerQuery {

	private String ip;
	private int port;

	public ServerQuery(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public Result getResult() {
		String raw = HttpClient.rawWithAgent("https://api.minetools.eu/ping/" + this.ip + "/" + this.port);

		JsonLoader jsonLoader = new JsonLoader(raw);
		JsonObject jsonObject = jsonLoader.getJsonObject();

		String description = null;
		String favicon = null;
		double latency = 1.0D;
		Players players = null;
		Version version = null;

		if(jsonObject.has("description")) {
			try {
				description = jsonObject.get("description").getAsString();
			} catch (Exception e) {
			}
		}

		if(jsonObject.has("favicon")) {
			try {
				favicon = jsonObject.get("favicon").getAsString();
			} catch (Exception e) {
			}
		}

		if(jsonObject.has("latency")) {
			try {
				latency = jsonObject.get("latency").getAsDouble();
			} catch (Exception e) {
			}
		}

		if(jsonObject.has("version")) {
			try {
				JsonObject versionJsonObject = jsonObject.get("version").getAsJsonObject();

				String name = null;
				try {
					if(versionJsonObject.has("name")) {
						name = versionJsonObject.get("name").getAsString();
					}
				} catch (Exception e) {
				}

				int protocol = -1;
				try {
					if(versionJsonObject.has("protocol")) {
						protocol = versionJsonObject.get("protocol").getAsInt();
					}
				} catch (Exception e) {
				}

				version = new Version(name, protocol);
			} catch (Exception e) {
			}
		}

		if(jsonObject.has("players")) {
			try {
				JsonObject playersJsonObject = jsonObject.get("players").getAsJsonObject();

				int online = -1;
				try {
					if(playersJsonObject.has("online")) {
						online = playersJsonObject.get("online").getAsInt();
					}
				} catch (Exception e) {
				}

				int max = -1;
				try {
					if(playersJsonObject.has("max")) {
						max = playersJsonObject.get("max").getAsInt();
					}
				} catch (Exception e) {
				}

				players = new Players(online, max);
			} catch (Exception e) {
			}
		}

		return new Result(description, favicon, latency, players, version);
	}

	public class Result {

		private String description;
		private String favicon;
		private double latency;
		private Players players;
		private Version version;

		public Result(String description, String favicon, double latency, Players players, Version version) {
			this.description = description;
			this.favicon = favicon;
			this.latency = latency;
			this.players = players;
			this.version = version;
		}

		public String getDescription() {
			return this.description;
		}

		public String getFavicon() {
			return this.favicon;
		}

		public double getLatency() {
			return this.latency;
		}

		public Players getPlayers() {
			return this.players;
		}

		public Version getVersion() {
			return this.version;
		}

	}

	public class Players {

		private int online;
		private int max;

		public Players(int online, int max) {
			this.online = online;
			this.max = max;
		}

		/**
		 * @return "20000"
		 */
		public int getOnline() {
			return this.online;
		}

		/**
		 * @return "62000"
		 */
		public int getMax() {
			return this.max;
		}

	}

	public class Version {

		private String name;
		private int protocol;

		public Version(String name, int protocol) {
			this.name = name;
			this.protocol = protocol;
		}

		/**
		 * @return "Requires MC 1.8-1.13"
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * @return "47"
		 */
		public int getProtocol() {
			return this.protocol;
		}

	}

}
