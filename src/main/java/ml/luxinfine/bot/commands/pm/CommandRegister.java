package ml.luxinfine.bot.commands.pm;

import ml.luxinfine.bot.Config;
import ml.luxinfine.bot.Messages;
import ml.luxinfine.bot.commands.CommandBase;
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

import java.util.HashMap;
import java.util.List;

public class CommandRegister implements CommandBase {
    private HashMap<String, Long> cooldowns = new HashMap<>();
    private MessageCreateEvent event;
    private TextChannel channel;
    private User sender;
    private Server server;
    private String[] command;

    @Override
    public void execute() {
        if(!(channel instanceof ServerTextChannel)) {
            if(command.length == 4) {
                String username = UserManager.getUsernameFromId(sender.getIdAsString());
                if(username.isEmpty()) {
                    if(!cooldowns.containsKey(sender.getIdAsString()) || System.currentTimeMillis() - cooldowns.get(sender.getIdAsString()) > Config.command.pm.register.cooldown) {
                        if((!(command[1].contains("<") && command[1].contains(">"))) && (!(command[2].contains("<") && command[2].contains(">"))) && (!(command[3].contains("<") && command[3].contains(">")))) {
                            if(command[3].contains("@") && command[3].contains(".")) {
                                if(command[1].matches("[a-zA-Z0-9_]+")) {
                                    if(command[2].length() >= Config.command.pm.register.min_password_length) {
                                        String answer = UrlUtils.execute(Config.command.pm.register.link_url + "?login=" + command[1] + "&password=" + command[2] + "&email=" + command[3] + "&step=1");
                                        if(!answer.isEmpty()) {
                                            if(!answer.equals("-1")) {
                                                if(!answer.equals("-2")) {
                                                    if(!answer.equals("error")) {
                                                        if(answer.equals("success")) {
                                                            EmbedUtils.sendInfoEmbed(channel, Messages.command.pm.register.SUCCESS);
                                                            String mail_name = command[3].split("@")[0];
                                                            String mail_domain = command[3].split("@")[1];
                                                            String mail = mail_name.charAt(0) + mail_name.substring(1, mail_name.length()-1).replaceAll("\\w", "?") + mail_name.substring(mail_name.length()-1) + "@" + mail_domain;
                                                            String password = command[2].replaceAll("\\w", "?");
                                                            LoggerUtils.log(Messages.logging.REGISTER, sender.getName(), command[1], mail, password);
                                                            cooldowns.put(sender.getIdAsString(), System.currentTimeMillis());
                                                        } else EmbedUtils.sendErrorEmbed(channel, Messages.command.UNEXPECTED_ERROR);
                                                    } else EmbedUtils.sendErrorEmbed(channel, Messages.command.UNEXPECTED_ERROR);
                                                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.register.BAD_EMAIL);
                                            } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.register.BAD_NICKNAME);
                                        } else EmbedUtils.sendErrorEmbed(channel, Messages.command.UNEXPECTED_ERROR);
                                    } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.register.SHORT_PASSWORD, Config.command.pm.register.min_password_length);
                                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.register.BAD_NICKNAME);
                            } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.register.BAD_EMAIL);
                        } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.BAD_FORMAT);
                    } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.register.COOLDOWN);
                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.register.ALREADY_CONNECTED);
            } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USAGE, this.getUsage());
        } else {
            event.getMessage().delete();
            if(command.length != 3) {
                EmbedUtils.sendErrorEmbed(channel, Messages.command.pm.INSTRUCTION_GET);
                EmbedUtils.sendInfoEmbed(sender, Messages.command.pm.register.INSTRUCTION);
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
    public List<String> getAliases() { return Config.command.pm.register.aliases; }

    @Override
    public String getUsage() {
        return Messages.command.pm.register.USAGE;
    }

    @Override
    public boolean isEnabled() { return Config.command.pm.register.enabled; }

    @Override
    public boolean isAllowToUse() {
        String role = DiscordUtils.getHighestRoleId(server, sender);
        return server.isOwner(sender) || Config.command.pm.register.roles.contains("*") || Config.command.pm.register.roles.contains(role);
    }
}
