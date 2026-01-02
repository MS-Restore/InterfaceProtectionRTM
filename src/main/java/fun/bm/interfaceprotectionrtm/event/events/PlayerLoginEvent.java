package fun.bm.interfaceprotectionrtm.event.events;

import fun.bm.interfaceprotectionrtm.data.MergedDataManager;
import fun.bm.interfaceprotectionrtm.data.ServerDataStore;
import fun.bm.interfaceprotectionrtm.data.action.TargetAimManager;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerLoginEvent {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register(PlayerLoginEvent::processEvent);
    }

    private static void processEvent(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer server) {
        ServerPlayerEntity player = serverPlayNetworkHandler.player;
        if (MergedDataManager.isChannelSubscribedFor(EnumCannel.LOGIN, player)) {
            TargetAimManager.addCanNotBeAimed(player, EnumCannel.LOGIN);
        }
        StringBuilder msg = new StringBuilder();
        boolean first = true;
        StringBuilder msg1 = new StringBuilder();
        boolean first1 = true;
        StringBuilder msg2 = new StringBuilder();
        boolean first2 = true;
        StringBuilder msg3 = new StringBuilder();
        boolean first3 = true;
        for (EnumCannel cannel : EnumCannel.values()) {
            boolean force = ServerDataStore.testCannelForce(cannel);
            boolean enabled = ServerDataStore.testCannelEnabled(cannel);
            if (force) {
                if (enabled) {
                    if (first2) {
                        first2 = false;
                    } else {
                        msg2.append(", ");
                    }
                    msg2.append(cannel.name().toLowerCase());
                } else {
                    if (first3) {
                        first3 = false;
                    } else {
                        msg3.append(", ");
                    }
                    msg3.append(cannel.name().toLowerCase());
                }
            } else {
                if (first) {
                    first = false;
                } else {
                    msg.append(", ");
                }
                msg.append(cannel.name().toLowerCase());
            }

            if (enabled) {
                if (first1) {
                    first1 = false;
                } else {
                    msg1.append(", ");
                }
                msg1.append(cannel.name().toLowerCase());
            }
        }
        player.sendMessage(Text.literal("[InterfaceProtectionRTM] 此服务器正在使用IFP-RTM保护玩家在一些情况下不被生物追踪"));
        if (!first) player.sendMessage(Text.literal("[InterfaceProtectionRTM] 服务器可用的订阅项: " + msg));
        if (!first1) player.sendMessage(Text.literal("[InterfaceProtectionRTM] 服务器默认启用的订阅项: " + msg1));
        if (!first2) player.sendMessage(Text.literal("[InterfaceProtectionRTM] 服务器强制启用的订阅项: " + msg2));
        if (!first3) player.sendMessage(Text.literal("[InterfaceProtectionRTM] 服务器强制禁用的订阅项: " + msg3));
        player.sendMessage(Text.literal("[InterfaceProtectionRTM] 服务器推荐客户端安装对应的模组以保护期望的全部行为"));
    }
}
