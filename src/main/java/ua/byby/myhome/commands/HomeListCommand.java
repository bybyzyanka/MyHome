package ua.byby.myhome.commands;

import org.bukkit.entity.Player;
import ua.byby.myhome.MyHomePlugin;
import ua.byby.myhome.models.Home;
import ua.byby.myhome.util.Command;
import ua.byby.myhome.util.Message;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HomeListCommand implements Command {

    private Player player;

    public HomeListCommand(Player player) {
        this.player = player;
    }

    @Override
    public boolean perform() {
        AtomicReference<String> list = new AtomicReference<>(Message.LIST.toString());
        List<Home> homes = homeUserDAO.getHomesWithAccess(player.getName());
        if(homes.isEmpty()) {
            return true;
        }

        homes.forEach(home -> list.set(list + userDAO.getUserById(home.getUserId()).getNick() + ", "));

        player.sendMessage(list.get().substring(0, list.get().length() - 2));
        return true;
    }
}
