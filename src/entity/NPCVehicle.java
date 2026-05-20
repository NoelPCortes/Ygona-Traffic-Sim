package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class NPCVehicle extends Entity {

    public BufferedImage carRight, carLeft;
    public boolean active = false;

    public NPCVehicle(GamePanel gp) {
        super(gp);
        setDefaultValue();
        getNPCVehicleImage();
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
            x += speed;
            if (x > gp.screenWidth) {
                active = false; // Despawn once off-screen
            }
        } else if (direction.equals("LEFT")) {
            x -= speed;
            if (x < -width) {
                active = false; // Despawn once off-screen
            }
        }
    }

}