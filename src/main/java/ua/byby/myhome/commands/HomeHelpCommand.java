package ua.byby.myhome.commands;

import org.bukkit.entity.Player;
import ua.byby.myhome.util.Command;
import ua.byby.myhome.util.Message;
import ua.byby.myhome.util.Permission;

public class HomeHelpCommand implements Command {

    private Player player;

    public HomeHelpCommand(Player player) {
        this.player = player;
    }

    @Override
    public boolean perform() {
        player.sendMessage(Message.HELP_HOME.toString());
        player.sendMessage(Message.HELP_INVITE.toString());
        player.sendMessage(Message.HELP_UNINVITE.toString());
        player.sendMessage(Message.HELP_LIST.toString());
        player.sendMessage(Message.HELP_ILIST.toString());
        player.sendMessage(Message.HELP_DELETE.toString());
        player.sendMessage(Message.HELP_HOME.toString());

        if(player.hasPermission(Permission.ADMIN)) {
            player.sendMessage(Message.HELP_RELOAD.toString());
        }

        return true;
    }
}
