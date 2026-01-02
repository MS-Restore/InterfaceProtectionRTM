package fun.bm.interfaceprotectionrtm.client.config.modules;

import fun.bm.interfaceprotectionrtm.client.enums.EnumClientLoadType;
import fun.bm.interfaceprotectionrtm.config.IConfigModule;
import fun.bm.interfaceprotectionrtm.config.flags.ConfigClassInfo;
import fun.bm.interfaceprotectionrtm.config.flags.ConfigInfo;
import fun.bm.interfaceprotectionrtm.enums.EnumConfigCategory;

@ConfigClassInfo(category = EnumConfigCategory.CLIENT, comments = """
        Available value:
        DEFAULT
        ENABLE
        DISABLE""")
public class ClientLoadTypeConfig implements IConfigModule {
    @ConfigInfo(name = "login")
    public static EnumClientLoadType login = EnumClientLoadType.DEFAULT;
    @ConfigInfo(name = "loadResourcePacks")
    public static EnumClientLoadType loadResourcePacks = EnumClientLoadType.DEFAULT;
    @ConfigInfo(name = "inEscScreen")
    public static EnumClientLoadType inEscScreen = EnumClientLoadType.DEFAULT;
}
