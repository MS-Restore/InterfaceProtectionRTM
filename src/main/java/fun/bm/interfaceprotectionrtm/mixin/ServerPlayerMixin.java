package fun.bm.interfaceprotectionrtm.mixin;

import fun.bm.interfaceprotectionrtm.data.action.TargetAimManager;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (needCancel((ServerPlayerEntity) (Object) this)) {
            cir.cancel();
            cir.setReturnValue(false);
        }
    }

    @Unique
    protected boolean needCancel(ServerPlayerEntity player) {
        return !TargetAimManager.canBeAimed(player);
    }
}
