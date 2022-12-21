package ua.byby.myhome.commands;

import org.bukkit.entity.Player;
import ua.byby.myhome.models.Home;
import ua.byby.myhome.util.Command;
import ua.byby.myhome.util.Message;

import java.util.Optional;

public class HomePublicCommand implements Command {

    private Player player;

    public HomePublicCommand(Player player) {
        this.player = player;
    }

    @Override
    public boolean perform() {
        Home home = homeDAO.getHome(player.getName());
        if(home == null) {
            player.sendMessage(Message.HOME_DOESNT_EXIST.toString());
            return true;
        }

        home.setPrivacy(false);
        homeDAO.updateHome(home);
        player.sendMessage(Message.PUBLIC.toString());

        return true;
    }
}
