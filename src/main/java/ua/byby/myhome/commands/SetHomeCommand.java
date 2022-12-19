package ua.byby.myhome.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.byby.myhome.models.Home;
import ua.byby.myhome.util.Message;

import java.util.Optional;

public class SetHomeCommand implements CommandExecutor, ua.byby.myhome.util.Command {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        Optional<Home> home = homeDAO.getHome(player.getName());
        if(home.isPresent()) {
            home.get().setLocation(player.getLocation());
            homeDAO.updateHome(home.get());
        } else {
            homeDAO.createHome(player.getName(), player.getLocation());
        }

        player.sendMessage(Message.SUCCESS.toString());
        return perform();
    }

    @Override
    public boolean perform() {
        return true;
    }
}
