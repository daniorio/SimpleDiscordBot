package ml.luxinfine.bot.utils;

import ml.luxinfine.bot.Config;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

import java.util.concurrent.CompletableFuture;

public class LoggerUtils {
    public static TextChannel channel;
    private static final CompletableFuture<Message> EMPTY = new CompletableFuture<>();

    public static CompletableFuture<Message> log(String msg, Object ... args) {
        if(Config.logging.enabled) {
            String message = MessageUtils.addArgs(msg, args);
            return MessageUtils.send(channel, message);
        }
        return EMPTY;
    }
}
