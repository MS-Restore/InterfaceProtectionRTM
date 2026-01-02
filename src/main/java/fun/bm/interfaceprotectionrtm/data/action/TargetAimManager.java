package fun.bm.interfaceprotectionrtm.data.action;

import fun.bm.interfaceprotectionrtm.data.MergedDataManager;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TargetAimManager {
    private static final Map<UUID, IgnoreReason> canNotBeAimed = new ConcurrentHashMap<>();

    public static boolean canBeAimed(ServerPlayerEntity player) {
        return !canNotBeAimed.containsKey(player.getUuid());
    }

    public static long getCanNotBeAimedTime(ServerPlayerEntity player) {
        IgnoreReason reason = canNotBeAimed.get(player.getUuid());
        return reason == null ? -1 : reason.timer;
    }

    public static IgnoreReason getCanNotBeAimedReason(ServerPlayerEntity player) {
        return canNotBeAimed.get(player.getUuid());
    }

    public static void addCanNotBeAimed(ServerPlayerEntity player, EnumCannel cannel) {
        if (MergedDataManager.isChannelSubscribedFor(cannel, player)) {
            canNotBeAimed.put(player.getUuid(), new IgnoreReason(cannel, player.getLastActionTime()));
        }
    }

    public static void addCanNotBeAimed(ServerPlayerEntity player, EnumCannel cannel, Object obj) {
        if (MergedDataManager.isChannelSubscribedFor(cannel, player)) {
            canNotBeAimed.put(player.getUuid(), new IgnoreReason(cannel, player.getLastActionTime(), obj));
        }
    }

    public static void removeCanNotBeAimed(ServerPlayerEntity player) {
        canNotBeAimed.remove(player.getUuid());
    }

    public static void tick() {
        canNotBeAimed.forEach((key, value) -> {
            if (value.tick()) {
                canNotBeAimed.remove(key);
            }
        });
    }
}
