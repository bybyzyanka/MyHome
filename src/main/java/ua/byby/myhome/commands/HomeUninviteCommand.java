package ua.byby.myhome.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ua.byby.myhome.models.Home;
import ua.byby.myhome.util.Command;
import ua.byby.myhome.util.Message;
import ua.byby.myhome.util.Placeholder;

import java.util.Optional;

public class HomeUninviteCommand implements Command {

    private Player player;
    private String uninvitedNick;

    public HomeUninviteCommand(Player player, String uninvitedNick) {
        this.player = player;
        this.uninvitedNick = uninvitedNick;
    }

    @Override
    public boolean perform() {
        Player uninvited = Bukkit.getPlayer(uninvitedNick);
        if(uninvited == null) {
            player.sendMessage(Message.PLAYER_DOESNT_EXIST.toString());
            return true;
        }

        Home home = homeDAO.getHome(player.getName());
        if(home == null) {
            player.sendMessage(Message.HOME_DOESNT_EXIST.toString());
            return true;
        }

        int invitedUserId = userDAO.getUser(uninvited.getName()).getUserId();
        if(!homeUserDAO.hasHomeAccess(home.getHomeId(), invitedUserId)) {
            player.sendMessage(Message.PLAYER_DOESNT_HAVE_ACCESS.toString());
            return true;
        }

        homeUserDAO.removeHomeAccess(home.getHomeId(), invitedUserId);
        player.sendMessage(Message.SUCCESS.toString());
        uninvited.sendMessage(Message.UNINVITED.toString().replace(Placeholder.NICK, player.getName()));

        return true;
    }
}
