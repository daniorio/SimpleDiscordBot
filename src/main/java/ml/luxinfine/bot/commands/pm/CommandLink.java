package ml.luxinfine.bot.commands.pm;

import ml.luxinfine.bot.Config;
import ml.luxinfine.bot.Messages;
import ml.luxinfine.bot.commands.CommandBase;
import ml.luxinfine.bot.managers.DatabaseManager;
import ml.luxinfine.bot.managers.UserManager;
import ml.luxinfine.bot.utils.DiscordUtils;
import ml.luxinfine.bot.utils.EmbedUtils;
import ml.luxinfine.bot.utils.LoggerUtils;
import ml.luxinfine.bot.utils.UrlUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.List;

public class CommandLink implements CommandBase {
    private MessageCreateEvent event;
    private TextChannel channel;
    private User sender;
    private Server server;
    private String[] command;

    @Override
    public void execute() {
        if(!(channel instanceof ServerTextChannel)) {
            if(command.length == 3) {
                String username = UserManager.getUsernameFromId(sender.getIdAsString());
                if(username.isEmpty()) {
                    String answer = UrlUtils.execute(Config.command.pm.link.link_url + "?name=" + command[1] + "&password=" + command[2]);
                    if(!answer.isEmpty()) {
                        if(answer.equals("allow")) {
                            boolean connected = DatabaseManager.setValue(command[1], Config.database.username_column, sender.getIdAsString(), Config.database.discord_column);
                            if(connected) {
                                EmbedUtils.sendInfoEmbed(channel, Messages.command.pm.link.SUCCESS);
                                LoggerUtils.log(Messages.logging.LINK, sender.getName(), command[1]);
                                if(Config.command.pm.link.update_name) sender.updateNickname(server, command[1]);
                            } else EmbedUtils.sendErrorEmbed(channel, Messages.command.UNEXPECTED_ERROR);
                        } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.link.BAD_LOGIN_OR_PASSWORD);
                    } else EmbedUtils.sendErrorEmbed(channel, Messages.command.UNEXPECTED_ERROR);
                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.link.ALREADY_CONNECTED);
            } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USAGE, this.getUsage());
        } else {
            event.getMessage().delete();
            if(command.length != 3) {
                EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.INSTRUCTION_GET);
                EmbedUtils.sendInfoEmbed(sender, Messages.command.pm.link.INSTRUCTION);
            } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.BAD_CHANNEL);
        }
    }

    @Override
    public void update(MessageCreateEvent event) {
        this.event = event;
        this.channel = event.getChannel();
        this.sender = event.getMessageAuthor().asUser().get();
        this.server = event.getApi().getServerById(Config.bot.server_id).get();
        this.command = event.getMessageContent().split("\\s+");
    }

    @Override
    public List<String> getAliases() { return Config.command.pm.link.aliases; }

    @Override
    public String getUsage() {
        return Messages.command.pm.link.USAGE;
    }

    @Override
    public boolean isEnabled() { return Config.command.pm.link.enabled; }

    @Override
    public boolean isAllowToUse() {
        String role = DiscordUtils.getHighestRoleId(server, sender);
        if(server.isOwner(sender)) return true;
        return server.isOwner(sender) || Config.command.pm.link.roles.contains("*") || Config.command.pm.link.roles.contains(role);
    }
}
