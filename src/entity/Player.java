package src.entity;

import src.main.GamePanel;
import src.main.KeyHandler;

import java.awt.*;

public class Player extends Entity {

    KeyHandler keyH;
    GamePanel gp;

    public Player(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
    }

    public void setDefaultValues() {

        //Sets player position in the middle
        x = gp.screenWidth / 2 - gp.tileSize / 2; //X = 176 pixels
        y = gp.screenHeight - 200; //Y = 568 pixels
        speed = 4;

    }

    public void update() {

        if(keyH.upPressed == true) {
            y -= speed;
        }

    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

    }

}