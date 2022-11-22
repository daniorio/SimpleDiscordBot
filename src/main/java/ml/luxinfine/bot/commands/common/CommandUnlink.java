package ml.luxinfine.bot.commands.common;

import ml.luxinfine.bot.Config;
import ml.luxinfine.bot.Messages;
import ml.luxinfine.bot.commands.CommandBase;
import ml.luxinfine.bot.managers.DatabaseManager;
import ml.luxinfine.bot.managers.UserManager;
import ml.luxinfine.bot.utils.DiscordUtils;
import ml.luxinfine.bot.utils.EmbedUtils;
import ml.luxinfine.bot.utils.LoggerUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.List;

public class CommandUnlink implements CommandBase {
    private MessageCreateEvent event;
    private TextChannel channel;
    private User sender;
    private String[] command;
    private Server server;

    @Override
    public void execute() {
        if(command.length == 1) {
            String username = UserManager.getUsernameFromId(sender.getIdAsString());
            if(!username.isEmpty()) {
                boolean executed = DatabaseManager.setValue(username, Config.database.username_column, "NULL", Config.database.discord_column);
                if(executed) {
                    UserManager.removeFromCache(sender.getIdAsString());
                    EmbedUtils.sendInfoEmbed(channel, Messages.command.common.unlink.SUCCESS, username);
                    LoggerUtils.log(Messages.logging.UNLINK, sender.getName(), username);
                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.UNEXPECTED_ERROR);
            } else EmbedUtils.sendErrorEmbed(channel, Messages.account.NOT_CONNECT_OWN);
        } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USAGE, this.getUsage());
    }

    @Override
    public void update(MessageCreateEvent event) {
        this.event = event;
        this.channel = event.getChannel();
        this.sender = event.getMessageAuthor().asUser().get();
        this.command = event.getMessageContent().split("\\s+");
        this.server = event.getApi().getServerById(Config.bot.server_id).get();
    }

    @Override
    public boolean isEnabled() { return Config.command.common.unlink.enabled; }

    @Override
    public boolean isAllowToUse() {
        String role = DiscordUtils.getHighestRoleId(server, sender);
        return server.isOwner(sender) || Config.command.common.unlink.roles.contains("*") || Config.command.common.unlink.roles.contains(role);
    }

    @Override
    public String getUsage() { return Messages.command.common.unlink.USAGE; }

    @Override
    public List<String> getAliases() { return Config.command.common.unlink.aliases; }
}
