package fun.bm.interfaceprotectionrtm.command;

import fun.bm.interfaceprotectionrtm.config.ConfigsInstance;
import fun.bm.interfaceprotectionrtm.data.AbstractDataStore;

public abstract class AbstractConfigCommand extends AbstractCommand {
    protected final AbstractDataStore dataStore;
    protected final ConfigsInstance configfile;

    public AbstractConfigCommand(boolean serverSide, AbstractDataStore dataStore, ConfigsInstance configfile) {
        super("ifp" + (serverSide ? "s" : "c") + "config");
        this.dataStore = dataStore;
        this.configfile = configfile;
    }

    @Override
    public abstract void register();
}
