package ml.luxinfine.bot.listeners;

import ml.luxinfine.bot.Config;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.HashMap;

public class CooldownListener implements MessageCreateListener {
    public static HashMap<String, Long> cooldowns = new HashMap<>();

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String sender_id = event.getMessageAuthor().getIdAsString();

        if(!event.getMessageContent().startsWith(Config.command.command_prefix)) return;
        if(cooldowns.containsKey(sender_id) && (System.currentTimeMillis() - cooldowns.get(sender_id) < Config.cooldown.command.seconds * 1000L)) return;

        for(Role role : event.getMessageAuthor().asUser().get().getRoles(event.getServer().get()))
            if(Config.cooldown.command.invulnerability_roles.contains(role.getIdAsString()))
                return;

       cooldowns.put(sender_id, System.currentTimeMillis());
    }
}
