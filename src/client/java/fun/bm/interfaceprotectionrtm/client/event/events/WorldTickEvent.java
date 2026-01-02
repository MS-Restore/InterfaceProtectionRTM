package fun.bm.interfaceprotectionrtm.client.event.events;

import fun.bm.interfaceprotectionrtm.client.data.StatusHolder;
import fun.bm.interfaceprotectionrtm.packet.packets.C2SESCScreen;
import fun.bm.interfaceprotectionrtm.packet.packets.C2SResourceLoading;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class WorldTickEvent {
    public static void register() {
        ClientTickEvents.START_WORLD_TICK.register(client -> {
            long time = System.currentTimeMillis();
            {
                StatusHolder.Status status = StatusHolder.resourceLoadStatus;
                if (status.startTime == 0) return;
                if (status.firstTick) {
                    if (time - status.startTime < 60 * 1000) {
                        sendPacketResource(status.enabled, status.startTime);
                    } else {
                        sendPacketResource(false, time);
                        StatusHolder.updateResourceLoadStatus(false);
                    }
                } else if (time - status.startTime > 60 * 1000 && status.enabled) {
                    sendPacketResource(false, time);
                    StatusHolder.updateResourceLoadStatus(false);
                }
                status.firstTick = false;
            }
            {
                StatusHolder.Status status = StatusHolder.escScreenStatus;
                if (status.startTime == 0) return;
                if (status.firstTick) {
                    if (time - status.startTime < 60 * 1000) {
                        sendPacketEsc(status.enabled, status.startTime);
                    } else {
                        sendPacketEsc(false, time);
                        StatusHolder.updateEscScreenStatus(false);
                    }
                } else if (time - status.startTime > 60 * 1000 && status.enabled) {
                    sendPacketEsc(false, time);
                    StatusHolder.updateEscScreenStatus(false);
                }
                status.firstTick = false;
            }
        });
    }

    private static void sendPacketResource(boolean inLoading, long time) {
        C2SResourceLoading packet = new C2SResourceLoading(inLoading, time);
        ClientPlayNetworking.send(packet);
    }

    private static void sendPacketEsc(boolean inLoading, long time) {
        C2SESCScreen packet = new C2SESCScreen(inLoading, time);
        ClientPlayNetworking.send(packet);
    }
}
