package ml.luxinfine.bot.commands.common;

import ml.luxinfine.bot.Config;
import ml.luxinfine.bot.Messages;
import ml.luxinfine.bot.commands.CommandBase;
import ml.luxinfine.bot.managers.EconomyManager;
import ml.luxinfine.bot.managers.UserManager;
import ml.luxinfine.bot.utils.*;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class CommandBalance implements CommandBase {
    private MessageCreateEvent event;
    private TextChannel channel;
    private User sender;
    private String[] command;
    private Server server;

    @Override
    public void execute() {
        if(command.length == 1) {
            //*     !BALANCE    *//
            UUID uuid = UserManager.getUuidFromId(sender.getIdAsString());
            if(uuid != null) {
                BufferedImage icon = ImageUtils.getIcon(Config.command.common.balance.icon_path);
                int balance = (int) EconomyManager.getBalance(uuid);
                String description = MessageUtils.addArgs(Messages.command.common.balance.DESCRIPTION, sender.getName(), balance);
                EmbedUtils.sendCmdEmbed(channel, Messages.command.common.balance.TITLE, description, icon);
            } else EmbedUtils.sendErrorEmbed(channel, Messages.account.NOT_CONNECT_OWN);
        } else if(command.length == 2) {
            //*     !BALANCE TOP    *//
            if (Config.command.common.balance.top.aliases.contains(command[1])) {
                LinkedHashMap top = EconomyManager.getBalanceTop(0);
                BufferedImage icon = ImageUtils.getIcon(Config.command.common.balance.top.icon_path);
                StringBuilder message = new StringBuilder();
                for (int i = 1; i <= top.entrySet().size(); i++) {
                    String username = top.keySet().toArray()[i - 1].toString();
                    int balance = (int) Double.parseDouble(top.values().toArray()[i - 1].toString());
                    message.append(MessageUtils.addArgs(Messages.command.common.balance.top.LINE, i, username, balance));
                }
               EmbedUtils.sendCmdEmbed(channel, Messages.command.common.balance.top.TITLE, message.toString(), icon);
            //*     !BALANCE USER    *//
            } else {
                String username = DiscordUtils.getUsername(command[1]);
                if (!username.isEmpty()) {
                    BufferedImage icon = ImageUtils.getIcon(Config.command.common.balance.icon_path);
                    int balance = (int) EconomyManager.getBalance(DiscordUtils.getUuid(command[1]));
                    String description = MessageUtils.addArgs(Messages.command.common.balance.DESCRIPTION, username, balance);
                    EmbedUtils.sendCmdEmbed(channel, Messages.command.common.balance.TITLE, description, icon);
                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USER_NOT_FOUND);
            }
        } else  if(command.length == 3) {
            //*     !BALANCE TOP  SIZE *//
            if (Config.command.common.balance.top.aliases.contains(command[1])) {
                int offset = Integer.parseInt(command[2]);
                LinkedHashMap top = EconomyManager.getBalanceTop(offset);
                BufferedImage icon = ImageUtils.getIcon(Config.command.common.balance.top.icon_path);
                StringBuilder message = new StringBuilder();
                for (int i = 1; i <= top.entrySet().size(); i++) {
                    String username = top.keySet().toArray()[i - 1].toString();
                    int balance = (int) Double.parseDouble(top.values().toArray()[i - 1].toString());
                    message.append(MessageUtils.addArgs(Messages.command.common.balance.top.LINE, 10 * offset + i, username, balance));
                }
                EmbedUtils.sendCmdEmbed(channel, Messages.command.common.balance.top.TITLE, message.toString(), icon);
            } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USAGE, this.getUsage());
        } else if(command.length == 4) {
            //*     !BALANCE ADD USER  *//
            if (Config.command.common.balance.add.aliases.contains(command[1])) {
                String username = DiscordUtils.getUsername(command[2]);
                if(!username.isEmpty()) {
                    boolean isAdded = EconomyManager.addBalance(DiscordUtils.getUuid(command[2]), Double.parseDouble(command[3].replace(",", ".")));
                    if(isAdded) {
                        User target = DiscordUtils.getUserFromMention(event.getApi(), command[2]);
                        if(target != null) EmbedUtils.sendInfoEmbed(target, Messages.command.common.balance.add.TO_USER, command[3]);
                        EmbedUtils.sendInfoEmbed(channel, Messages.command.common.balance.add.SUCCESS, command[3], username);
                        LoggerUtils.log(Messages.logging.BALANCE_ADD, sender.getName(), username, command[3]);
                    } else EmbedUtils.sendErrorEmbed(channel, Messages.command.UNEXPECTED_ERROR);
                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USER_NOT_FOUND);
            //*     !BALANCE SET USER   *//
            } else if (Config.command.common.balance.set.aliases.contains(command[1])) {
                String username = DiscordUtils.getUsername(command[2]);
                if(!username.isEmpty()) {
                    boolean isSet = EconomyManager.setBalance(DiscordUtils.getUuid(command[2]), Double.parseDouble(command[3].replace(",", ".")));
                    if(isSet) {
                        User target = DiscordUtils.getUserFromMention(event.getApi(), command[2]);
                        if(target != null) EmbedUtils.sendInfoEmbed(target, Messages.command.common.balance.set.TO_USER, command[3]);
                        EmbedUtils.sendInfoEmbed(channel, Messages.command.common.balance.set.SUCCESS, command[3], username);
                        LoggerUtils.log(Messages.logging.BALANCE_SET, sender.getName(), username, command[3]);
                    } else EmbedUtils.sendErrorEmbed(channel, Messages.command.UNEXPECTED_ERROR);
                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USER_NOT_FOUND);
            //*     !BALANCE REMOVE USER   *//
            } else if (Config.command.common.balance.remove.aliases.contains(command[1])) {
                String username = DiscordUtils.getUsername(command[2]);
                if (!username.isEmpty()) {
                    boolean isRemoved = EconomyManager.removeBalance(DiscordUtils.getUuid(command[2]), Double.parseDouble(command[3].replace(",", ".")));
                    if (isRemoved) {
                        User target = DiscordUtils.getUserFromMention(event.getApi(), command[2]);
                        if(target != null) EmbedUtils.sendInfoEmbed(target, Messages.command.common.balance.remove.TO_USER, command[3]);
                        EmbedUtils.sendInfoEmbed(channel, Messages.command.common.balance.remove.SUCCESS, command[3], username);
                        LoggerUtils.log(Messages.logging.BALANCE_REMOVE, sender.getName(), username, command[3]);
                    } else EmbedUtils.sendErrorEmbed(channel, Messages.command.UNEXPECTED_ERROR);
                } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USER_NOT_FOUND);
            } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USAGE, this.getUsage());
        } else EmbedUtils.sendErrorEmbed(channel, Messages.command.USAGE, this.getUsage());
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
    public List<String> getAliases() { return Config.command.common.balance.aliases; }

    @Override
    public boolean isEnabled() {
        if(Config.command.common.balance.enabled) {
            if(command.length == 1) {
                return Config.command.common.balance.standard.enabled;
            } else if(command.length == 2) {
                if (Config.command.common.balance.top.aliases.contains(command[1])) {
                    return Config.command.common.balance.top.enabled;
                } else return Config.command.common.balance.other.enabled;
            } else if(command.length == 3) {
                if(Config.command.common.balance.top.aliases.contains(command[1])) {
                    return Config.command.common.balance.top.enabled;
                } else return false;
            } else if(command.length == 4) {
                if(Config.command.common.balance.remove.aliases.contains(command[1])) {
                    return Config.command.common.balance.top.enabled;
                } else if(Config.command.common.balance.add.aliases.contains(command[1])) {
                    return Config.command.common.balance.add.enabled;
                } else if (Config.command.common.balance.set.aliases.contains(command[1])) {
                    return Config.command.common.balance.set.enabled;
                } else return false;
            } else return false;
        } else return false;
    }

    @Override
    public boolean isAllowToUse() {
        String role = DiscordUtils.getHighestRoleId(server, sender);
        if(server.isOwner(sender)) return true;
        if(Config.command.common.balance.roles.contains("*") || Config.command.common.balance.roles.contains(role)) {
            if(command.length == 1) {
                return Config.command.common.balance.standard.roles.contains("*") || Config.command.common.balance.standard.roles.contains(role);
            } else if(command.length == 2) {
                if (Config.command.common.balance.top.aliases.contains(command[1])) {
                    return Config.command.common.balance.top.roles.contains("*") || Config.command.common.balance.top.roles.contains(role);
                } else return Config.command.common.balance.other.roles.contains("*") || Config.command.common.balance.top.roles.contains(role);
            } else if(command.length == 3) {
                if(Config.command.common.balance.top.aliases.contains(command[1])) {
                    return Config.command.common.balance.top.roles.contains("*") || Config.command.common.balance.top.roles.contains(role);
                } else return false;
            } else if(command.length == 4) {
                if(Config.command.common.balance.remove.aliases.contains(command[1])) {
                    return Config.command.common.balance.remove.roles.contains("*") || Config.command.common.balance.remove.roles.contains(role);
                } else if(Config.command.common.balance.add.aliases.contains(command[1])) {
                    return Config.command.common.balance.add.roles.contains("*") || Config.command.common.balance.add.roles.contains(role);
                } else if (Config.command.common.balance.set.aliases.contains(command[1])) {
                    return Config.command.common.balance.set.roles.contains("*") || Config.command.common.balance.set.roles.contains(role);
                }
            }
        }
        return false;
    }

    @Override
    public String getUsage() { return Messages.command.common.balance.USAGE; }
}
