package com.kai.game.master;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ResourceManager {

    //TODO: Organize images into spritesheets.
    //Unable to do so at current time due to inaccessibility of aseprite.

    public static Image getImage(String imageName) {
        try {
            return (ImageIO.read(new File("src/com/kai/resources/"+imageName)));
        } catch (IOException e) {
            System.out.println(imageName + " loading error.");
            e.printStackTrace();
        }
        return null;
    }

    public static Image getImage(String imageName, int width, int height) {
        return (getImage(imageName)).getScaledInstance(width, height, Image.SCALE_FAST);
    }

}
