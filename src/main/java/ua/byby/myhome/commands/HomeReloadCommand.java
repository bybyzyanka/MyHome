package ua.byby.myhome.commands;

import org.bukkit.command.CommandSender;
import ua.byby.myhome.MyHomePlugin;
import ua.byby.myhome.util.Command;
import ua.byby.myhome.util.Permission;

public class HomeReloadCommand implements Command {

    private CommandSender sender;

    public HomeReloadCommand(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean perform() {
        if(sender.hasPermission(Permission.ADMIN)) {
            MyHomePlugin.getInstance().reloadConfig();
            return true;
        }

        return false;
    }
}
