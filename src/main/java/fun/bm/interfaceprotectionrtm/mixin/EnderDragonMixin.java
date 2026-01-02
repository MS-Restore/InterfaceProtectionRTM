package fun.bm.interfaceprotectionrtm.mixin;

import fun.bm.interfaceprotectionrtm.data.action.TargetAimManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonMixin {
    @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z",
            at = @At("HEAD"),
            cancellable = true)
    private void canTarget(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof ServerPlayerEntity player && needCancel(player)) {
            cir.cancel();
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean needCancel(ServerPlayerEntity player) {
        return !TargetAimManager.canBeAimed(player);
    }
}
