package fun.bm.interfaceprotectionrtm.packet;

import fun.bm.interfaceprotectionrtm.packet.handlers.C2SCannelSubscribeHandler;
import fun.bm.interfaceprotectionrtm.packet.handlers.C2SCannelUpdateSubscribeHandler;
import fun.bm.interfaceprotectionrtm.packet.handlers.C2SESCScreenHandler;
import fun.bm.interfaceprotectionrtm.packet.handlers.C2SResourceLoadingHandler;

public class ServerPacketRegister {
    public static void register() {
        C2SCannelSubscribeHandler.register();
        C2SCannelUpdateSubscribeHandler.register();
        C2SResourceLoadingHandler.register();
        C2SESCScreenHandler.register();
    }
}
