package ua.byby.myhome.util;

import ua.byby.myhome.MyHomePlugin;
import ua.byby.myhome.dao.HomeDAO;
import ua.byby.myhome.dao.HomeUserDAO;
import ua.byby.myhome.dao.UserDAO;

public interface IDAO {

    MyHomePlugin plugin = MyHomePlugin.getInstance();

    UserDAO userDAO = MyHomePlugin.getInstance().getUserDAO();
    HomeDAO homeDAO = MyHomePlugin.getInstance().getHomeDAO();
    HomeUserDAO homeUserDAO = MyHomePlugin.getInstance().getHomeUserDAO();
}
