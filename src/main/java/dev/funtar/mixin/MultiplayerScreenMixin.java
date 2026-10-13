package dev.funtar.mixin;

import dev.funtar.SessionIDLoginMod;
import dev.funtar.screens.EditAccountScreen;
import dev.funtar.screens.LoginScreen;
import dev.funtar.screens.SessionStatusWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JoinMultiplayerScreen.class)
public abstract class MultiplayerScreenMixin extends Screen {

	protected MultiplayerScreenMixin(Component title) {
		super(title);
	}

	@Inject(method = "init", at = @At("TAIL"))
	private void sessionIdLogin$addWidgets(CallbackInfo ci) {
		this.addRenderableWidget(Button.builder(Component.literal("Login"), button ->
						SessionIDLoginMod.openScreen(new LoginScreen()))
				.bounds(this.width - 90, 5, 80, 20).build());

		this.addRenderableWidget(Button.builder(Component.literal("Edit Account"), button ->
						SessionIDLoginMod.openScreen(new EditAccountScreen()))
				.bounds(this.width - 180, 5, 80, 20).build());

		this.addRenderableWidget(new SessionStatusWidget(5, 5, 200, 12));
	}
}
