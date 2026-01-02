package fun.bm.interfaceprotectionrtm.data;

import fun.bm.interfaceprotectionrtm.data.subscribes.SubscribeManager;
import fun.bm.interfaceprotectionrtm.data.subscribes.SubscribedPlayer;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import net.minecraft.server.network.ServerPlayerEntity;

public class MergedDataManager {
    public static boolean isChannelSubscribedFor(EnumCannel cannel, ServerPlayerEntity player) {
        boolean force = ServerDataStore.testCannelForce(cannel);
        boolean enabled = ServerDataStore.testCannelEnabled(cannel);
        if (force) return enabled;
        SubscribedPlayer sp = SubscribeManager.get(player);
        if (sp == null) return false;
        return sp.subscribedChannels.contains(cannel.getCannelId());
    }
}
