package fun.bm.interfaceprotectionrtm.packet.packets;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class C2SResourceLoading implements FabricPacket {
    public static final PacketType<C2SCannelSubcribe> TYPE =
            PacketType.create(new Identifier("interfaceprotectionrtm", "resource_loading"), C2SCannelSubcribe::new);

    private final boolean isLoading;
    private final long ClientSideStartTime;

    public C2SResourceLoading(boolean loading, long clientSideStartTime) {
        this.isLoading = loading;
        ClientSideStartTime = clientSideStartTime;
    }

    public C2SResourceLoading(PacketByteBuf buf) {
        this.isLoading = buf.readBoolean();
        this.ClientSideStartTime = buf.readLong();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(isLoading);
        buf.writeLong(ClientSideStartTime);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public long getClientSideStartTime() {
        return ClientSideStartTime;
    }
}
