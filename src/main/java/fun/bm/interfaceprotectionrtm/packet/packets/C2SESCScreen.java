package fun.bm.interfaceprotectionrtm.packet.packets;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class C2SESCScreen implements FabricPacket {
    public static final PacketType<C2SCannelSubcribe> TYPE =
            PacketType.create(new Identifier("interfaceprotectionrtm", "esc"), C2SCannelSubcribe::new);

    private final boolean inEsc;
    private final long ClientSideStartTime;

    public C2SESCScreen(boolean inEsc, long clientSideStartTime) {
        this.inEsc = inEsc;
        ClientSideStartTime = clientSideStartTime;
    }

    public C2SESCScreen(PacketByteBuf buf) {
        this.inEsc = buf.readBoolean();
        this.ClientSideStartTime = buf.readLong();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(inEsc);
        buf.writeLong(ClientSideStartTime);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public boolean isInEsc() {
        return inEsc;
    }

    public long getClientSideStartTime() {
        return ClientSideStartTime;
    }
}
