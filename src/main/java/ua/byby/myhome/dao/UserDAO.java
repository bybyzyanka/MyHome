package ua.byby.myhome.dao;

import ua.byby.myhome.models.User;
import ua.byby.myhome.util.Column;
import ua.byby.myhome.util.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO extends DAO {

    @Override
    public void createTable() {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + Table.USER + "(" +
                    Column.USER_ID + " int NOT NULL AUTO_INCREMENT," +
                    Column.NICK + " varchar(32) NOT NULL UNIQUE," +
                    "PRIMARY KEY(" + Column.USER_ID + "))");

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public User getUser(String nick) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM " + Table.USER + " WHERE " + Column.NICK + "=?");

            statement.setString(1, nick);

            ResultSet result = statement.executeQuery();
            result.next();

            User user = new User();
            user.setUserId(result.getInt(Column.USER_ID));
            user.setNick(result.getString(Column.NICK));

            return user;
        } catch (Exception exception) {
            return null;
        }
    }

    public User getUserById(int userId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM " + Table.USER + " WHERE " + Column.USER_ID + "=?");

            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            result.next();
            User user = new User();
            user.setUserId(result.getInt(Column.USER_ID));
            user.setNick(result.getString(Column.NICK));

            return user;
        } catch (Exception exception) {
            return null;
        }
    }

    public void createUser(String nick) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO " + Table.USER + "(" + Column.NICK + ") VALUES(?)");

            statement.setString(1, nick);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
