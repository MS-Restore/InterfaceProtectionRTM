package fun.bm.interfaceprotectionrtm.packet.handlers;

import fun.bm.interfaceprotectionrtm.data.subscribes.SubscribeManager;
import fun.bm.interfaceprotectionrtm.data.subscribes.SubscribedPlayer;
import fun.bm.interfaceprotectionrtm.packet.packets.C2SCannelSubcribe;
import fun.bm.interfaceprotectionrtm.packet.packets.S2CChannelResponse;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashSet;
import java.util.Set;

public class C2SCannelSubscribeHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
                C2SCannelSubcribe.TYPE.getId(),
                (server, player, handler, buf, responseSender) -> {
                    C2SCannelSubcribe packet = new C2SCannelSubcribe(buf);
                    server.execute(() -> handleC2SChannelSubscribe(player, packet));
                }
        );
    }

    private static void handleC2SChannelSubscribe(ServerPlayerEntity player, C2SCannelSubcribe packet) {
        IntList channelIds = packet.getChannelIds();
        Set<Integer> ids = new HashSet<>(channelIds);
        SubscribedPlayer subscribe = SubscribeManager.set(player, ids);
        S2CChannelResponse response = new S2CChannelResponse(subscribe);
        ServerPlayNetworking.send(player, response);
    }
}
