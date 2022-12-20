package ua.byby.myhome.dao;

import org.bukkit.Location;
import ua.byby.myhome.MyHomePlugin;
import ua.byby.myhome.models.Home;
import ua.byby.myhome.util.Column;
import ua.byby.myhome.util.LocationParser;
import ua.byby.myhome.util.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class HomeDAO extends DAO {

    private LocationParser locationParser = new LocationParser();

    @Override
    public void createTable() {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + Table.HOME + "(" +
                    Column.HOME_ID + " int NOT NULL AUTO_INCREMENT," +
                    Column.USER_ID + " int NOT NULL, " +
                    Column.PRIVACY + " int NOT NULL," +
                    Column.LOCATION + " varchar(255) NOT NULL," +
                    "PRIMARY KEY(" + Column.HOME_ID + ")," +
                    "FOREIGN KEY(" + Column.USER_ID + ") REFERENCES " + Table.USER + "(" + Column.USER_ID + "))");

            statement.executeUpdate();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Optional<Home> getHome(int userId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM " + Table.HOME + " WHERE " + Column.USER_ID + "=?");

            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            Home home = new Home();
            home.setHomeId(result.getInt(Column.HOME_ID));
            home.setUserId(result.getInt(Column.USER_ID));
            home.setLocation(locationParser.parseToLocation(result.getString(Column.LOCATION)));
            home.setPrivacy(result.getBoolean(Column.PRIVACY));

            return Optional.of(home);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<Home> getHomeById(int homeId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT * FROM " + Table.HOME + " WHERE " + Column.HOME_ID + "=?");

            statement.setInt(1, homeId);

            ResultSet result = statement.executeQuery();
            Home home = new Home();
            home.setHomeId(result.getInt(Column.HOME_ID));
            home.setUserId(result.getInt(Column.USER_ID));
            home.setLocation(locationParser.parseToLocation(result.getString(Column.LOCATION)));
            home.setPrivacy(result.getBoolean(Column.PRIVACY));

            return Optional.of(home);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<Home> getHome(String nick) {
        return getHome(MyHomePlugin.getInstance().getUserDAO().getUser(nick).get().getUserId());
    }

    public void createHome(int userId, Location location) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO " + Table.HOME + "(" +
                            Column.USER_ID + ", " + Column.LOCATION + ", " + Column.PRIVACY + ") VALUES(?, ?, ?)");

            statement.setInt(1, userId);
            statement.setString(2, locationParser.parseToString(location));
            statement.setInt(3, 0);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void createHome(String nick, Location location) {
        createHome(userDAO.getUser(nick).get().getUserId(), location);
    }

    public void updateHome(Home home) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "UPDATE " + Table.HOME + " SET " + Column.USER_ID + "=?, " + Column.LOCATION + "=?, " +
                            Column.PRIVACY + "=? WHERE " + Column.HOME_ID + "=?");

            statement.setInt(1, home.getUserId());
            statement.setString(2, locationParser.parseToString(home.getLocation()));
            statement.setInt(3, home.isPrivate() ? 1 : 0);
            statement.setInt(4, home.getHomeId());

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deleteHome(int homeId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "DELETE FROM " + Table.HOME + " WHERE " + Column.HOME_ID + "=?");

            statement.setInt(1, homeId);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
