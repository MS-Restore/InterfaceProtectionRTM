package fun.bm.interfaceprotectionrtm.enums;

public enum EnumCannel {
    LOGIN(0, true, true),
    LOADRESOURCEPACKS(1, true, false),
    INESCSCREEN(2, false, false);

    final int cannelId;
    final boolean defaultClient;
    final boolean defaultServer;

    EnumCannel(int id, boolean defaultClient, boolean defaultServer) {
        this.cannelId = id;
        this.defaultClient = defaultClient;
        this.defaultServer = defaultServer;
    }

    public static EnumCannel getCannel(int id) {
        for (EnumCannel cannel : values()) {
            if (cannel.cannelId == id) {
                return cannel;
            }
        }
        return null;
    }

    public int getCannelId() {
        return this.cannelId;
    }

    public boolean isDefaultClient() {
        return this.defaultClient;
    }

    public boolean isDefaultServer() {
        return this.defaultServer;
    }
}
