package ua.byby.myhome.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ua.byby.myhome.models.Home;
import ua.byby.myhome.util.Command;
import ua.byby.myhome.util.Message;
import ua.byby.myhome.util.Placeholder;

import java.util.Optional;

public class HomeInviteCommand implements Command {

    private Player player;
    private String invitedNick;

    public HomeInviteCommand(Player player, String invitedNick) {
        this.player = player;
        this.invitedNick = invitedNick;
    }

    @Override
    public boolean perform() {
        Player invited = Bukkit.getPlayer(invitedNick);
        if(invited == null) {
            player.sendMessage(Message.PLAYER_DOESNT_EXIST.toString());
            return true;
        }

        Home home = homeDAO.getHome(player.getName());
        if(home == null) {
            player.sendMessage(Message.HOME_DOESNT_EXIST.toString());
            return true;
        }

        int invitedUserId = userDAO.getUser(invited.getName()).getUserId();
        if(homeUserDAO.hasHomeAccess(home.getHomeId(), invitedUserId)) {
            player.sendMessage(Message.PLAYER_ALREADY_HAS_ACCESS.toString());
            return true;
        }

        homeUserDAO.giveHomeAccess(home.getHomeId(), invitedUserId);
        player.sendMessage(Message.SUCCESS.toString());
        invited.sendMessage(Message.INVITED.toString().replace(Placeholder.NICK, player.getName()));

        return true;
    }
}
