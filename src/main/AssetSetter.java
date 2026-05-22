package main;

import entity.NPCVehicle;
import entity.Pedestrian;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setNPCVehicle() {
        // left to right
        gp.npcVehicle[0] = new NPCVehicle(gp);
        gp.npcVehicle[0].x = -gp.tileSize * 0;
        gp.npcVehicle[0].y = gp.tileSize * 5;
        gp.npcVehicle[0].direction = "RIGHT";

        // right to left
        gp.npcVehicle[1] = new NPCVehicle(gp);
        gp.npcVehicle[1].x = gp.screenWidth - gp.tileSize;
        gp.npcVehicle[1].y = gp.tileSize * 6;
        gp.npcVehicle[1].direction = "LEFT";
    }

    public void setPedestrian() {
        // Pedestrian 1: left to right 
        gp.pedestrians[0] = new Pedestrian(gp);
        gp.pedestrians[0].x = -gp.tileSize;
        gp.pedestrians[0].y = gp.tileSize * 7;
        gp.pedestrians[0].direction = "RIGHT";

        // Pedestrian 2: right to left 
        gp.pedestrians[1] = new Pedestrian(gp);
        gp.pedestrians[1].x = gp.screenWidth;
        gp.pedestrians[1].y = gp.tileSize * 7 + (gp.tileSize / 4);
        gp.pedestrians[1].direction = "LEFT";
    }
}