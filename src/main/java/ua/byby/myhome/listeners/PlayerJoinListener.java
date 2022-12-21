package ua.byby.myhome.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ua.byby.myhome.MyHomePlugin;
import ua.byby.myhome.dao.UserDAO;

public class PlayerJoinListener implements Listener {

    private UserDAO userDAO = MyHomePlugin.getInstance().getUserDAO();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String nick = event.getPlayer().getName();
        if(userDAO.getUser(nick) == null) {
            userDAO.createUser(nick);
        }
    }
}
