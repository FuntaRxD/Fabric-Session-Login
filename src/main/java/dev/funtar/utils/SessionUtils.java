package dev.funtar.utils;

import dev.funtar.SessionIDLoginMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;

import java.util.Optional;
import java.util.UUID;

public class SessionUtils {
	public static String getUsername() {
		return Minecraft.getInstance().getUser().getName();
	}

	public static String getToken() {
		return Minecraft.getInstance().getUser().getAccessToken();
	}

	public static User getSession() {
		return Minecraft.getInstance().getUser();
	}

	public static User createSession(String username, String uuidString, String ssid) {
		if (uuidString.length() == 32) {
			uuidString = uuidString.substring(0, 8) + "-" +
					uuidString.substring(8, 12) + "-" +
					uuidString.substring(12, 16) + "-" +
					uuidString.substring(16, 20) + "-" +
					uuidString.substring(20);
		}
		return createSession(username, UUID.fromString(uuidString), ssid);
	}

	public static User createSession(String username, UUID uuid, String ssid) {
		//? if >=1.21.9 {
		return new User(username, uuid, ssid, Optional.empty(), Optional.empty());
		//?} else {
		/*return new User(username, uuid, ssid, Optional.empty(), Optional.empty(), User.Type.MSA);
		*///?}
	}

	public static void setSession(User session) {
		SessionIDLoginMod.currentSession = session;
	}

	public static void restoreSession() {
		SessionIDLoginMod.currentSession = SessionIDLoginMod.originalSession;
	}
}
