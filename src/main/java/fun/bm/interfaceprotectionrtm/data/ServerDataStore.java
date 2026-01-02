package fun.bm.interfaceprotectionrtm.data;

import fun.bm.interfaceprotectionrtm.data.defaults.DefaultValue;
import fun.bm.interfaceprotectionrtm.data.defaults.DefaultValuesStore;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fun.bm.interfaceprotectionrtm.data.ServerConfigTranslator.getAllKeysServer;

public class ServerDataStore extends AbstractDataStore {
    public static AbstractDataStore INSTANCE = new ServerDataStore();
    private DefaultValuesStore serverStore = null;

    public static boolean testCannelEnabled(EnumCannel cannel) {
        return testCannelEnabled(cannel.getCannelId());
    }

    public static boolean testCannelForce(EnumCannel cannel) {
        return testCannelForce(cannel.getCannelId());
    }

    public static boolean testCannelEnabled(int cannel) {
        DefaultValuesStore store = ((ServerDataStore) INSTANCE).getOrLoadStore();
        for (DefaultValue value : store.values) {
            if (value.cannel.getCannelId() == cannel) {
                return value.enabled;
            }
        }
        return false;
    }

    public static boolean testCannelForce(int cannel) {
        DefaultValuesStore store = ((ServerDataStore) INSTANCE).getOrLoadStore();
        for (DefaultValue value : store.values) {
            if (value.cannel.getCannelId() == cannel) {
                return value.force;
            }
        }
        return false;
    }

    public List<Integer> getOrLoadValues() {
        List<Integer> ret = new ArrayList<>();
        for (DefaultValue value : getOrLoadStore().values) {
            if (value.enabled) {
                ret.add(value.cannel.getCannelId());
            }
        }
        return ret;
    }

    public DefaultValuesStore getOrLoadStore() {
        if (serverStore == null || needUpdate) {
            DefaultValuesStore store = new DefaultValuesStore();
            Map<Integer, Pair<Boolean, Boolean>> map = getAllKeysServer();
            for (EnumCannel cannel : EnumCannel.values()) {
                boolean enabled = map.get(cannel.getCannelId()).getLeft();
                boolean force = map.get(cannel.getCannelId()).getRight();
                store.add(new DefaultValue(cannel, enabled, force));
            }
            serverStore = store;
            return store;
        } else {
            return serverStore;
        }
    }
}
