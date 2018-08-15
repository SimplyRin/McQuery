package net.simplyrin.mcquery;

import java.util.UUID;

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
public class NameQuery {

	private String name;

	public NameQuery(String name) {
		this.name = name;
	}

	public Profile getProfile() {
		String raw = HttpClient.rawWithAgent("https://api.mojang.com/users/profiles/minecraft/" + this.name);

		try {
			JsonLoader jsonLoader = new JsonLoader(raw);
			JsonObject jsonObject = jsonLoader.getJsonObject();

			UUID uniqueId = this.toFullUniqueId(jsonObject.get("id").getAsString());
			String name = jsonObject.get("name").getAsString();

			return new Profile(uniqueId, name);
		} catch (Exception e) {
		}
		return null;
	}

	public class Profile {

		private UUID uuid;
		private String name;

		public Profile(UUID uuid, String name) {
			this.uuid = uuid;
			this.name = name;
		}

		public UUID getUniqueId() {
			return this.uuid;
		}

		public String getName() {
			return this.name;
		}

	}

	public UUID toFullUniqueId(String name) {
		return UUID.fromString(name.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
	}

}
