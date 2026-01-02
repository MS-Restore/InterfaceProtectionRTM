package fun.bm.interfaceprotectionrtm.data;

import fun.bm.interfaceprotectionrtm.config.IConfigModule;
import fun.bm.interfaceprotectionrtm.config.ServerConfigManager;
import fun.bm.interfaceprotectionrtm.config.flags.ConfigInfo;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import fun.bm.interfaceprotectionrtm.enums.EnumServerLoadType;
import net.minecraft.util.Pair;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServerConfigTranslator {
    public static Map<Integer, Pair<Boolean, Boolean>> getAllKeysServer() {
        Map<Integer, Pair<Boolean, Boolean>> keys = new HashMap<>();
        Set<IConfigModule> configs = ServerConfigManager.getConfig("server").getAllInstanced().keySet();
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
                    value = EnumServerLoadType.DEFAULT;
                }
                if (value instanceof EnumServerLoadType type) {
                    boolean enable = type == EnumServerLoadType.DEFAULT ? cannel1.isDefaultServer() : type == EnumServerLoadType.FORCEENABLE;
                    boolean force = type != EnumServerLoadType.DEFAULT;

                    keys.put(cannel1.getCannelId(), new Pair<>(enable, force));
                }
            }
        }
        return keys;
    }
}
