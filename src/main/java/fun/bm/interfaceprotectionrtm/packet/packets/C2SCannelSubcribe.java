package fun.bm.interfaceprotectionrtm.packet.packets;

import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class C2SCannelSubcribe implements FabricPacket {
    public static final PacketType<C2SCannelSubcribe> TYPE =
            PacketType.create(new Identifier("interfaceprotectionrtm", "channel_subscribe"), C2SCannelSubcribe::new);

    private final IntList channelIds;

    public C2SCannelSubcribe(IntList ids) {
        this.channelIds = ids;
    }

    public C2SCannelSubcribe(PacketByteBuf buf) {
        this.channelIds = buf.readIntList();
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeIntList(this.channelIds);
    }

    public IntList getChannelIds() {
        return channelIds;
    }
}
