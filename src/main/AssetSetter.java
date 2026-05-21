package main;

import entity.NPCVehicle;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setNPCVehicle() {
        // left to right
        gp.npcVehicle[0] = new NPCVehicle(gp);
        gp.npcVehicle[0].worldX = -gp.tileSize * 0;
        gp.npcVehicle[0].worldY = gp.tileSize * 5;
        gp.npcVehicle[0].direction = "RIGHT";

        // right to left
        gp.npcVehicle[1] = new NPCVehicle(gp);
        gp.npcVehicle[1].worldX = gp.screenWidth - gp.tileSize;
        gp.npcVehicle[1].worldY = gp.tileSize * 6;
        gp.npcVehicle[1].direction = "LEFT";
        
        
    }
}