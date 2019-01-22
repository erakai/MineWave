package com.kai.game.util;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceManager {

    //TODO: Organize images into spritesheets.
    //Unable to do so at current time due to inaccessibility of aseprite.

    //TODO: Load all resources at the start of the program, rather then on GameObject creation.

    public static BufferedImage getImage(String imageName) {
        try {
            return (ImageIO.read(ResourceManager.class.getResourceAsStream("/com/kai/resources/"+imageName)));
        } catch (Exception e) {
            System.out.println("/com/kai/resources/" + imageName + " loading error.");
            e.printStackTrace();
        }
        return null;
    }

    public static Image getImage(String imageName, int width, int height) {
        return resizeImage(getImage(imageName), width, height);
    }

    public static Image resizeImage(Image begin, int width, int height) {
        return (begin.getScaledInstance(width, height, Image.SCALE_FAST));
    }

    public static Image getItemImage(int x, int y, int width, int height) {
        return getImage("items.png").getSubimage(x, y, 8, 8).getScaledInstance(width, height, Image.SCALE_FAST);
    }


    /*
    THE BELOW METHODS ARE TAKEN FROM https://code.google.com/archive/p/game-engine-for-java/source.
    I take no credit for any of the below code.
     */


    public static Image rotate(Image img, double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));

        int w = img.getWidth(null),
                h = img.getHeight(null);

        int neww = (int) Math.floor(w*cos + h*sin),
                newh = (int) Math.floor(h*cos + w*sin);

        BufferedImage bimg = toBufferedImage(getEmptyImage(neww, newh));
        Graphics2D g = bimg.createGraphics();

        g.translate((neww-w)/2, (newh-h)/2);
        g.rotate(Math.toRadians(angle), w/2, h/2);
        g.drawRenderedImage(toBufferedImage(img), null);
        g.dispose();

        return toImage(bimg);
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    public static Image getEmptyImage(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return toImage(img);
    }

    public static Image toImage(BufferedImage bimage) {
        // Casting is enough to convert from BufferedImage to Image
        Image img = (Image) bimage;
        return img;
    }

}
