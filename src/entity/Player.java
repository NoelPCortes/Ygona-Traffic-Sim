package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyH;
    GamePanel gp;

    public Player(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        //Sets player position in the middle
        x = gp.screenWidth / 2 - gp.tileSize / 2; //X = 176 pixels
        y = gp.screenHeight - 200; //Y = 568 pixels
        speed = 4;

    }

    public void getPlayerImage() {
        try {

            car = ImageIO.read(getClass().getResourceAsStream("/res/playerSprite/Car3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {

        if(keyH.upPressed == true) {
            y -= speed;
        }

    }

    public void draw(Graphics2D g2) {

        //g2.setColor(Color.WHITE);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = car;

        g2.drawImage(image, x, y, gp.tileSize * 2, gp.tileSize * 3, null);

    }

}