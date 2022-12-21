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
            PreparedStatement statement = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + Table.HOME_USER + "(" +
                    Column.HOME_ID + " int," +
                    Column.USER_ID + " int," +
                    "FOREIGN KEY(" + Column.HOME_ID + ") REFERENCES " + Table.HOME + "(" + Column.HOME_ID + ")," +
                    "FOREIGN KEY(" + Column.USER_ID + ") REFERENCES " + Table.USER + "(" + Column.USER_ID + ")," +
                    "PRIMARY KEY(" + Column.HOME_ID + ", " + Column.USER_ID + "))");

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<User> getUsersWithAccess(int homeId) {
        List<User> usersWithAccess = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM " + Table.HOME_USER + " WHERE " + Column.HOME_ID + "=?");

            statement.setInt(1, homeId);

            ResultSet result = statement.executeQuery();
            while(result.next()) {
                usersWithAccess.add(userDAO.getUserById(result.getInt(Column.USER_ID)));
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
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM " + Table.HOME_USER + " WHERE " + Column.USER_ID + "=?");

            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            while(result.next()) {
                usersWithAccess.add(homeDAO.getHomeById(result.getInt(Column.HOME_ID)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return usersWithAccess;
    }

    public List<Home> getHomesWithAccess(String nick) {
        return getHomesWithAccess(userDAO.getUser(nick).getUserId());
    }

    public boolean hasHomeAccess(int userId, int homeId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM " + Table.HOME_USER + " WHERE " + Column.USER_ID + "=? AND " + Column.HOME_ID + "=?");

            statement.setInt(1, userId);
            statement.setInt(2, homeId);

            ResultSet result = statement.executeQuery();
            if(!result.next()) {
                throw new Exception();
            }

            return result.next();
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean hasHomeAccess(String nick, int homeId) {
        return hasHomeAccess(userDAO.getUser(nick).getUserId(), homeId);
    }

    public void giveHomeAccess(int homeId, int userId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO " + Table.HOME_USER + "(" + Column.HOME_ID + ", " + Column.USER_ID + ") VALUES(?, ?)");

            statement.setInt(1, homeId);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void removeHomeAccess(int homeId, int userId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "DELETE FROM " + Table.HOME_USER + " WHERE " + Column.HOME_ID + "=? AND " + Column.USER_ID + "=?");

            statement.setInt(1, homeId);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
