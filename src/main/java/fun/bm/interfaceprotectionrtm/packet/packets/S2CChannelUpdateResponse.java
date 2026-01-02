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

public class S2CChannelUpdateResponse implements FabricPacket {
    public static final PacketType<S2CChannelUpdateResponse> TYPE =
            PacketType.create(new Identifier("interfaceprotectionrtm", "channel_update_response"), S2CChannelUpdateResponse::new);
    private final int updatedId;
    private final boolean success;
    private final boolean missingData;
    private IntList channelIds;
    private String name;
    private UUID uuid;

    public S2CChannelUpdateResponse(SubscribedPlayer player, int updatedId, boolean success) {
        this.updatedId = updatedId;
        this.success = success;
        if (success && player == null) {
            this.missingData = true;
        } else {
            Set<Integer> channelIds = player.subscribedChannels;
            this.channelIds = new IntImmutableList(channelIds);
            this.name = player.name;
            this.uuid = player.uuid;
            this.missingData = false;
        }
    }

    public S2CChannelUpdateResponse(PacketByteBuf buf) {
        this.success = buf.readBoolean();
        this.missingData = buf.readBoolean();
        this.updatedId = buf.readInt();
        if (missingData) return;
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
        buf.writeBoolean(this.missingData);
        buf.writeInt(this.updatedId);
        if (missingData) return;
        buf.writeIntList(this.channelIds);
        buf.writeString(this.name);
        buf.writeUuid(this.uuid);
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean isMissingData() {
        return this.missingData;
    }

    public int getUpdatedId() {
        return this.updatedId;
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
