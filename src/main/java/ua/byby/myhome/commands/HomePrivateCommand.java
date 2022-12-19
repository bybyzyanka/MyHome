package ua.byby.myhome.commands;

import org.bukkit.entity.Player;
import ua.byby.myhome.models.Home;
import ua.byby.myhome.util.Command;
import ua.byby.myhome.util.Message;

import java.util.Optional;

public class HomePrivateCommand implements Command {

    private Player player;

    public HomePrivateCommand(Player player) {
        this.player = player;
    }

    @Override
    public boolean perform() {
        Optional<Home> home = homeDAO.getHome(player.getName());
        if(!home.isPresent()) {
            player.sendMessage(Message.HOME_DOESNT_EXIST.toString());
            return true;
        }

        home.get().setPrivacy(true);
        homeDAO.updateHome(home.get());

        return true;
    }
}