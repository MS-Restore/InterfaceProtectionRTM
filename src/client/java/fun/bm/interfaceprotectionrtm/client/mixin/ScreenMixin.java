package fun.bm.interfaceprotectionrtm.client.mixin;

import fun.bm.interfaceprotectionrtm.client.data.StatusHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(method = "close", at = @At("TAIL"))
    private void afterClose(CallbackInfo ci) {
        if (!StatusHolder.escScreenStatus.enabled) return;
        if (MinecraftClient.getInstance().currentScreen == null) {
            StatusHolder.updateEscScreenStatus(false);
        }
    }
}

