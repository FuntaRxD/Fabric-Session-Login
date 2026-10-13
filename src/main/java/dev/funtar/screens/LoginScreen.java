package dev.funtar.screens;

import dev.funtar.SessionIDLoginMod;
import dev.funtar.utils.APIUtils;
import dev.funtar.utils.SessionUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;

import java.io.IOException;

import static dev.funtar.utils.FormattingUtils.surroundWithObfuscated;

public class LoginScreen extends Screen {
	private EditBox sessionField;
	private Button loginButton;
	private Button restoreButton;

	private Component currentTitle;

	public LoginScreen() {
		super(Component.literal(""));
		this.currentTitle = surroundWithObfuscated(Component.literal("Input Session ID").withStyle(ChatFormatting.GOLD), 5);
	}

	@Override
	protected void init() {
		int centerX = this.width / 2;
		int centerY = this.height / 2;

		sessionField = new EditBox(
				this.font,
				centerX - 100,
				centerY,
				200,
				20,
				Component.literal("Session Input")
		);
		sessionField.setMaxLength(32767);
		sessionField.setValue("");
		this.addRenderableWidget(sessionField);
		this.setInitialFocus(sessionField);

		loginButton = Button.builder(Component.literal("Login"), button -> {
			String sessionInput = sessionField.getValue().trim();

			if (!sessionInput.isEmpty()) {
				try {
					String[] sessionInfo = APIUtils.getProfileInfo(sessionInput);
					SessionUtils.setSession(SessionUtils.createSession(sessionInfo[0], sessionInfo[1], sessionInput));
					this.currentTitle = surroundWithObfuscated(Component.literal("Logged in as: " + sessionInfo[0]).withStyle(ChatFormatting.GREEN), 5);
					restoreButton.active = true;
				} catch (IOException | RuntimeException e) {
					this.currentTitle = surroundWithObfuscated(Component.literal("Invalid Session ID").withStyle(ChatFormatting.RED), 7);
				}
			} else {
				this.currentTitle = surroundWithObfuscated(Component.literal("Session ID cannot be empty").withStyle(ChatFormatting.RED), 5);
			}
		}).bounds(centerX - 100, centerY + 25, 97, 20).build();
		this.addRenderableWidget(loginButton);

		restoreButton = Button.builder(Component.literal("Restore"), button -> {
			SessionUtils.restoreSession();
			this.currentTitle = surroundWithObfuscated(Component.literal("Restored original session").withStyle(ChatFormatting.GREEN), 7);
			loginButton.active = true;
			restoreButton.active = false;
		}).bounds(centerX + 3, centerY + 25, 97, 20).build();
		this.addRenderableWidget(restoreButton);

		Button backButton = Button.builder(Component.literal("Back"), button ->
						SessionIDLoginMod.openScreen(new JoinMultiplayerScreen(new TitleScreen())))
				.bounds(centerX - 100, centerY + 50, 200, 20).build();
		this.addRenderableWidget(backButton);

		if (SessionIDLoginMod.currentSession.equals(SessionIDLoginMod.originalSession)) {
			restoreButton.active = false;
		}
	}

	//? if >=26.1 {
	@Override
	public void extractRenderState(net.minecraft.client.gui.GuiGraphicsExtractor extractor, int mouseX, int mouseY, float delta) {
		super.extractRenderState(extractor, mouseX, mouseY, delta);
		extractor.centeredText(this.font, this.currentTitle, this.width / 2, this.height / 2 - 30, 0xFFFFFFFF);
	}
	//?} else {
	/*@Override
	public void render(net.minecraft.client.gui.GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(this.font, this.currentTitle, this.width / 2, this.height / 2 - 30, 0xFFFFFFFF);
	}
	*///?}
}
