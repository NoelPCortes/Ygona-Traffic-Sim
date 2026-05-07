package entity;

import main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Entity {
    GamePanel gp;
    public int x, y;
    public int speed;

    public int width, height; 

    public BufferedImage car;
    public String direction = "RIGHT";

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        if (car != null) {
            g2.drawImage(car, x, y, width, height, null);
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(x, y, width, height);
        }
    }
}