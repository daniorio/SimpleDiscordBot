package ml.luxinfine.bot.listeners;

import ml.luxinfine.bot.Config;
import ml.luxinfine.bot.enums.ActivityType;
import ml.luxinfine.bot.managers.EconomyManager;
import ml.luxinfine.bot.managers.UserManager;
import ml.luxinfine.bot.utils.LoggerUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.MessageEditEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.MessageEditListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ActivityListener implements MessageCreateListener, MessageEditListener, ReactionAddListener, ServerVoiceChannelMemberJoinListener {
    private final Random rand = new Random();
    public static final HashMap<String, Long> write_cooldown = new HashMap<>();
    public static HashMap<String, Long> edit_cooldown = new HashMap<>();
    public static HashMap<String, Long> voice_cooldown = new HashMap<>();
    public static HashMap<String, Long> reaction_cooldown = new HashMap<>();

    //todo Выдача наград за сообщение в зависимости от его длины, повторяемости слов, кол-ва сообщений и аттачментов
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        event.getMessageAuthor().asUser().ifPresent(user -> this.check(user, ActivityType.WRITE));
    }

    @Override
    public void onMessageEdit(MessageEditEvent event) {
        event.requestMessage().thenAccept(msg -> msg.getAuthor().asUser().ifPresent(user -> this.check(user, ActivityType.EDIT)));
    }

    @Override
    public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent event) {
        this.check(event.getUser(), ActivityType.VOICE);
    }

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        event.requestMessage().thenApply(Message::getAuthor).thenAccept(author -> {
            if(author.isRegularUser()) author.asUser().ifPresent(user -> this.check(user, ActivityType.REACTION));
        });
    }

    private void check(User user, ActivityType type) {
        if(!user.isBot() && type.enabled() && rand.nextInt(100) < type.chance() && type.cooldowns().getOrDefault(user.getIdAsString(), 0L) < System.currentTimeMillis()) {
            UUID uuid = UserManager.getUuidFromId(user.getIdAsString());
            if (uuid != null) {
                Server server = user.getApi().getServerById(Config.bot.server_id).get();
                double adding = this.rand(type.min_reward(), type.max_reward());
                if(user.getRoles(server).stream().anyMatch(role -> role.getIdAsString().equals(Config.boosts.role_id))) adding *= Config.activity.booster_multiplier;
                EconomyManager.addBalance(uuid, adding);
                LoggerUtils.log(type.log(), user.getName(), adding);
            }
            type.cooldowns().put(user.getIdAsString(), System.currentTimeMillis() + type.cooldown() * 1000L);
        }
    }

    private double rand(double min, double max) {
        return Math.random() * (max - min) + min;
    }
}
