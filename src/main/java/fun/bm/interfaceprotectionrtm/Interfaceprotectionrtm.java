package fun.bm.interfaceprotectionrtm;

import fun.bm.interfaceprotectionrtm.config.ServerConfigManager;
import fun.bm.interfaceprotectionrtm.event.ServerEventHandler;
import fun.bm.interfaceprotectionrtm.packet.ServerPacketRegister;
import net.fabricmc.api.ModInitializer;

public class Interfaceprotectionrtm implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerPacketRegister.register();
        ServerEventHandler.register();
        ServerConfigManager.initConfigs();
    }
}
