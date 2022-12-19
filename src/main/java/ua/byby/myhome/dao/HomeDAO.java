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
            PreparedStatement statement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ?(" +
                    "home_id int GENERATED BY DEFAULT AS IDENTITY ON DELETE CASCADE," +
                    "user_id int REFERENCES user(user_id)," +
                    "privacy bit," +
                    "location varchar," +
                    "PRIMARY KEY(home_id)");

            statement.setString(1, Table.HOME);
            statement.executeUpdate();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Optional<Home> getHome(int userId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM ? WHERE ?=?");

            statement.setString(1, Table.HOME);
            statement.setString(2, Column.USER_ID);
            statement.setInt(3, userId);

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
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM ? WHERE ?=?");

            statement.setString(1, Table.HOME);
            statement.setString(2, Column.HOME_ID);
            statement.setInt(3, homeId);

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
            PreparedStatement statement = getConnection().prepareStatement("INSERT INTO ?(?, ?, ?) VALUES(?, ?, ?)");

            statement.setString(1, Table.HOME);

            statement.setString(2, Column.USER_ID);
            statement.setString(3, Column.LOCATION);
            statement.setString(4, Column.PRIVACY);

            statement.setInt(5, userId);
            statement.setString(6, locationParser.parseToString(location));
            statement.setInt(7, 0);

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
            PreparedStatement statement = getConnection().prepareStatement("UPDATE ? SET ?=? ?=? ?=? WHERE ?=?");

            statement.setString(1, Table.HOME);

            statement.setString(2, Column.USER_ID);
            statement.setInt(3, home.getUserId());

            statement.setString(4, Column.LOCATION);
            statement.setString(5, locationParser.parseToString(home.getLocation()));

            statement.setString(6, Column.PRIVACY);
            statement.setInt(7, home.isPrivate() ? 1 : 0);

            statement.setString(8, Column.HOME_ID);
            statement.setInt(9, home.getHomeId());

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deleteHome(int homeId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("DELETE FROM ? WHERE ?=?");

            statement.setString(1, Table.HOME);
            statement.setString(2, Column.HOME_ID);
            statement.setInt(3, homeId);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
