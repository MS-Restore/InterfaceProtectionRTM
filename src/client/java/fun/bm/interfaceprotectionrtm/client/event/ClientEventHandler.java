package fun.bm.interfaceprotectionrtm.client.event;

import fun.bm.interfaceprotectionrtm.client.event.events.PlayerLoginEvent;
import fun.bm.interfaceprotectionrtm.client.event.events.WorldTickEvent;

public class ClientEventHandler {
    public static void register() {
        PlayerLoginEvent.register();
        WorldTickEvent.register();
    }
}
