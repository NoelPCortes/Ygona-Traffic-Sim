package main;

public class SignManager {
    GamePanel gp; 

    private String currentSign = "GREEN";
    private int timer = 0;

    public SignManager(GamePanel gp) {
        this.gp = gp;
    }

    public void update() {
        timer++;

        if (timer < 360) {
            currentSign = "GREEN";
        } else if (timer < 540) {
            currentSign = "YELLOW";
        } else if (timer < 900) {
            currentSign = "RED";
        } else {
            timer = 0; // Reset cycle
        }
    }

    public String getCurrentSign() {
        return currentSign;
    }
}
