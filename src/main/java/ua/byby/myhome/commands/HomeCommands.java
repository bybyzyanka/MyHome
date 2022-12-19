package ua.byby.myhome.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.byby.myhome.util.Message;

import java.util.Arrays;

public class HomeCommands implements CommandExecutor {

    enum SubCommand {
        DELETE,
        LIST,
        ILIST,
        HELP,
        RELOAD,
        PRIVATE,
        PUBLIC,
        INVITE,
        UNINVITE;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args == null || args.length == 0) {
                new HomeCommand(player).perform();
                return true;
            } else {
                String value = args[0];
                if(!Arrays.stream(SubCommand.values()).anyMatch(subCommand ->
                        subCommand.name().equalsIgnoreCase(value))) {

                    new HomeCommand(player, value).perform();
                    return true;
                }

                if(value.equalsIgnoreCase(SubCommand.DELETE.name())) {
                    if(new HomeDeleteCommand(player).perform()) {
                        player.sendMessage(Message.SUCCESS.toString());
                        return true;
                    }
                }
                else if(value.equalsIgnoreCase(SubCommand.HELP.name())) {
                    return new HomeHelpCommand(player).perform();
                }
                else if(value.equalsIgnoreCase(SubCommand.LIST.name())) {
                    return new HomeListCommand(player).perform();
                }
                else if(value.equalsIgnoreCase(SubCommand.ILIST.name())) {
                    return new HomeIListCommand(player).perform();
                }
                else if(value.equalsIgnoreCase(SubCommand.PUBLIC.name())) {
                    return new HomePublicCommand(player).perform();
                }
                else if(value.equalsIgnoreCase(SubCommand.PRIVATE.name())) {
                    return new HomePrivateCommand(player).perform();
                } else if(args.length >= 2) {
                    if(value.equalsIgnoreCase(SubCommand.INVITE.name())) {
                        return new HomeInviteCommand(player, args[1]).perform();
                    }
                    else if(value.equalsIgnoreCase(SubCommand.UNINVITE.name())) {
                        return new HomeUninviteCommand(player, args[1]).perform();
                    }
                }
            }
        }

        if(args != null && args.length >= 1 && args[0].equalsIgnoreCase(SubCommand.RELOAD.name())) {
            return new HomeReloadCommand(sender).perform();
        }

        return false;
    }
}
