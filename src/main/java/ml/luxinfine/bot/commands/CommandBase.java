package ml.luxinfine.bot.commands;

import org.javacord.api.event.message.MessageCreateEvent;

import java.util.List;

public interface CommandBase {
    void execute();
    void update(MessageCreateEvent event);
    boolean isEnabled() ;
    boolean isAllowToUse();
    String getUsage();
    List<String> getAliases();
}
