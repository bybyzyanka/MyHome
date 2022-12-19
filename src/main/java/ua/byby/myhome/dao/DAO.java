package ua.byby.myhome.dao;

import ua.byby.myhome.MyHomePlugin;
import ua.byby.myhome.util.IDAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class DAO implements IDAO {

    private static Connection connection;

    public static boolean connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + getDatabaseFile());
        } catch (Exception exception) {
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
