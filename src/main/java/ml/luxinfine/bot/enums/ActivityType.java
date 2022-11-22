package ml.luxinfine.bot.enums;

import ml.luxinfine.bot.Config;
import ml.luxinfine.bot.Messages;
import ml.luxinfine.bot.listeners.ActivityListener;

import java.util.HashMap;

public enum ActivityType {
    WRITE(Config.activity.write.enabled, Config.activity.write.chance, Config.activity.write.min_reward, Config.activity.write.max_reward, Messages.logging.ACTIVITY_MSG, ActivityListener.write_cooldown, Config.activity.write.cooldown),
    EDIT(Config.activity.edit.enabled, Config.activity.edit.chance, Config.activity.edit.min_reward, Config.activity.edit.max_reward, Messages.logging.ACTIVITY_EDIT, ActivityListener.edit_cooldown, Config.activity.edit.cooldown),
    VOICE(Config.activity.voice.enabled, Config.activity.voice.chance, Config.activity.voice.min_reward, Config.activity.voice.max_reward, Messages.logging.ACTIVITY_VOICE, ActivityListener.voice_cooldown, Config.activity.voice.cooldown),
    REACTION(Config.activity.reaction.enabled, Config.activity.reaction.chance, Config.activity.reaction.min_reward, Config.activity.reaction.max_reward, Messages.logging.ACTIVITY_EMOJI, ActivityListener.reaction_cooldown, Config.activity.reaction.cooldown);

    private final boolean enabled;
    private final int chance;
    private final double min_reward;
    private final double max_reward;
    private final String log_msg;
    private final int cooldown;
    private final HashMap<String, Long> cooldowns;

    ActivityType(boolean enabled, int chance, double min_reward, double max_reward, String log_msg, HashMap<String, Long> cooldowns, int cooldown) {
        this.enabled = enabled;
        this.chance = chance;
        this.min_reward = min_reward;
        this.max_reward = max_reward;
        this.log_msg = log_msg;
        this.cooldowns = cooldowns;
        this.cooldown = cooldown;
    }

    public boolean enabled() { return this.enabled; }

    public int chance() { return this.chance; }

    public double min_reward() { return this.min_reward; }

    public double max_reward() { return this.max_reward; }

    public String log() { return this.log_msg; }

    public HashMap<String, Long> cooldowns() { return this.cooldowns; }

    public int cooldown() { return this.cooldown; }
}
