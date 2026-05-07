package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        
        super(gp);
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        //Sets player position in the middle
        x = gp.tileSize * 3 + (gp.tileSize / 2);
        y = gp.tileSize * 10;
        width = (int)(gp.tileSize * 0.8);
        height = (int)(gp.tileSize * 1.5);
        speed = 4;

    }

    public void getPlayerImage() {
        try {

            car = ImageIO.read(getClass().getResourceAsStream("/playerSprite/Car3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {

        //Spawns player to starting position if it reaches out of bounds
        if(y < 0){
            y = gp.tileSize * 10;
        }

        if(keyH.upPressed == true) {
            y -= speed;
        }

    }

    public void draw(Graphics2D g2) {

        if (car != null) {
            g2.drawImage(car, x, y, width, height, null);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height);
        }
    }

}