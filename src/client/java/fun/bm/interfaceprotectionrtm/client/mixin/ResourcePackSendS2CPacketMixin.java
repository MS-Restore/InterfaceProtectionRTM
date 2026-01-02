package fun.bm.interfaceprotectionrtm.client.mixin;

import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.ResourcePackSendS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static fun.bm.interfaceprotectionrtm.client.data.StatusHolder.updateResourceLoadStatus;

@Mixin(ResourcePackSendS2CPacket.class)
public class ResourcePackSendS2CPacketMixin {
    @Inject(method = "apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V", at = @At("HEAD"))
    private void beforeApply(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
        updateResourceLoadStatus(true);
    }
}
