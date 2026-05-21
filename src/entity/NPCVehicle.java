package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class NPCVehicle extends Entity {

    GamePanel gp;

    public BufferedImage carRight, carLeft;
    public boolean active = false;

    public NPCVehicle(GamePanel gp) {
        this.gp = gp;
        setDefaultValue();
        getNPCVehicleImage();

        solidArea = new Rectangle();
        solidArea.x = worldX;
        solidArea.y = worldY;
        solidArea.width = (int) (gp.tileSize * 1.5);
        solidArea.height = (int) (gp.tileSize * 1.5) - 64;
    }

    public void getNPCVehicleImage() {
        try {

            carRight = ImageIO.read(getClass().getResourceAsStream("/npc/leftToRightNpcCar.png"));
            carLeft = ImageIO.read(getClass().getResourceAsStream("/npc/rightToLeftNpcCar.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValue() {
        speed = 2;
        width = (int) (gp.tileSize * 1.5);
        height = (int) (gp.tileSize * 1.5);
    }

    public void update() {
        if (!active) {
            return;
        }

        if (direction.equals("RIGHT")) {
            car = carRight;
        } else if (direction.equals("LEFT")) {
            car = carLeft;
        }

        // NPC vehicles only move when the traffic light clearance is finished and
        // cross-traffic has GREEN
        if (!gp.sign.canNPCMove()) {
            return;
        }

        if (direction.equals("RIGHT")) {
            worldX += speed;
            if (worldX > gp.screenWidth) {
                active = false; // Despawn once off-screen
            }
        } else if (direction.equals("LEFT")) {
            worldX -= speed;
            if (worldX < -width) {
                active = false; // Despawn once off-screen
            }
        }

        solidArea.x = worldX;
        solidArea.y = worldY + 32;
    }

}