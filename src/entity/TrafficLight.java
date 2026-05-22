package entity;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TrafficLight {
    GamePanel gp;
    BufferedImage red, yellow, green;
    int x, y;

    public TrafficLight(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;

        getImages();
    }

    public void getImages() {
        try {
            red = ImageIO.read(getClass().getResourceAsStream("/signs/redLight.png"));
            yellow = ImageIO.read(getClass().getResourceAsStream("/signs/yellowLight.png"));
            green = ImageIO.read(getClass().getResourceAsStream("/signs/greenLight.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        String state = gp.sign.getCurrentSign();

        if(state.equals("RED")) {
            image = red;
        } else if(state.equals("YELLOW")) {
            image = yellow;
        } else if(state.equals("GREEN")) {
            image = green;
        }

        if(image != null) {
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        }

        
    }
}
