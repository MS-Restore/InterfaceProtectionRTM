package fun.bm.interfaceprotectionrtm.event;

import fun.bm.interfaceprotectionrtm.event.events.PlayerLoginEvent;
import fun.bm.interfaceprotectionrtm.event.events.ServerTickEvent;

public class ServerEventHandler {
    public static void register() {
        ServerTickEvent.register();
        PlayerLoginEvent.register();
    }
}
