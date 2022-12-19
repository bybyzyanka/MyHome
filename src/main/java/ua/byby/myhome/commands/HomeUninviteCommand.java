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

        Optional<Home> home = homeDAO.getHome(player.getName());
        if(!home.isPresent()) {
            player.sendMessage(Message.HOME_DOESNT_EXIST.toString());
            return true;
        }

        int invitedUserId = userDAO.getUser(uninvited.getName()).get().getUserId();
        if(!homeUserDAO.hasHomeAccess(home.get().getHomeId(), invitedUserId)) {
            player.sendMessage(Message.PLAYER_DOESNT_HAVE_ACCESS.toString());
            return true;
        }

        homeUserDAO.removeHomeAccess(home.get().getHomeId(), invitedUserId);
        player.sendMessage(Message.SUCCESS.toString());
        uninvited.sendMessage(Message.UNINVITED.toString().replace(Placeholder.NICK, player.getName()));

        return true;
    }
}
