package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyH;
    GamePanel gp;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight - height - gp.tileSize;

        solidArea = new Rectangle();
        solidArea.x = worldX;
        solidArea.y = worldY;
        solidArea.width = (int)(gp.tileSize * 0.8);
        solidArea.height = (int)(gp.tileSize * 1.5);
    }

    public void setDefaultValues() {

        width = (int)(gp.tileSize * 0.8);
        height = (int)(gp.tileSize * 1.5);

        //Sets player position in the right lane (column 4) centered
        worldX = gp.tileSize * 4 + (gp.tileSize - width) / 2;
        worldY = (gp.maxWorldRow - 3) * gp.tileSize;
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

        int currentSpeed = 2; // Default cruising drift speed

        // Lateral lane-steering movement
        if(keyH.leftPressed) {
            direction = "left";
        } else if(keyH.rightPressed) {
            direction = "right";
        } else if (keyH.upPressed) {
            direction = "up";
        } else if (keyH.downPressed) {
            direction = "down";
        }

        if(worldY < (gp.tileSize * 10)){
            worldY = (gp.maxWorldRow - 3) * gp.tileSize;
        }

        //COLLISION
        collisionOn = false;
        gp.collisionChecker.checkCar(this);

        if(collisionOn == false) {
            switch(direction){
                case "up":
                    currentSpeed = 5; // Fast acceleration
                    break;
                case "down":
                    currentSpeed = 0; // Brake to a complete stop
                    break;
                case "left":
                    worldX -= speed;
                    if(worldX < gp.tileSize * 4 + 4) {
                        worldX = gp.tileSize * 4 + 4;
                    }
//                    screenX -= speed;
//                    if(screenX < gp.tileSize * 3 + 4) {
//                        screenX = gp.tileSize * 3 + 4;
//                    }
                    break;
                case "right":
                    worldX += speed;
                    if(worldX > gp.tileSize * 6 - width - 4) {
                        worldX = gp.tileSize * 6 - width - 4;
                    }
//                    screenX += speed;
//                    if(screenX > gp.tileSize * 5 - width - 4) {
//                        screenX = gp.tileSize * 5 - width - 4;
//                    }
                    break;
                default:
                    currentSpeed = 2;
            }
        }

        //screenY -= currentSpeed;
        worldY -= currentSpeed;

        //Spawns player to starting position if it reaches out of bounds (past the intersection)

        solidArea.x = screenX;
        solidArea.y = screenY;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(solidArea.x, solidArea.y, solidArea.width, solidArea.height);

        if (car != null) {
            g2.drawImage(car, screenX, screenY, width, height, null);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillRect(screenX, screenY, width, height);
        }
    }

}