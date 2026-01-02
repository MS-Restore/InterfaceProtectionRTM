package fun.bm.interfaceprotectionrtm.client.mixin;

import fun.bm.interfaceprotectionrtm.client.data.StatusHolder;
import net.minecraft.client.resource.ResourceReloadLogger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResourceReloadLogger.class)
public abstract class ResourceReloadLoggerMixin {

    @Inject(method = "finish", at = @At("TAIL"))
    private void afterReload(CallbackInfo ci) {
        StatusHolder.updateResourceLoadStatus(false);
    }
}

