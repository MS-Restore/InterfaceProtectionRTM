package fun.bm.interfaceprotectionrtm.enums;

public enum EnumConfigCategory {
    SERVER("server"),
    CLIENT("client"),
    REMOVED("removed"); // removed config

    private final String baseKeyName;

    EnumConfigCategory(String baseKeyName) {
        this.baseKeyName = baseKeyName;
    }

    public String getBaseKeyName() {
        return this.baseKeyName;
    }
}