package fun.bm.interfaceprotectionrtm.data.subscribes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SubscribedPlayer {
    public String name; // fallback key to identify
    public UUID uuid; // primary key to identify
    public Set<Integer> subscribedChannels;

    public SubscribedPlayer(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.subscribedChannels = new HashSet<>();
    }

    public boolean addChannel(int channel) {
        return this.subscribedChannels.add(channel);
    }

    public boolean removeChannel(int channel) {
        return this.subscribedChannels.remove(channel);
    }
}
