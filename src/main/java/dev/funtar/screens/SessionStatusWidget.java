package dev.funtar.screens;

import dev.funtar.utils.APIUtils;
import dev.funtar.utils.SessionUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

/**
 * Renders the "User: name | [status]" line on the multiplayer screen.
 *
 * <p>Implemented as an auto-rendered widget instead of a render injection, so the same code
 * works both on the classic GUI ({@code renderWidget}) and on the retained-mode GUI
 * ({@code extractWidgetRenderState}) introduced in 26.1.</p>
 */
public class SessionStatusWidget extends AbstractWidget {
	private volatile Boolean isSessionValid = null;
	private boolean validationStarted = false;

	public SessionStatusWidget(int x, int y, int width, int height) {
		super(x, y, width, height, Component.literal("Session status"));
		this.active = false;
	}

	//? if >=26.1 {
	@Override
	protected void extractWidgetRenderState(net.minecraft.client.gui.GuiGraphicsExtractor extractor, int mouseX, int mouseY, float delta) {
		startValidationIfNeeded();
		extractor.text(Minecraft.getInstance().font, buildDisplay(), this.getX(), this.getY(), 0xFFFFFFFF);
	}
	//?} else {
	/*@Override
	protected void renderWidget(net.minecraft.client.gui.GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		startValidationIfNeeded();
		graphics.drawString(Minecraft.getInstance().font, buildDisplay(), this.getX(), this.getY(), 0xFFFFFFFF);
	}
	*///?}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput narration) {
	}

	private void startValidationIfNeeded() {
		if (!validationStarted) {
			validationStarted = true;
			new Thread(() -> isSessionValid =
					APIUtils.validateSession(Minecraft.getInstance().getUser().getAccessToken()),
					"SessionValidationThread").start();
		}
	}

	private Component buildDisplay() {
		Component statusText;
		Boolean valid = isSessionValid;
		if (valid == null) {
			statusText = Component.literal("[... Validating]").withStyle(ChatFormatting.GRAY);
		} else if (valid) {
			statusText = Component.literal("[\u2714] Valid").withStyle(ChatFormatting.GREEN);
		} else {
			statusText = Component.literal("[\u2718] Invalid").withStyle(ChatFormatting.RED);
		}

		return Component.literal("User: ")
				.append(Component.literal(SessionUtils.getUsername()).withStyle(ChatFormatting.WHITE))
				.append(Component.literal(" | ").withStyle(ChatFormatting.DARK_GRAY))
				.append(statusText);
	}
}
