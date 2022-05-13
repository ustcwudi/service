package edu.hubu.config;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.util.Configurable;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Translucent implementation of {@link BackgroundProducer}, adds a translucent background to an image.
 */
public class TranslucentBackground extends Configurable implements BackgroundProducer {
    /**
     * @param baseImage the base image
     * @return an image with a translucent background added to the base image.
     */
    public BufferedImage addBackground(BufferedImage baseImage) {
        int width = baseImage.getWidth();
        int height = baseImage.getHeight();

        // create an opaque image
        BufferedImage imageWithBackground = new BufferedImage(width, height,
                BufferedImage.TRANSLUCENT);

        Graphics2D graph = (Graphics2D) imageWithBackground.getGraphics();
        graph.drawImage(baseImage, 0, 0, null);

        return imageWithBackground;
    }
}