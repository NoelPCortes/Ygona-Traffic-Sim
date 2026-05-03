package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 2;

    public final int tileSize = originalTileSize * scale; //32x32 tile
    public final int maxScreenCol = 12;
    public final int maxScreenRow = 24;
    public final int screenWidth = tileSize * maxScreenCol; // 384 pixels
    public final int screenHeight = tileSize * maxScreenRow; //768 pixels

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    //SETS SCREEN
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GRAY);
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
        double drawInterval = 1_000_000_000/FPS; // draw the screen for 0.0166666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {

            // update information such as character positions
            update();
            // draw the screen with the updated information
            repaint();//Calls paintComponent()
        
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

        player.update();

    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);

        g2.dispose();
    }

}