package fun.bm.interfaceprotectionrtm.packet.handlers;

import fun.bm.interfaceprotectionrtm.data.ServerDataStore;
import fun.bm.interfaceprotectionrtm.data.subscribes.SubscribeManager;
import fun.bm.interfaceprotectionrtm.data.subscribes.SubscribedPlayer;
import fun.bm.interfaceprotectionrtm.packet.packets.C2SCannelUpdateSubscribe;
import fun.bm.interfaceprotectionrtm.packet.packets.S2CChannelUpdateResponse;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;

public class C2SCannelUpdateSubscribeHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
                C2SCannelUpdateSubscribe.TYPE.getId(),
                (server, player, handler, buf, responseSender) -> {
                    C2SCannelUpdateSubscribe packet = new C2SCannelUpdateSubscribe(buf);
                    server.execute(() -> handleC2SChannelUpdateSubscribe(player, packet));
                }
        );
    }

    private static void handleC2SChannelUpdateSubscribe(ServerPlayerEntity player, C2SCannelUpdateSubscribe packet) {
        int updatedId = packet.getUpdatedId();
        boolean subscribe = packet.toSubscribe();
        Pair<SubscribedPlayer, Boolean> pair = SubscribeManager.subscribe(player, updatedId, subscribe);
        ServerDataStore.INSTANCE.update();
        S2CChannelUpdateResponse response = new S2CChannelUpdateResponse(pair.getLeft(), updatedId, pair.getRight());
        ServerPlayNetworking.send(player, response);
    }
}
