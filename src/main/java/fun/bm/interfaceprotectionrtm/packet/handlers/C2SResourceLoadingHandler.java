package fun.bm.interfaceprotectionrtm.packet.handlers;

import fun.bm.interfaceprotectionrtm.data.action.TargetAimManager;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import fun.bm.interfaceprotectionrtm.packet.packets.C2SResourceLoading;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;

public class C2SResourceLoadingHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
                C2SResourceLoading.TYPE.getId(),
                (server, player, handler, buf, responseSender) -> {
                    C2SResourceLoading packet = new C2SResourceLoading(buf);
                    server.execute(() -> handleC2SResourceLoading(player, packet));
                }
        );
    }

    private static void handleC2SResourceLoading(ServerPlayerEntity player, C2SResourceLoading packet) {
        boolean isLoading = packet.isLoading();
        if (isLoading) {
            long clientSideStartTime = packet.getClientSideStartTime();
            long serverSideStartTime = System.currentTimeMillis();
            TargetAimManager.addCanNotBeAimed(player, EnumCannel.LOADRESOURCEPACKS, new Pair<>(serverSideStartTime, clientSideStartTime));
        } else {
            TargetAimManager.removeCanNotBeAimed(player);
        }
    }
}
