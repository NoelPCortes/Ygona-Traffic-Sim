package main;

import entity.Player;
import tile.Road;
import entity.NPCVehicle;
import entity.TrafficLight;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 4;

    public final int tileSize = originalTileSize * scale; // 32x32 tile
    public final int maxScreenCol = 8;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 384 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels

    public int worldY = 0;

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public Road road = new Road(this);
    public Player player = new Player(this, keyH);
    public AssetSetter aSetter = new AssetSetter(this);
    public NPCVehicle[] npcVehicle = new NPCVehicle[10];
    public SignManager sign = new SignManager(this);
    public TrafficLight trafficLight = new TrafficLight(this, tileSize * 6, tileSize * 2);

    // SETS SCREEN
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        setupGame();

    }

    public void setupGame() {
        aSetter.setNPCVehicle();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // Calls run method (in-case I forgot)
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS; // draw the screen for 0.0166666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            // update information such as character positions
            update();
            // draw the screen with the updated information
            repaint();// Calls paintComponent()

            try {
                double remainingTime = nextDrawTime - System.nanoTime(); // time remaining until nextDrawTime
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    public void update() {
        int currentSpeed = 2; // Default cruising drift speed
        if (keyH.upPressed) {
            currentSpeed = 5; // Fast acceleration
        } else if (keyH.downPressed) {
            currentSpeed = 0; // Brake to a complete stop
        }
        worldY += currentSpeed;

        if (sign.getCurrentSign().equals("RED")) {
            player.y -= currentSpeed;
        } else {
            player.y = tileSize * 10;
        }
        player.update();

        for (int i = 0; i < npcVehicle.length; i++) {
            if (npcVehicle[i] != null) {
                npcVehicle[i].update();
            }
        }
        sign.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        road.draw(g2);

        player.draw(g2);
        // NPC VEhicle - draw only if active
        for (int i = 0; i < npcVehicle.length; i++) {
            if (npcVehicle[i] != null && npcVehicle[i].active) {
                npcVehicle[i].draw(g2);
            }
        }

        trafficLight.draw(g2);

        g2.dispose();
    }

}