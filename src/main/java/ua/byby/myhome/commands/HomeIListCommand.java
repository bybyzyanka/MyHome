package ua.byby.myhome.commands;

import org.bukkit.entity.Player;
import ua.byby.myhome.MyHomePlugin;
import ua.byby.myhome.models.Home;
import ua.byby.myhome.models.User;
import ua.byby.myhome.util.Command;
import ua.byby.myhome.util.Message;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class HomeIListCommand implements Command {

    private Player player;

    public HomeIListCommand(Player player) {
        this.player = player;
    }

    @Override
    public boolean perform() {
        Optional<Home> home = homeDAO.getHome(player.getName());
        if(!home.isPresent()) {
            player.sendMessage(Message.HOME_DOESNT_EXIST.toString());
            return true;
        }

        AtomicReference<String> list = new AtomicReference<>(Message.ILIST.toString());
        List<User> users = homeUserDAO.getUsersWithAccess(home.get().getHomeId());
        if(users.isEmpty()) {
            return true;
        }

        users.forEach(user -> list.set(list + user.getNick() + "; "));

        player.sendMessage(list.get().substring(0, list.get().length() - 2));
        return true;
    }
}
