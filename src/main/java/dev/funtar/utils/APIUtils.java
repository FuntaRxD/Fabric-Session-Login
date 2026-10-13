package dev.funtar.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class APIUtils {

	private static final HttpClient HTTP = HttpClient.newHttpClient();

	public static String[] getProfileInfo(String token) throws IOException {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.minecraftservices.com/minecraft/profile"))
					.header("Authorization", "Bearer " + token)
					.GET()
					.build();
			HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
			JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
			String ign = jsonObject.get("name").getAsString();
			String uuid = jsonObject.get("id").getAsString();
			return new String[]{ign, uuid};
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Boolean validateSession(String token) {
		try {
			String[] profileInfo = getProfileInfo(token);
			String ign = profileInfo[0];
			String uuidString = profileInfo[1];

			if (uuidString.length() == 32) {
				uuidString = uuidString.substring(0, 8) + "-" +
						uuidString.substring(8, 12) + "-" +
						uuidString.substring(12, 16) + "-" +
						uuidString.substring(16, 20) + "-" +
						uuidString.substring(20);
			}

			UUID uuid = UUID.fromString(uuidString);

			return ign.equals(Minecraft.getInstance().getUser().getName())
					&& uuid.equals(Minecraft.getInstance().getUser().getProfileId());
		} catch (Exception e) {
			return false;
		}
	}

	public static int changeSkin(String url, String token) {
		try {
			String jsonString = String.format("{ \"variant\": \"classic\", \"url\": \"%s\"}", url);
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.minecraftservices.com/minecraft/profile/skins"))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(jsonString))
					.build();
			HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode();
		} catch (Exception e) {
			return -1;
		}
	}

	public static int changeName(String newName, String token) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.minecraftservices.com/minecraft/profile/name/" + newName))
					.header("Authorization", "Bearer " + token)
					.PUT(HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode();
		} catch (Exception e) {
			return -1;
		}
	}
}
