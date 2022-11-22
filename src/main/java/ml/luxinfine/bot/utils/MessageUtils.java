package ml.luxinfine.bot.utils;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Messageable;

import java.util.concurrent.CompletableFuture;

public class MessageUtils {

    public static CompletableFuture<Message> send(Messageable target, String message, Object ... args) {
        message = MessageUtils.addArgs(message, args);
        return target.sendMessage(message);
    }

    //todo убрать из утилов и сделать средствами java
    public static String addArgs(String msg, Object ... args) {
        for (Object arg : args) msg = msg.replaceFirst("%s", MessageUtils.format(arg.toString()));
        return msg;
    }

    public static String mentionToUserid(String mention) {
        return mention.replaceAll("[^0-9]", "");
    }

    // если строка содержит / рядом со спецсимволами, то будут беды
    public static String format(String str) {
        return str.replace("*", "\\*").replace("~", "\\~").replace(">", "\\>").replace("`", "\\`").replace("|", "\\|");
    }
}
