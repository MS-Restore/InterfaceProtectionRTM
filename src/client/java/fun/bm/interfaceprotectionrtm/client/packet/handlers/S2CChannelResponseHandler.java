package fun.bm.interfaceprotectionrtm.client.packet.handlers;

import fun.bm.interfaceprotectionrtm.client.util.ClientMessageSender;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import fun.bm.interfaceprotectionrtm.packet.packets.S2CChannelResponse;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class S2CChannelResponseHandler {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(
                S2CChannelResponse.TYPE.getId(),
                (client, handler, buf, responseSender) -> {
                    S2CChannelResponse packet = new S2CChannelResponse(buf);

                    client.execute(() -> handleS2CChannelResponse(packet));
                }
        );
    }

    // always run in client side, so we will uncheck it
    private static void handleS2CChannelResponse(S2CChannelResponse packet) {
        if (packet.isSuccess()) {
            String msgP = "订阅玩家信息: " + packet.getName() + "[" + packet.getUuid() + "]";
            StringBuilder msgC = new StringBuilder("订阅的事件: ");
            boolean first = true;
            for (int channelId : packet.getChannelIds()) {
                if (first) {
                    first = false;
                } else {
                    msgC.append(", ");
                }
                EnumCannel cannel = EnumCannel.getCannel(channelId);
                if (cannel != null) {
                    msgC.append(cannel.name().toLowerCase());
                }
            }
            ClientMessageSender.sendMessage(msgP, msgC.toString());
        } else {
            String msg = "无法向服务器同步订阅消息。";
            ClientMessageSender.sendMessage(msg);
        }
    }
}
