package fun.bm.interfaceprotectionrtm.event.events;

import fun.bm.interfaceprotectionrtm.data.action.IgnoreReason;
import fun.bm.interfaceprotectionrtm.data.action.TargetAimManager;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;

public class ServerTickEvent {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            TargetAimManager.tick();
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                long time = player.getLastActionTime();
                long timeLast = TargetAimManager.getCanNotBeAimedTime(player);
                if (timeLast == -1) continue;
                IgnoreReason reason = TargetAimManager.getCanNotBeAimedReason(player);
                switch (reason.cannel) {
                    case EnumCannel.LOGIN -> {
                        if (time != timeLast) {
                            TargetAimManager.removeCanNotBeAimed(player);
                        }
                    }

                    case EnumCannel.LOADRESOURCEPACKS, EnumCannel.INESCSCREEN -> {
                        Object obj = reason.getObj();
                        if (obj == null) continue;
                        long now = System.currentTimeMillis();
                        Pair<Long, Long> pair = (Pair<Long, Long>) obj;
                        long clientTime = pair.getLeft();
                        long serverTime = pair.getRight();

                        if (Math.abs(serverTime - clientTime) > 10 * 1000
                                || (now - serverTime > 60 * 1000)) {
                            TargetAimManager.removeCanNotBeAimed(player);
                        }
                    }
                }
            }
        });
    }
}
