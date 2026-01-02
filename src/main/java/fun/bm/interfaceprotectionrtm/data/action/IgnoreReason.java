package fun.bm.interfaceprotectionrtm.data.action;

import fun.bm.interfaceprotectionrtm.enums.EnumCannel;

public class IgnoreReason {
    public final EnumCannel cannel;
    public int timer = 0;
    public long lastActionTime = -1;
    private Object obj = null;

    public IgnoreReason(EnumCannel cannel, long lastActionTime) {
        this.cannel = cannel;
        this.lastActionTime = lastActionTime;
    }

    public IgnoreReason(EnumCannel cannel, long commitTime, Object obj) {
        this.cannel = cannel;
        this.lastActionTime = commitTime;
        this.obj = obj;
    }

    public IgnoreReason(EnumCannel cannel) {
        this.cannel = cannel;
    }

    public boolean tick() {
        timer++;
        return cannel == EnumCannel.LOGIN && timer >= 20 * 60; // 1 minute
    }

    public long getLastActionTime() {
        return lastActionTime;
    }

    public Object getObj() {
        return obj;
    }
}
