package fun.bm.interfaceprotectionrtm.data.defaults;

import fun.bm.interfaceprotectionrtm.enums.EnumCannel;

public class DefaultValue {
    public final EnumCannel cannel;
    public final boolean enabled;
    public boolean force;

    public DefaultValue(EnumCannel cannel, boolean enabled) {
        this.cannel = cannel;
        this.enabled = enabled;
    }

    public DefaultValue(EnumCannel cannel, boolean enabled, boolean force) {
        this.cannel = cannel;
        this.enabled = enabled;
        this.force = force;
    }
}
