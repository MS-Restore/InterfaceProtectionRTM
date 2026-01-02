package fun.bm.interfaceprotectionrtm.config;


import fun.bm.interfaceprotectionrtm.command.ServerConfigCommand;
import fun.bm.interfaceprotectionrtm.data.ServerDataStore;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerConfigManager {
    public static final Map<String, ConfigsInstance> configfiles = new ConcurrentHashMap<>();

    public static void initConfigs() {
        ConfigsInstance config = ConfigsInstance.of(new File("config"), "ifp", "ifp_server_config.toml", "fun.bm.interfaceprotectionrtm.config.modules");
        configfiles.put("server", config);
        try {
            config.preLoadConfig();
            config.finalizeLoadConfig();
            new ServerConfigCommand(ServerDataStore.INSTANCE, config).register();
        } catch (IOException e) {
            throw new RuntimeException("Failed to preload config", e);
        }
    }

    public static ConfigsInstance getConfig(String name) {
        return configfiles.get(name);
    }
}