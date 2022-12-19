package ua.byby.myhome.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationParser {

    public Location parseToLocation(String unparsedLocation) {
        String[] split = unparsedLocation.split(";");
        return new Location(Bukkit.getWorld(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2]),
                Integer.parseInt(split[3]));
    }

    public String parseToString(Location location) {
        return location.getWorld().getName() + ";"
                + location.getBlockX() + ";"
                + location.getBlockY() + ";"
                + location.getBlockZ();
    }
}
