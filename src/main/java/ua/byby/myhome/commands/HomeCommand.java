package ua.byby.myhome.commands;

import org.bukkit.entity.Player;
import ua.byby.myhome.MyHomePlugin;
import ua.byby.myhome.dao.HomeDAO;
import ua.byby.myhome.models.Home;
import ua.byby.myhome.util.Command;
import ua.byby.myhome.util.Message;

public class HomeCommand implements Command {

    private Player player;
    private String homeOwnerNick;
    private Home home;

    public HomeCommand(Player player) {
        this.player = player;
    }

    public HomeCommand(Player player, String homeOwnerNick) {
        this.player = player;
        this.homeOwnerNick = homeOwnerNick;
    }

    @Override
    public boolean perform() {
        findHome();
        homeTeleportation();
        return true;
    }

    private void findHome() {
        if(homeOwnerNick != null) {
            home = homeDAO.getHome(homeOwnerNick);
            return;
        }

        home = homeDAO.getHome(player.getName());
    }

    private boolean homeTeleportation() {
        if(home == null) {
            player.sendMessage(Message.HOME_DOESNT_EXIST.toString());
            return false;
        }

        Home home = this.home;
        if(home.isPrivate()) {
            if(!MyHomePlugin.getInstance().getHomeUserDAO().hasHomeAccess(homeOwnerNick, home.getHomeId())) {
                player.sendMessage(Message.NO_ACCESS.toString());
                return false;
            }
        }

        player.teleport(home.getLocation());
        return true;
    }
}
