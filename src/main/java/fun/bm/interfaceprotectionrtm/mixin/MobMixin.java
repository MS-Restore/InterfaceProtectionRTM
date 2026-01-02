package fun.bm.interfaceprotectionrtm.mixin;

import fun.bm.interfaceprotectionrtm.data.action.TargetAimManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void beforeTick(CallbackInfo ci) {
        MobEntity self = (MobEntity) (Object) this;
        LivingEntity target = self.getTarget();
        if (target instanceof ServerPlayerEntity p) {
            if (p.isCreative() || p.isSpectator() || !TargetAimManager.canBeAimed(p)) {
                self.setTarget(null);
            }
        }
    }
}
