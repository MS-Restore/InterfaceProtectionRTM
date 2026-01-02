package fun.bm.interfaceprotectionrtm.data;

import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public abstract class AbstractDataStore {
    protected boolean needUpdate = false;

    public abstract List<Integer> getOrLoadValues();

    public void onConfigUpdate(PlayerEntity player, EnumCannel cannel) {
    }

    public void onConfigReload(PlayerEntity player) {
    }

    public void update() {
        needUpdate = true;
    }
}
