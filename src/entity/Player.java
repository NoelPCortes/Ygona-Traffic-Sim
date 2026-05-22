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

        width = (int)(gp.tileSize * 0.8);
        height = (int)(gp.tileSize * 1.5);
        //Sets player position in the right lane (column 4) centered
        x = gp.tileSize * 4 + (gp.tileSize - width) / 2;
        y = gp.tileSize * 10;
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
        // Lateral lane-steering movement
        if(keyH.leftPressed) {
            x -= speed;
            if(x < gp.tileSize * 3 + 4) {
                x = gp.tileSize * 3 + 4;
            }
        }
        if(keyH.rightPressed) {
            x += speed;
            if(x > gp.tileSize * 5 - width - 4) {
                x = gp.tileSize * 5 - width - 4;
            }
        }

        // COLLISION DETECTION WITH NPC VEHICLES
        Rectangle playerHitbox = new Rectangle(x, y, width, height);

        for(int i = 0; i < gp.npcVehicle.length; i++) {
            if(gp.npcVehicle[i] != null && gp.npcVehicle[i].active) {

                // Get the hitbox of the current active NPC car
                Rectangle npcHitbox = new Rectangle(
                        gp.npcVehicle[i].x,
                        gp.npcVehicle[i].y,
                        gp.npcVehicle[i].width,
                        gp.npcVehicle[i].height
                );

                // Check if the two rectangles overlap
                if(playerHitbox.intersects(npcHitbox)) {
                    System.out.println("CRASH! You hit an NPC vehicle.");
                    // Reset the player to their starting position
                    setDefaultValues();
                }
            }
        }

        //Spawns player to starting position if it reaches out of bounds (past the intersection)
        if(y < gp.tileSize * 2){
            y = gp.tileSize * 10;
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