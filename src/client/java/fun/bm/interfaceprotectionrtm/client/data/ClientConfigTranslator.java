package fun.bm.interfaceprotectionrtm.client.data;

import fun.bm.interfaceprotectionrtm.client.enums.EnumClientLoadType;
import fun.bm.interfaceprotectionrtm.config.IConfigModule;
import fun.bm.interfaceprotectionrtm.config.ServerConfigManager;
import fun.bm.interfaceprotectionrtm.config.flags.ConfigInfo;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientConfigTranslator {
    public static List<Integer> getAllKeysClient() {
        List<Integer> keys = new ArrayList<>();
        Set<IConfigModule> configs = ServerConfigManager.getConfig("client").getAllInstanced().keySet();
        for (IConfigModule config : configs) {
            for (Field field : config.getClass().getDeclaredFields()) {
                ConfigInfo info = field.getAnnotation(ConfigInfo.class);
                if (info == null) continue;
                EnumCannel cannel1 = EnumCannel.valueOf(info.name().toUpperCase());
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(null);
                } catch (IllegalAccessException e) {
                    value = EnumClientLoadType.DEFAULT;
                }
                if (value instanceof EnumClientLoadType type) {
                    if (type == EnumClientLoadType.DEFAULT ? cannel1.isDefaultClient() : type == EnumClientLoadType.ENABLE) {
                        keys.add(cannel1.getCannelId());
                    }
                }
            }
        }
        return keys;
    }
}
