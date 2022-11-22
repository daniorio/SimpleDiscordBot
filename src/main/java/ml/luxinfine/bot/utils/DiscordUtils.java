package ml.luxinfine.bot.utils;

import ml.luxinfine.bot.Config;
import ml.luxinfine.bot.managers.DatabaseManager;
import ml.luxinfine.bot.managers.UserManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.UUID;

public class DiscordUtils {

    public static User getUserFromMention(DiscordApi api, String raw) {
        try {
            return api.getUserById(MessageUtils.mentionToUserid(raw)).get();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public static String getHighestRoleId(Server server, User sender) {
        return server.getHighestRole(sender).isPresent() ? server.getHighestRole(sender).get().getIdAsString() : "-1";
    }

    public static String getUsername(String raw) {
        boolean isExist = DatabaseManager.isValueExist(Config.database.username_column, raw, Config.database.users_table);
        if(isExist) return raw;
        return UserManager.getUsernameFromId(MessageUtils.mentionToUserid(raw));
    }

    public static UUID getUuid(String raw) {
        boolean isUsername = DatabaseManager.isValueExist(Config.database.username_column, raw, Config.database.users_table);
        if(isUsername) return UserManager.getUuidFromUsername(raw);
        return UserManager.getUuidFromId(MessageUtils.mentionToUserid(raw));
    }
}
