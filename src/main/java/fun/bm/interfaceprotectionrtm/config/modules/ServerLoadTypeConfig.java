package fun.bm.interfaceprotectionrtm.config.modules;

import fun.bm.interfaceprotectionrtm.config.IConfigModule;
import fun.bm.interfaceprotectionrtm.config.flags.ConfigClassInfo;
import fun.bm.interfaceprotectionrtm.config.flags.ConfigInfo;
import fun.bm.interfaceprotectionrtm.enums.EnumConfigCategory;
import fun.bm.interfaceprotectionrtm.enums.EnumServerLoadType;

@ConfigClassInfo(category = EnumConfigCategory.SERVER, comments = """
        Available value:
        DEFAULT
        FORCEENABLE
        FORCEDISABLE""")
public class ServerLoadTypeConfig implements IConfigModule {
    @ConfigInfo(name = "login")
    public static EnumServerLoadType login = EnumServerLoadType.DEFAULT;
    @ConfigInfo(name = "loadResourcePacks")
    public static EnumServerLoadType loadResourcePacks = EnumServerLoadType.DEFAULT;
    @ConfigInfo(name = "inEscScreen")
    public static EnumServerLoadType inEscScreen = EnumServerLoadType.DEFAULT;
}
