package fun.bm.interfaceprotectionrtm.packet.packets;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class C2SCannelUpdateSubscribe implements FabricPacket {
    public static final PacketType<C2SCannelSubcribe> TYPE =
            PacketType.create(new Identifier("interfaceprotectionrtm", "channel_update_subscribe"), C2SCannelSubcribe::new);

    private final int updatedId;
    private final boolean subscribe;

    public C2SCannelUpdateSubscribe(int updatedId, boolean subscribe) {
        this.updatedId = updatedId;
        this.subscribe = subscribe;
    }

    public C2SCannelUpdateSubscribe(PacketByteBuf buf) {
        this.updatedId = buf.readInt();
        this.subscribe = buf.readBoolean();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(updatedId);
        buf.writeBoolean(subscribe);
    }

    public int getUpdatedId() {
        return updatedId;
    }

    public boolean toSubscribe() {
        return subscribe;
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
