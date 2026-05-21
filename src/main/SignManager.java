package main;

public class SignManager {
    GamePanel gp;

    private String currentSign = "GREEN";
    private int timer = 0;

    public static final int GREEN_TIME = 600; // 10 seconds
    public static final int YELLOW_TIME = 180; // 3 seconds
    public static final int ALL_RED_TIME = 120; // 2 seconds (clearance pause)
    public static final int RED_TIME = 480; // 8 seconds (NPC crossing)

    public SignManager(GamePanel gp) {
        this.gp = gp;
    }

    public void update() {
        timer++;

        int totalCycle = GREEN_TIME + YELLOW_TIME + ALL_RED_TIME + RED_TIME;

        if (timer < GREEN_TIME) {
            if (!currentSign.equals("GREEN")) {
                currentSign = "GREEN";
                despawnNPCs();
            }
        } else if (timer < GREEN_TIME + YELLOW_TIME) {
            currentSign = "YELLOW";
        } else if (timer < totalCycle) {
            if (!currentSign.equals("RED")) {
                currentSign = "RED";
                spawnNPCs();
            }
        } else {
            timer = 0; // Reset cycle
        }
    }

    private void spawnNPCs() {
        for (int i = 0; i < gp.npcVehicle.length; i++) {
            if (gp.npcVehicle[i] != null) {
                gp.npcVehicle[i].active = true;
                if (gp.npcVehicle[i].direction.equals("RIGHT")) {
                    gp.npcVehicle[i].worldX = -gp.npcVehicle[i].width;
                } else if (gp.npcVehicle[i].direction.equals("LEFT")) {
                    gp.npcVehicle[i].worldX = gp.screenWidth;
                }
            }
        }
    }

    private void despawnNPCs() {
        for (int i = 0; i < gp.npcVehicle.length; i++) {
            if (gp.npcVehicle[i] != null) {
                gp.npcVehicle[i].active = false;
            }
        }
    }

    public String getCurrentSign() {
        return currentSign;
    }

    public boolean canNPCMove() {
        // NPCs are only allowed to move during the active RED crossing phase, NOT the
        // ALL_RED clearance phase
        int activeNPCStart = GREEN_TIME + YELLOW_TIME + ALL_RED_TIME;
        int activeNPCEnd = GREEN_TIME + YELLOW_TIME + ALL_RED_TIME + RED_TIME;
        return timer >= activeNPCStart && timer < activeNPCEnd;
    }
}
