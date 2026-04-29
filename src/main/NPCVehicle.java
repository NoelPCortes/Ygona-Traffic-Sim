package src.main;

public class NPCVehicle {
    // Position of NPC car on screen
    private int x;
    private int y;

    //movement speed of NCP car
    private int speed;

    //Direction of NPC car (Left or Right)
    private String direction;

    private boolean isNPCCarActive;
    
    public NPCVehicle(int x, int y, int speed, String direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.isNPCCarActive = true; // active when spawned
    }

    // handles movement logic of npc car
    public void move() {
        
    }

    // updates npc car every frame
    public void update() {

    }

    // draw npc car
    public void render() {

    }

    public int getNpcCarPositionX() {
        return x;
    }

    public int getNpcCarPositionY() {
        return y;
    }

    public boolean isActive() {
        return isNPCCarActive;
    }
}