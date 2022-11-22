package ml.luxinfine.bot.managers;

import ml.luxinfine.bot.Config;
import ml.luxinfine.bot.utils.Pair;

import java.util.*;

//todo
public class UserManager {
    private static final HashMap<String, Pair<String, String>> users = new HashMap<>(); // id | username, uuid
    private static final Set<Pair<String, String>> nameToUuid = new HashSet<>();

    public static UUID getUuidFromUsername(String username) {
        try {
            return UUID.fromString(UserManager.getFromUser(username, false));
        } catch(Exception e) {
            return null;
        }
    }

    public static String getUsernameFromId(String user_id) { return UserManager.getFromId(user_id, true); }

    public static UUID getUuidFromId(String user_id) {
        try {
            return UUID.fromString(UserManager.getFromId(user_id, false));
        } catch(Exception e) {
            return null;
        }
    }

    // isName - то что мы получаем в ответе
    public static String getFromUser(String user, boolean isName) {
        Optional<String> result = users.values().stream().filter(pair -> (isName ? pair.getValue() : pair.getKey()).equals(user)).map(pair -> isName ? pair.getKey() : pair.getValue()).findFirst();
        if(result.isPresent()) return result.get();
        result = nameToUuid.stream().filter(pair -> (isName ? pair.getValue() : pair.getKey()).equals(user)).map(pair -> isName ? pair.getKey() : pair.getValue()).findFirst();
        if(result.isPresent()) return result.get();
        String answer = DatabaseManager.getValue(user, isName ? Config.database.uuid_column : Config.database.username_column, isName ? Config.database.username_column : Config.database.uuid_column);
        if(!answer.isEmpty()) nameToUuid.add(Pair.of(isName ? answer : user, isName ? user : answer));
        return answer;
    }

    public static String getFromId(String user_id, boolean isName) {
        if(user_id.isEmpty()) return user_id;
        if(!users.containsKey(user_id)) {
            String uuid = DatabaseManager.getValue(user_id, Config.database.discord_column, Config.database.uuid_column);
            String username = DatabaseManager.getValue(user_id, Config.database.discord_column, Config.database.username_column);
            if(!uuid.isEmpty() && !username.isEmpty()) users.put(user_id, Pair.of(username, uuid));
            return isName ? username : uuid;
        } else return isName ? users.get(user_id).getKey() : users.get(user_id).getValue();
    }

    public static void removeFromCache(String user_id) { users.remove(user_id); }
}
