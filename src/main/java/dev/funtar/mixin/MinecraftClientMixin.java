package dev.funtar.mixin;

import dev.funtar.SessionIDLoginMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

	@Inject(method = "getUser", at = @At("HEAD"), cancellable = true)
	private void sessionIdLogin$overrideUser(CallbackInfoReturnable<User> cir) {
		if (!SessionIDLoginMod.overrideSession || SessionIDLoginMod.currentSession == null) {
			return;
		}
		cir.setReturnValue(SessionIDLoginMod.currentSession);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void sessionIdLogin$windowTitle(CallbackInfo ci) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.getWindow() != null) {
			mc.getWindow().setTitle("SessionID Login \u2022 v" + SessionIDLoginMod.MOD_VERSION + " \u2022 by " + SessionIDLoginMod.MOD_AUTHOR);
		}
	}
}
