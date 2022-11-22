package ml.luxinfine.bot;

import ml.luxinfine.bot.listeners.ActivityListener;
import ml.luxinfine.bot.listeners.CommandListener;
import ml.luxinfine.bot.listeners.CooldownListener;
import ml.luxinfine.bot.utils.LoggerUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordBot {
    private static DiscordApi api;

    public static void main(String[] args) {
        api = new DiscordApiBuilder().setToken(Config.bot.token).setAllIntents().login().join();
        setupLogger();
        applyListeners();
    }

    private static void applyListeners() {
        if(Config.cache.enabled) api.setMessageCacheSize(Config.cache.size, Config.cache.seconds);
        if(Config.command.enabled) api.addListener(new CommandListener());
        if(Config.cooldown.command.enabled) api.addListener(new CooldownListener());
        if(Config.activity.enabled) api.addListener(new ActivityListener());
        api.updateActivity(Config.bot.activity, Messages.bot.GAME_STATUS);
    }
    private static void setupLogger() {
        LoggerUtils.channel = DiscordBot.api.getTextChannelById(Config.logging.channel).get();
    }
}
