package ml.luxinfine.bot.listeners;

import ml.luxinfine.bot.Config;
import ml.luxinfine.bot.Messages;
import ml.luxinfine.bot.commands.CommandBase;
import ml.luxinfine.bot.commands.common.CommandBalance;
import ml.luxinfine.bot.commands.common.CommandUnlink;
import ml.luxinfine.bot.commands.pm.CommandLink;
import ml.luxinfine.bot.commands.pm.CommandRegister;
import ml.luxinfine.bot.utils.EmbedUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.List;

public class CommandListener implements MessageCreateListener {
    private final List<CommandBase> commands = new ArrayList<>();

    public CommandListener() { this.register(); }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String msg = event.getMessage().getContent();

        if(!msg.startsWith(Config.command.command_prefix) || msg.matches("![0-9]+") || msg.equals("!bump") || msg.equals("!d bump")) return;

        String sender_id = event.getMessageAuthor().getIdAsString();

        TextChannel channel = event.getChannel();

        if(CooldownListener.cooldowns.containsKey(sender_id) && (System.currentTimeMillis() - CooldownListener.cooldowns.get(sender_id) < Config.cooldown.command.seconds * 1000L)) {
            EmbedUtils.sendErrorEmbed(channel, Messages.command.COOLDOWN);
            return;
        }

        String command = event.getMessageContent().trim().toLowerCase().split("\\s+")[0];

        for(CommandBase cmd : commands) {
            cmd.update(event);
            if(cmd.getAliases().contains(command)) {
                if(cmd.isEnabled()) {
                    if(cmd.isAllowToUse()) {
                        cmd.execute();
                    } else EmbedUtils.sendErrorEmbed(channel, Messages.command.NO_PERMISSION);
                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.DISABLED);
                return;
            }
        }

        if(Config.command.send_unknown_command_exception && event.getChannel().getIdAsString().equals(Config.command.channel)) EmbedUtils.sendErrorEmbed(channel, Messages.command.UNKNOWN);
    }

    private void register() {
        this.commands.add(new CommandBalance());
        this.commands.add(new CommandUnlink());
        this.commands.add(new CommandLink());
        this.commands.add(new CommandRegister());
    }
}
