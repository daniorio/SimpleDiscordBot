package ml.luxinfine.bot.utils;

import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EmbedUtils {
    public static EmbedBuilder getCmdEmbed(String title, String description, BufferedImage icon) {
        EmbedBuilder embed = new EmbedBuilder().setTitle("").setDescription(description);
        return icon != null ? embed.setAuthor(title, "", icon) : embed.setAuthor(title);
    }

    public static EmbedBuilder getInfoEmbed(String unformatted_msg, Object ... args) {
        unformatted_msg = MessageUtils.addArgs(unformatted_msg, args);
        String title = unformatted_msg.split(":")[0];
        String description = unformatted_msg.split(":")[1];
        return EmbedUtils.getDefaultEmbed(title, description).setColor(Color.GREEN);
    }

    public static EmbedBuilder getErrorEmbed(String unformatted_msg, Object ... args) {
        unformatted_msg = MessageUtils.addArgs(unformatted_msg, args);
        String title = unformatted_msg.split(":")[0];
        String description = unformatted_msg.split(":")[1];
        return EmbedUtils.getDefaultEmbed(title, description).setColor(Color.RED);
    }

    public static EmbedBuilder getDefaultEmbed(String title, String description) {
        return new EmbedBuilder().setTitle(title).setDescription(description);
    }

    public static void sendErrorEmbed(Messageable channel, String unformatted_msg, Object ... args) {
        channel.sendMessage(EmbedUtils.getErrorEmbed(unformatted_msg, args));
    }

    public static void sendInfoEmbed(Messageable channel, String unformatted_msg, Object ... args) {
        channel.sendMessage(EmbedUtils.getInfoEmbed(unformatted_msg, args));
    }

    public static void sendCmdEmbed(Messageable channel, String title, String description, BufferedImage icon) {
        channel.sendMessage(EmbedUtils.getCmdEmbed(title, description, icon));
    }
}
