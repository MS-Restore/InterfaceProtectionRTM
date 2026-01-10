package fun.bm.interfaceprotectionrtm.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import fun.bm.interfaceprotectionrtm.command.AbstractConfigCommand;
import fun.bm.interfaceprotectionrtm.config.ConfigsInstance;
import fun.bm.interfaceprotectionrtm.data.AbstractDataStore;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.command.CommandException;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class ClientConfigCommand extends AbstractConfigCommand {

    public ClientConfigCommand(AbstractDataStore dataStore, ConfigsInstance configfile) {
        super(false, dataStore, configfile);
    }

    @Override
    public void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher
                    .register(
                            ClientCommandManager
                                    .literal(this.name)
                                    .then(
                                            ClientCommandManager
                                                    .literal("reload")
                                                    .executes(
                                                            context -> {
                                                                configfile.reload();
                                                                dataStore.update();
                                                                dataStore.onConfigReload(context.getSource().getPlayer());
                                                                return 1;
                                                            })

                                    )
                                    .then(
                                            ClientCommandManager
                                                    .literal("set")
                                                    .then(
                                                            ClientCommandManager
                                                                    .argument("key", StringArgumentType.string())
                                                                    .suggests(
                                                                            (context, builder) -> {
                                                                                String path;
                                                                                try {
                                                                                    path = StringArgumentType.getString(context, "key");
                                                                                } catch (Exception e) {
                                                                                    path = "";
                                                                                }
                                                                                int dotIndex = path.lastIndexOf(".");
                                                                                int spaceIndex = builder.getInput().lastIndexOf(' ');
                                                                                int safeOffset = Math.max(0, spaceIndex + dotIndex + 2);
                                                                                safeOffset = Math.min(safeOffset, builder.getInput().length());
                                                                                builder = builder.createOffset(safeOffset);

                                                                                for (String s : configfile.completeConfigPath(path)) {
                                                                                    builder.suggest(s.substring(path.lastIndexOf('.') + 1));
                                                                                }
                                                                                return builder.buildFuture();
                                                                            })
                                                                    .executes(
                                                                            context -> {
                                                                                String key = StringArgumentType.getString(context, "key");
                                                                                String value = configfile.getConfig(key);
                                                                                context.getSource().sendFeedback(Text.literal("设置项" + key + "当前值: " + value));
                                                                                return 1;
                                                                            }
                                                                    )
                                                                    .then(
                                                                            ClientCommandManager
                                                                                    .argument("value", StringArgumentType.string())
                                                                                    .suggests(
                                                                                            (context, builder) -> {
                                                                                                String path;
                                                                                                try {
                                                                                                    path = StringArgumentType.getString(context, "key");
                                                                                                } catch (Exception e) {
                                                                                                    path = "";
                                                                                                }
                                                                                                if (!configfile.getAllConfigPaths("").contains(path)) {
                                                                                                    return builder
                                                                                                            .suggest("<ERROR CONFIG>", Text.literal("This config path does not exist."))
                                                                                                            .buildFuture();
                                                                                                }
                                                                                                Object value = configfile.getConfigOrigin(path);
                                                                                                String[] suggestions = configfile.getConfigSuggestions(path);
                                                                                                builder.suggest(value.toString(), Text.literal("Default value")
                                                                                                        .styled(style -> style.withColor(TextColor.fromFormatting(Formatting.GRAY))));
                                                                                                if (suggestions == null) {
                                                                                                    if (value instanceof Boolean) {
                                                                                                        builder.suggest(String.valueOf(!(Boolean) value));
                                                                                                    } else if (value instanceof Enum<?> enumValue) {
                                                                                                        Enum<?>[] values = enumValue.getClass().getEnumConstants();
                                                                                                        for (Enum<?> enumValue1 : values) {
                                                                                                            if (enumValue1 == value)
                                                                                                                continue;
                                                                                                            builder.suggest(enumValue1.name());
                                                                                                        }
                                                                                                    }
                                                                                                } else {
                                                                                                    for (String s : suggestions) {
                                                                                                        if (!Objects.equals(s, value.toString())) {
                                                                                                            builder.suggest(s);
                                                                                                        }
                                                                                                    }
                                                                                                }

                                                                                                return builder.buildFuture();
                                                                                            }
                                                                                    )
                                                                                    .executes(
                                                                                            context -> {
                                                                                                String key = StringArgumentType.getString(context, "key");
                                                                                                String value = StringArgumentType.getString(context, "value");
                                                                                                configfile.setConfig(key, value);
                                                                                                configfile.saveConfigs();
                                                                                                configfile.reload();
                                                                                                dataStore.update();
                                                                                                EnumCannel cannel = null;
                                                                                                for (EnumCannel cannel0 : EnumCannel.values()) {
                                                                                                    if (key.toLowerCase().contains(cannel0.name().toLowerCase())) {
                                                                                                        cannel = cannel0;
                                                                                                    }
                                                                                                }
                                                                                                if (cannel == null)
                                                                                                    throw new CommandException(Text.literal("未找到此设置项"));
                                                                                                dataStore.onConfigUpdate(context.getSource().getPlayer(), cannel);
                                                                                                context.getSource().sendFeedback(Text.literal("设置项" + key + "已设置为: " + value));
                                                                                                return 1;
                                                                                            }
                                                                                    )
                                                                    )
                                                    )
                                    )
                    );
        });
    }
}
