package fun.bm.interfaceprotectionrtm.client.packet.handlers;

import fun.bm.interfaceprotectionrtm.client.util.ClientMessageSender;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import fun.bm.interfaceprotectionrtm.packet.packets.S2CChannelUpdateResponse;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class S2CChannelUpdateResponseHandler {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(
                S2CChannelUpdateResponse.TYPE.getId(),
                (client, handler, buf, responseSender) -> {
                    S2CChannelUpdateResponse packet = new S2CChannelUpdateResponse(buf);

                    client.execute(() -> handleS2CChannelUpdateResponse(packet));
                }
        );
    }

    private static void handleS2CChannelUpdateResponse(S2CChannelUpdateResponse packet) {
        if (packet.isSuccess()) {
            String msgS = "订阅已成功更新。";
            String msgP;
            String msgC;
            String msgSt = "";
            boolean now = false;
            EnumCannel cannel = EnumCannel.getCannel(packet.getUpdatedId());
            if (cannel == null) {
                msgP = "";
                msgC = "未知的订阅ID: " + packet.getUpdatedId();
            } else {
                if (packet.isMissingData()) {
                    msgP = "由于订阅为空，服务器未返回数据表。";
                    msgC = "";
                } else {
                    IntList ids = packet.getChannelIds();
                    if (ids != null) now = ids.contains(packet.getUpdatedId());
                    msgP = "订阅玩家信息: " + packet.getName() + "[" + packet.getUuid() + "]";
                    StringBuilder msgC0 = new StringBuilder("当前订阅的全部事件: ");
                    boolean first = true;
                    for (int channelId : packet.getChannelIds()) {
                        if (first) {
                            first = false;
                        } else {
                            msgC0.append(", ");
                        }
                        EnumCannel cannel0 = EnumCannel.getCannel(channelId);
                        if (cannel0 != null) {
                            msgC0.append(cannel0.name().toLowerCase());
                        }
                    }
                    msgC = msgC0.toString();
                }
            }
            if (cannel != null) {
                msgSt = "订阅 " + cannel.name().toLowerCase() + " 已" + (now ? "启" : "禁") + "用。";
            }
            ClientMessageSender.sendMessage(msgS, msgSt, msgP, msgC);
        } else {
            String msg = "无法向服务器更新订阅消息。";
            ClientMessageSender.sendMessage(msg);
        }
    }
}
