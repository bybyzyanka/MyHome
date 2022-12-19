package ua.byby.myhome.models;

import org.bukkit.Location;
import ua.byby.myhome.util.LocationParser;

public class Home {

    private int homeId, userId;
    private boolean privacy;
    private Location location;

    public Home() {}

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getHomeId() {
        return homeId;
    }

    public int getUserId() {
        return userId;
    }

    public Boolean isPrivate() {
        return privacy;
    }

    public Location getLocation() {
        return location;
    }
}
