package fun.bm.interfaceprotectionrtm.client.config;


import fun.bm.interfaceprotectionrtm.client.command.ClientConfigCommand;
import fun.bm.interfaceprotectionrtm.client.data.ClientDataStore;
import fun.bm.interfaceprotectionrtm.config.ConfigsInstance;
import fun.bm.interfaceprotectionrtm.config.ServerConfigManager;

import java.io.File;
import java.io.IOException;

public class ClientConfigManager {
    public static void initConfig() {
        ConfigsInstance config = ConfigsInstance.of(new File("config"), "ifp", "ifp_client_config.toml", "fun.bm.interfaceprotectionrtm.client.config.modules");
        ServerConfigManager.configfiles.put("client", config);
        try {
            config.preLoadConfig();
            config.finalizeLoadConfig();
            new ClientConfigCommand(ClientDataStore.INSTANCE, config).register();
        } catch (IOException e) {
            throw new RuntimeException("Failed to preload config", e);
        }
    }
}