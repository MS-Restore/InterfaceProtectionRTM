package fun.bm.interfaceprotectionrtm.event.events;

import fun.bm.interfaceprotectionrtm.data.action.TargetAimManager;
import fun.bm.interfaceprotectionrtm.data.subscribes.SubscribeManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class PlayerLogoutEvent {
    public static void register() {
        ServerPlayConnectionEvents.DISCONNECT.register(PlayerLogoutEvent::processEvent);
    }

    private static void processEvent(ServerPlayNetworkHandler serverPlayNetworkHandler, MinecraftServer server) {
        TargetAimManager.removeCanNotBeAimed(serverPlayNetworkHandler.player);
        SubscribeManager.remove(serverPlayNetworkHandler.player);
    }
}
