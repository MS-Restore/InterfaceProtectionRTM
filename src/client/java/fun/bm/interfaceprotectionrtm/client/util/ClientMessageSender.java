package fun.bm.interfaceprotectionrtm.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ClientMessageSender {
    public static void sendMessage(String... msg) {
        for (String message : msg) {
            if (message.isEmpty()) continue;
            sendMessage(message);
        }
    }

    public static void sendMessage(String msg) {
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(
                    Text.literal(msg),
                    false
            );
        }
    }
}
