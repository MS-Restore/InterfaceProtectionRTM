package fun.bm.interfaceprotectionrtm.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import fun.bm.interfaceprotectionrtm.config.ConfigsInstance;
import fun.bm.interfaceprotectionrtm.data.AbstractDataStore;
import fun.bm.interfaceprotectionrtm.enums.EnumCannel;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class ServerConfigCommand extends AbstractConfigCommand {

    public ServerConfigCommand(AbstractDataStore dataStore, ConfigsInstance configfile) {
        super(true, dataStore, configfile);
    }

    @Override
    public void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher
                    .register(
                            CommandManager
                                    .literal(this.name)
                                    .requires(source -> source.hasPermissionLevel(4))
                                    .then(
                                            CommandManager
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
                                            CommandManager
                                                    .literal("set")
                                                    .then(
                                                            CommandManager
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
                                                                                builder = builder.createOffset(builder.getInput().lastIndexOf(' ') + dotIndex + 2);
                                                                                for (String s : configfile.completeConfigPath(path)) {
                                                                                    builder.suggest(s.substring(path.lastIndexOf('.') + 1));
                                                                                }
                                                                                return builder.buildFuture();
                                                                            })
                                                                    .executes(
                                                                            context -> {
                                                                                String key = StringArgumentType.getString(context, "key");
                                                                                String value = configfile.getConfig(key);
                                                                                context.getSource().sendMessage(Text.literal("设置项" + key + "当前值: " + value));
                                                                                return 1;
                                                                            }
                                                                    )
                                                                    .then(
                                                                            CommandManager
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
                                                                                                context.getSource().sendMessage(Text.literal("设置项" + key + "已设置为: " + value));
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
