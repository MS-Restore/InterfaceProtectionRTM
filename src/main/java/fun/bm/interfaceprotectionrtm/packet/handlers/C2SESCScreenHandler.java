package fun.bm.interfaceprotectionrtm.packet.handlers;

import fun.bm.interfaceprotectionrtm.data.action.TargetAimManager;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import fun.bm.interfaceprotectionrtm.packet.packets.C2SESCScreen;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;

public class C2SESCScreenHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
                C2SESCScreen.TYPE.getId(),
                (server, player, handler, buf, responseSender) -> {
                    C2SESCScreen packet = new C2SESCScreen(buf);
                    server.execute(() -> handleC2SESCScreen(player, packet));
                }
        );
    }

    private static void handleC2SESCScreen(ServerPlayerEntity player, C2SESCScreen packet) {
        boolean isLoading = packet.isInEsc();
        if (isLoading) {
            long clientSideStartTime = packet.getClientSideStartTime();
            long serverSideStartTime = System.currentTimeMillis();
            TargetAimManager.addCanNotBeAimed(player, EnumCannel.INESCSCREEN, new Pair<>(serverSideStartTime, clientSideStartTime));
        } else {
            TargetAimManager.removeCanNotBeAimed(player);
        }
    }
}
