package fun.bm.interfaceprotectionrtm.client.data;

import fun.bm.interfaceprotectionrtm.data.AbstractDataStore;
import fun.bm.interfaceprotectionrtm.data.defaults.DefaultValue;
import fun.bm.interfaceprotectionrtm.data.defaults.DefaultValuesStore;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import fun.bm.interfaceprotectionrtm.packet.packets.C2SCannelSubcribe;
import fun.bm.interfaceprotectionrtm.packet.packets.C2SCannelUpdateSubscribe;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

import static fun.bm.interfaceprotectionrtm.client.data.ClientConfigTranslator.getAllKeysClient;

public class ClientDataStore extends AbstractDataStore {
    public static AbstractDataStore INSTANCE = new ClientDataStore();
    private DefaultValuesStore clientStore = null;

    public List<Integer> getOrLoadValues() {
        if (clientStore == null || needUpdate) {
            DefaultValuesStore store = new DefaultValuesStore();
            List<Integer> ret = getAllKeysClient();
            for (EnumCannel cannel : EnumCannel.values()) {
                boolean enabled = ret.contains(cannel.getCannelId());
                store.add(new DefaultValue(cannel, enabled));
            }
            clientStore = store;
            return ret;
        } else {
            List<Integer> ret = new ArrayList<>();
            for (DefaultValue value : clientStore.values) {
                if (value.enabled) {
                    ret.add(value.cannel.getCannelId());
                }
            }
            return ret;
        }
    }

    @Override
    public void onConfigUpdate(PlayerEntity player, EnumCannel cannel) {
        boolean value = getOrLoadValues().contains(cannel.getCannelId());
        if (player == null) return;
        C2SCannelUpdateSubscribe packet = new C2SCannelUpdateSubscribe(cannel.getCannelId(), value);
        ClientPlayNetworking.send(packet);
    }

    @Override
    public void onConfigReload(PlayerEntity player) {
        if (player == null) return;
        C2SCannelSubcribe packet = new C2SCannelSubcribe(new IntImmutableList(getOrLoadValues()));
        ClientPlayNetworking.send(packet);
    }
}
