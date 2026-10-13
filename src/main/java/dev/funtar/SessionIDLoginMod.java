package dev.funtar;

import dev.funtar.utils.SessionUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionIDLoginMod implements ModInitializer {
	public static final String MOD_ID = "session-id-login-mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static String MOD_VERSION = "1.?.?";
	public static String MOD_AUTHOR = "Unknown";

	public static User originalSession;
	public static User currentSession;
	public static boolean overrideSession = false;

	@Override
	public void onInitialize() {
		MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID)
				.map(c -> c.getMetadata().getVersion().getFriendlyString())
				.orElse("1.?.?");
		MOD_AUTHOR = FabricLoader.getInstance().getModContainer(MOD_ID)
				.map(c -> c.getMetadata().getAuthors().isEmpty() ? "Unknown" : c.getMetadata().getAuthors().iterator().next().getName())
				.orElse("Unknown");

		originalSession = SessionUtils.getSession();
		currentSession = originalSession;
		overrideSession = true;
	}

	/**
	 * Opens a screen, hiding the {@code setScreen} / {@code setScreenAndShow} difference
	 * between 26.1 and 26.2+.
	 */
	public static void openScreen(Screen screen) {
		//? if >=26.2 {
		Minecraft.getInstance().setScreenAndShow(screen);
		//?} else {
		/*Minecraft.getInstance().setScreen(screen);
		*///?}
	}
}
