package ua.byby.myhome.dao;

import ua.byby.myhome.models.Home;
import ua.byby.myhome.models.User;
import ua.byby.myhome.util.Column;
import ua.byby.myhome.util.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HomeUserDAO extends DAO {

    @Override
    public void createTable() {
        try {
            PreparedStatement statement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ? (" +
                    "? int REFERENCES ?(?)," +
                    "? int REFERENCES ?(?))," +
                    "PRIMARY KEY(?, ?)");

            statement.setString(1, Table.HOME_USER);

            statement.setString(2, Column.HOME_ID);
            statement.setString(3, Table.HOME);
            statement.setString(4, Column.HOME_ID);

            statement.setString(5, Column.USER_ID);
            statement.setString(6, Table.USER);
            statement.setString(7, Column.USER_ID);

            statement.setString(8, Column.HOME_ID);
            statement.setString(9, Column.USER_ID);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<User> getUsersWithAccess(int homeId) {
        List<User> usersWithAccess = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM ? WHERE ?=?");

            statement.setString(1, Table.HOME_USER);
            statement.setString(2, Column.HOME_ID);
            statement.setInt(3, homeId);

            ResultSet result = statement.executeQuery();
            while(result.next()) {
                usersWithAccess.add(userDAO.getUserById(result.getInt(Column.USER_ID)).get());
            }

            return usersWithAccess;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return usersWithAccess;
    }

    public List<Home> getHomesWithAccess(int userId) {
        List<Home> usersWithAccess = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM ? WHERE ?=?");

            statement.setString(1, Table.HOME_USER);
            statement.setString(2, Column.USER_ID);
            statement.setInt(3, userId);

            ResultSet result = statement.executeQuery();
            while(result.next()) {
                usersWithAccess.add(homeDAO.getHomeById(result.getInt(Column.HOME_ID)).get());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return usersWithAccess;
    }

    public List<Home> getHomesWithAccess(String nick) {
        return getHomesWithAccess(userDAO.getUser(nick).get().getUserId());
    }

    public boolean hasHomeAccess(int userId, int homeId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM ? WHERE ?=? AND ?=?");

            statement.setString(1, Table.HOME_USER);

            statement.setString(2, Column.USER_ID);
            statement.setInt(3, userId);

            statement.setString(4, Column.HOME_ID);
            statement.setInt(5, homeId);

            ResultSet result = statement.executeQuery();
            if(!result.next()) {
                throw new Exception();
            }
        } catch (Exception exception) {
            return false;
        }

        return true;
    }

    public boolean hasHomeAccess(String nick, int homeId) {
        return hasHomeAccess(userDAO.getUser(nick).get().getUserId(), homeId);
    }

    public void giveHomeAccess(int homeId, int userId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("INSERT INTO ?(?, ?) VALUES(?, ?)");

            statement.setString(1, Table.HOME_USER);

            statement.setString(2, Column.HOME_ID);
            statement.setString(3, Column.USER_ID);

            statement.setInt(4, homeId);
            statement.setInt(5, userId);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void removeHomeAccess(int homeId, int userId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("DELETE * FROM ? WHERE ?=? AND ?=?");

            statement.setString(1, Table.HOME_USER);

            statement.setString(2, Column.HOME_ID);
            statement.setString(3, Column.USER_ID);

            statement.setInt(4, homeId);
            statement.setInt(5, userId);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
