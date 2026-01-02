package fun.bm.interfaceprotectionrtm.client;

import fun.bm.interfaceprotectionrtm.client.config.ClientConfigManager;
import fun.bm.interfaceprotectionrtm.client.event.ClientEventHandler;
import fun.bm.interfaceprotectionrtm.client.packet.ClientPacketRegister;
import net.fabricmc.api.ClientModInitializer;

public class InterfaceprotectionrtmClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPacketRegister.register();
        ClientEventHandler.register();
        ClientConfigManager.initConfig();
    }
}
