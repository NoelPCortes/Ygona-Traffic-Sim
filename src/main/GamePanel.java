package src.main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 2;

    final int tileSize = originalTileSize * scale; //32x32 tile
    final int maxScreenCol = 12;
    final int maxScreenRow = 24;
    final int screenWidth = tileSize * maxScreenCol; // 384 pixels
    final int screenHeight = tileSize * maxScreenRow; //768 pixels

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    //SETS SCREEN
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); //Calls run method (in-case I forgot)
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/FPS; // draw the screen for 0.0166666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {

            // update information such as character positions
            update();
            // draw the screen with the updated information
            repaint();
        
            try {
                double remainingTime = nextDrawTime - System.nanoTime(); // time remaining until nextDrawTime
                remainingTime /= 1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
        
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if(keyH.upPressed == true) {
            playerY -= playerSpeed;
        } 

        // stops from moving off the top of screen
        if(playerY < 0) {
            playerY = 0;
        }
    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        Graphics g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }

}
