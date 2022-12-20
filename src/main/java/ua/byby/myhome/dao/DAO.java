package ua.byby.myhome.dao;

import ua.byby.myhome.MyHomePlugin;
import ua.byby.myhome.util.IDAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class DAO implements IDAO {

    private static Connection connection;

    public static boolean connectToDatabase(String address, String port, String name, String user, String password) {
        try {
            if(address == null || port == null || name == null || user == null || password == null)
                return false;

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://*address*:*port*/*name*?autoReconnect=true&initialTimeout=1&useSSL=false"
                    .replace("*name*", name).replace("*address*", address)
                    .replace("*port*", port), user, password);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public DAO() {
        createTable();
    }

    private static File getDatabaseFile() {
        File file = new File(plugin.getDataFolder(), "myhome.db");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception exception) {}
        }

        return file;
    }

    protected Connection getConnection() {
        return connection;
    }

    public abstract void createTable();
}
