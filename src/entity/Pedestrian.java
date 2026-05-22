package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Pedestrian extends Entity {

    public BufferedImage walkRight, walkLeft;
    public boolean active = false;

    public Pedestrian(GamePanel gp) {
        super(gp);
        setDefaultValue();
        getPedestrianImage();
    }

    public void getPedestrianImage() {
        try {
            // Replace these with your actual pedestrian sprite paths if you have them
            walkRight = ImageIO.read(getClass().getResourceAsStream("/npc/pedestrianRight.png"));
            walkLeft = ImageIO.read(getClass().getResourceAsStream("/npc/pedestrianLeft.png"));
        } catch (Exception e) {
            // Fail silently so it can fall back to drawing a blue circle if images aren't ready
        }
    }

    public void setDefaultValue() {
        speed = 2; // Walk speed
        width = gp.tileSize / 2;
        height = gp.tileSize / 2;
    }

    public void update() {
        if (!active) {
            return;
        }

        // Pedestrians only move when the traffic light clearance is finished (Red light active)
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

    public void draw(Graphics2D g2) {
        if (!active) return;

        BufferedImage image = null;

        if (direction.equals("RIGHT")) {
            image = walkRight;
        } else if (direction.equals("LEFT")) {
            image = walkLeft;
        }

        if (image != null) {
            g2.drawImage(image, x, y, width, height, null);
        } else {
            // Fallback: Blue circle if images aren't loaded
            g2.setColor(java.awt.Color.BLUE);
            g2.fillOval(x, y, width, height);
        }
    }
}