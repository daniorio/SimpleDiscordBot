package ml.luxinfine.bot.managers;

import ml.luxinfine.bot.Config;

import java.util.LinkedHashMap;
import java.util.UUID;

public class EconomyManager {

    public static LinkedHashMap<String, String> getBalanceTop(int offset) {
        return DatabaseManager.getTop(Config.command.common.balance.top.size, Config.database.balance_column, offset);
    }

    public static double getBalance(UUID uuid) {
        String value = DatabaseManager.getValue(uuid.toString(), Config.database.uuid_column, Config.database.balance_column);
        return !value.isEmpty() ? Double.parseDouble(value) : Integer.MIN_VALUE;
    }

    public static boolean setBalance(UUID uuid, double balance) {
        return DatabaseManager.setValue(uuid.toString(), Config.database.uuid_column, balance, Config.database.balance_column);
    }

    public static boolean addBalance(UUID uuid, double balance) {
        return EconomyManager.setBalance(uuid, EconomyManager.getBalance(uuid) + balance);
    }

    public static boolean removeBalance(UUID uuid, double balance) {
        return EconomyManager.setBalance(uuid, EconomyManager.getBalance(uuid) - balance);
    }
}
