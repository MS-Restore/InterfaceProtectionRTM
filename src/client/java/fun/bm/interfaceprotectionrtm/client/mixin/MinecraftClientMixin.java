package fun.bm.interfaceprotectionrtm.client.mixin;

import fun.bm.interfaceprotectionrtm.client.data.StatusHolder;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "reloadResourcesConcurrently", at = @At("HEAD"))
    private void beforeReload(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        StatusHolder.updateResourceLoadStatus(true);
    }

    @Inject(method = "openPauseMenu", at = @At("HEAD"))
    private void beforeOpenPauseMenu(boolean pause, CallbackInfo ci) {
        StatusHolder.updateEscScreenStatus(true);
    }
}

