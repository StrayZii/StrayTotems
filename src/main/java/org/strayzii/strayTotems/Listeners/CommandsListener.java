package org.strayzii.strayTotems.Listeners;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.strayzii.strayTotems.StrayTotems;

import java.util.List;

public class CommandsListener implements TabExecutor {
    private final StrayTotems plugin;

    public CommandsListener(@NotNull StrayTotems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            return true;
        }

        String subCommand = args[0];

        switch (subCommand.toLowerCase()) {
            case "get":
                String prefix = plugin.getConfig().getString("messages.prefix", "§x§4§4§9§8§D§Bᴛᴏᴛᴇᴍs &8»&r");
                if (!sender.hasPermission("straytotems.get")) {
                    String msg = plugin.getConfig().getString("messages.no-permission");

                    msg = msg.replace("$prefix", prefix);

                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', msg)
                    );
                    return true;
                }

                String msg = plugin.getConfig().getString("messages.commands.get", "$prefix §x§B§5§C§4§E§0ВМаксимум тотемов на данный момент: $amount");

                msg = msg.replace("$prefix", prefix);
                msg = msg.replace("$amount", String.valueOf(plugin.getConfig().getInt("max-totems", 3)));

                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', msg)
                );
                break;
            case "set":
                if (args.length < 2) return true;
                String prfx = plugin.getConfig().getString("messages.prefix", "§x§4§4§9§8§D§Bᴛᴏᴛᴇᴍs &8»&r");
                if (!sender.hasPermission("straytotems.set")) {
                    String msga = plugin.getConfig().getString("messages.no-permission");

                    msga = msga.replace("$prefix", prfx);

                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', msga)
                    );
                    return true;
                }

                int newAmount = Integer.parseInt(args[1]);
                if (newAmount == 0) return true;

                int oldAmount = plugin.getConfig().getInt("max-totems", newAmount);

                plugin.getConfig().set("max-totems", newAmount);
                plugin.saveConfig();
                plugin.getUtils().checkEveryone();

                String msga = plugin.getConfig().getString("messages.commands.set", "$prefix §x§B§5§C§4§E§0ВМаксимум тотемов был изменён. $old -> $new");
                msga = msga.replace("$prefix", prfx);
                msga = msga.replace("$old", String.valueOf(oldAmount));
                msga = msga.replace("$new", String.valueOf(newAmount));

                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', msga)
                );

                break;
            case "reload":
                String praf = plugin.getConfig().getString("messages.prefix", "§x§4§4§9§8§D§Bᴛᴏᴛᴇᴍs &8»&r");
                if (!sender.hasPermission("straytotems.reload")) {
                    String msgaa = plugin.getConfig().getString("messages.no-permission");

                    msgaa = msgaa.replace("$prefix", praf);

                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', msgaa)
                    );
                    return true;
                }

                plugin.reloadConfig();
                plugin.getUtils().checkEveryone();

                String msgaa = plugin.getConfig().getString("messages.commands.reload", "$prefix §x§B§5§C§4§E§0ВКонфигурация была успешно перезагружена");
                msgaa = msgaa.replace("$prefix", praf);
                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', msgaa)
                );

                break;
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command cmd,
            @NotNull String alias,
            @NotNull String @NotNull [] args
    ) {
        if (args.length == 1) {
            return List.of("get", "set", "reload")
                    .stream()
                    .filter(command -> command.startsWith(args[0].toLowerCase()))
                    .filter(command -> switch (command) {
                        case "get" -> sender.hasPermission("straytotems.get");
                        case "set" -> sender.hasPermission("straytotems.set");
                        case "reload" -> sender.hasPermission("straytotems.reload");
                        default -> false;
                    })
                    .toList();
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            if (!sender.hasPermission("straytotems.set")) {
                return List.of();
            }

            String currentAmount = String.valueOf(
                    plugin.getConfig().getInt("max-totems", 3)
            );

            return List.of(currentAmount)
                    .stream()
                    .filter(value -> value.startsWith(args[1]))
                    .toList();
        }

        return List.of();
    }
}
