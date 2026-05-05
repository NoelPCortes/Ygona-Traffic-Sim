package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

//Will implement this later when benchmark is finished

public class NPCVehicle extends Entity{

    public BufferedImage carRight, carLeft;

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
        speed = 1;
    }
    

    public void update() {
        if(direction.equals("RIGHT")) {
            x += speed;
            car = carRight;
        } else if(direction.equals("LEFT")) {
            x -= speed;
            car = carLeft;
        }
    }
    

}