package ml.luxinfine.bot.utils;

import ml.luxinfine.bot.DiscordBot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage getIcon(String path) {
        try { return ImageIO.read(DiscordBot.class.getClassLoader().getResourceAsStream(path)); } catch (IOException e) { return null; }
    }
}
