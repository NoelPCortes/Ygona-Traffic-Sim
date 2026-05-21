package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public int width, height; 

    public BufferedImage car;
    public String direction = "RIGHT";

    public Rectangle solidArea;
    public boolean collisionOn = false;


    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);
        g2.fillRect(solidArea.x, solidArea.y, solidArea.width, solidArea.height);

        if (car != null) {
            g2.drawImage(car, worldX, worldY, width, height, null);
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(worldX, worldY, width, height);
        }
    }
}