package fun.bm.interfaceprotectionrtm.client.event.events;

import fun.bm.interfaceprotectionrtm.client.data.ClientDataStore;
import fun.bm.interfaceprotectionrtm.packet.packets.C2SCannelSubcribe;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

import java.util.List;

public class PlayerLoginEvent {
    public static void register() {
        ClientPlayConnectionEvents.JOIN.register(PlayerLoginEvent::processEvent);
    }

    private static void processEvent(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient minecraftClient) {
        List<Integer> list = ClientDataStore.INSTANCE.getOrLoadValues();
        C2SCannelSubcribe packet = new C2SCannelSubcribe(new IntImmutableList(list));
        ClientPlayNetworking.send(packet);
    }
}
