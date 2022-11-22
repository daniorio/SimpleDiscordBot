package ml.luxinfine.bot.managers;

import ml.luxinfine.bot.Config;

import java.sql.*;
import java.util.LinkedHashMap;

public class DatabaseManager {
    private static Connection connection;

    public static boolean setValue(Object key, String key_column, Object value, String value_column) {
        try {
            connect();
            PreparedStatement s = connection.prepareStatement("UPDATE " + Config.database.users_table + " SET " + value_column + " = ? WHERE " + key_column + " = ?");
            s.setObject(1, value);
            s.setObject(2, key);
            s.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public static LinkedHashMap<String, String> getTop(int size, String column, int offset) {
        try {
            connect();
            PreparedStatement s = connection.prepareStatement("SELECT " + Config.database.username_column + "," + column + " FROM " + Config.database.users_table + " ORDER BY " + column + " DESC LIMIT " + offset * size + "," + size);
            ResultSet set = s.executeQuery();
            LinkedHashMap<String, String> top = new LinkedHashMap<>();
            while(set.next()) top.put(set.getString(Config.database.username_column), set.getString(column));
            return top;
        } catch (SQLException e) { e.printStackTrace(); }
        return new LinkedHashMap<>();
    }

    public static String getValue(Object key, String key_column, String value_column) {
        try {
            connect();
            PreparedStatement s = connection.prepareStatement("SELECT " + value_column + " FROM " + Config.database.users_table + " WHERE " + key_column + " = ?");
            s.setObject(1, key);
            ResultSet set = s.executeQuery();
            if(set.next()) {
                String name = set.getString(value_column);
                return name != null ? name : "";
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "";
    }

    public static boolean isValueExist(String check_column, Object value, String table) {
        try {
            connect();
            PreparedStatement s = connection.prepareStatement("SELECT " + check_column + " FROM " + table + " WHERE " + check_column + " = ?");
            s.setObject(1, value);
            return s.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private static void connect() throws SQLException {
        if(connection == null || connection.isClosed()) connection = DriverManager.getConnection(Config.database.url, Config.database.user, Config.database.password);
    }
}
