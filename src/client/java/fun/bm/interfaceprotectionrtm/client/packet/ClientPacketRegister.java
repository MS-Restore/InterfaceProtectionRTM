package fun.bm.interfaceprotectionrtm.client.packet;

import fun.bm.interfaceprotectionrtm.client.packet.handlers.S2CChannelResponseHandler;
import fun.bm.interfaceprotectionrtm.client.packet.handlers.S2CChannelUpdateResponseHandler;

public class ClientPacketRegister {
    public static void register() {
        S2CChannelResponseHandler.register();
        S2CChannelUpdateResponseHandler.register();
    }
}
