package fun.bm.interfaceprotectionrtm.data.subscribes;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SubscribeManager {
    public static Set<SubscribedPlayer> subscribed_players = new HashSet<>();

    public static SubscribedPlayer set(ServerPlayerEntity player, Set<Integer> channels) {
        remove(player);
        SubscribedPlayer subscribe = add(player);
        channels.forEach(subscribe::addChannel);
        return subscribe;
    }

    public static SubscribedPlayer subscribe(ServerPlayerEntity player, Set<Integer> channels) {
        SubscribedPlayer subscribe = getOrAdd(player);
        channels.forEach(subscribe::addChannel);
        return subscribe;
    }

    public static Pair<SubscribedPlayer, Boolean> subscribe(ServerPlayerEntity player, int cancel, boolean subscribe) {
        SubscribedPlayer sp;
        boolean success;
        if (subscribe) {
            sp = getOrAdd(player);
            success = sp.addChannel(cancel);
        } else {
            sp = get(player);
            if (sp == null) {
                success = false;
            } else {
                success = sp.removeChannel(cancel);
                if (sp.subscribedChannels.isEmpty()) {
                    subscribed_players.remove(sp);
                    sp = null;
                }
            }
        }
        return new Pair<>(sp, success);
    }

    private static SubscribedPlayer getOrAdd(ServerPlayerEntity player) {
        return getOrAdd(player.getName().getString(), player.getUuid());
    }

    private static SubscribedPlayer getOrAdd(String name, UUID uuid) {
        SubscribedPlayer sp = get(name, uuid);
        return sp != null ? sp : add(name, uuid);
    }

    public static SubscribedPlayer get(ServerPlayerEntity player) {
        return get(player.getName().getString(), player.getUuid());
    }

    private static SubscribedPlayer get(String name, UUID uuid) {
        for (SubscribedPlayer subscribed_player : subscribed_players) {
            if (subscribed_player.name.equals(name) && subscribed_player.uuid.equals(uuid)) {
                return subscribed_player;
            }
        }
        return null;
    }

    private static SubscribedPlayer add(String name, UUID uuid) {
        SubscribedPlayer player = new SubscribedPlayer(name, uuid);
        subscribed_players.add(player);
        return player;
    }

    private static SubscribedPlayer add(ServerPlayerEntity player) {
        return add(player.getName().getString(), player.getUuid());
    }

    public static void clean(MinecraftServer server) {
        for (SubscribedPlayer player : subscribed_players) {
            if (player.subscribedChannels.isEmpty()) {
                subscribed_players.remove(player);
            }
            if (server.getPlayerManager().getPlayer(player.uuid) == null
                    && server.getPlayerManager().getPlayer(player.name) == null) {
                subscribed_players.remove(player);
            }
        }
    }

    public static void remove(ServerPlayerEntity player) {
        for (SubscribedPlayer subscribed_player : subscribed_players) {
            if (subscribed_player.uuid.equals(player.getUuid())
                    || subscribed_player.name.equals(player.getName().getString())) {
                subscribed_players.remove(subscribed_player);
                return;
            }
        }
    }
}
