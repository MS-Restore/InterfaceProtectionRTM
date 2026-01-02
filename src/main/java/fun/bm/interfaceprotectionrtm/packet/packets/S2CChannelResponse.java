package fun.bm.interfaceprotectionrtm.packet.packets;

import fun.bm.interfaceprotectionrtm.data.subscribes.SubscribedPlayer;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.UUID;

public class S2CChannelResponse implements FabricPacket {
    public static final PacketType<S2CChannelResponse> TYPE =
            PacketType.create(new Identifier("interfaceprotectionrtm", "channel_response"), S2CChannelResponse::new);
    private final boolean success;
    private IntList channelIds;
    private String name;
    private UUID uuid;

    public S2CChannelResponse() {
        this.success = false; // return false by if missing data
    }

    public S2CChannelResponse(SubscribedPlayer player) {
        boolean success;
        try {
            Set<Integer> channelIds = player.subscribedChannels;
            this.channelIds = new IntImmutableList(channelIds);
            this.name = player.name;
            this.uuid = player.uuid;
            success = true;
        } catch (Exception e) {
            success = false;
        }
        this.success = success;
    }

    public S2CChannelResponse(IntList channelIds, String name, UUID uuid) {
        this.success = true;
        this.channelIds = channelIds;
        this.name = name;
        this.uuid = uuid;
    }

    public S2CChannelResponse(PacketByteBuf buf) {
        this.success = buf.readBoolean();
        if (!success) return;
        this.channelIds = buf.readIntList();
        this.name = buf.readString();
        this.uuid = buf.readUuid();
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.success);
        if (!success) return;
        buf.writeIntList(this.channelIds);
        buf.writeString(this.name);
        buf.writeUuid(this.uuid);
    }

    public boolean isSuccess() {
        return this.success;
    }

    public IntList getChannelIds() {
        return success ? this.channelIds : null;
    }

    public String getName() {
        return success ? this.name : null;
    }

    public UUID getUuid() {
        return success ? this.uuid : null;
    }
}
