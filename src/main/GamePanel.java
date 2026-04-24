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

    Thread gameThread;

    //SETS SCREEN
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); //Calls run method (in-case I forgot)
    }

    @Override
    public void run(){

    }

}
