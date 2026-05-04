package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Objects;

public class Road {
    GamePanel gp;
    public Tile[] tile;

    public Road(GamePanel gp) {
        this.gp = gp;
        this.tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/leftRoad.png"));
            
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/middleRoad.png"));
            
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rightVerticalRoad.png"));
            
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rightVerticalSidewalk.png"));
            
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/middleSidewalk.png"));
            
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/leftVerticalSidewalk.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Researcher-style validation to catch loading errors
        if (tile[0].image == null) System.out.println("ERROR: leftVerticalRoad.png not loaded");
        if (tile[1].image == null) System.out.println("ERROR: middleRoad.png not loaded");
    }

    public void rotateTile(Graphics2D g2, BufferedImage image, int x, int y, double angle) {
        AffineTransform backup = g2.getTransform();
        
        // Rotate around the center of the tile
        int centerX = x + gp.tileSize / 2;
        int centerY = y + gp.tileSize / 2;
        
        g2.translate(centerX, centerY);
        g2.rotate(Math.toRadians(angle));
        
        // Draw the image offset by half its size to keep it centered
        g2.drawImage(image, -gp.tileSize / 2, -gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        
        g2.setTransform(backup);
    }

    public void draw(Graphics2D g2) {
        // Optimized loop using your GamePanel variables
        for (int col = 0; col < gp.maxScreenCol; col++) {
            for (int row = 0; row < gp.maxScreenRow; row++) {

                int x = col * gp.tileSize;
                int y = row * gp.tileSize;

                // Define the row ranges for the North-South segments
                boolean isNorthSouthRoad = (row >= 0 && row <= 4) || (row >= 7 && row <= 11);
                boolean isEastWestRoad = (row >= 5 && row <= 6);

                if (isNorthSouthRoad) {
                    if (col == 0) {
                        g2.drawImage(tile[5].image, x, y, gp.tileSize, gp.tileSize, null);
                    } 
                    else if (col == 7) {
                        // Horizontal flip for the right-side sidewalk
                        g2.drawImage(tile[5].image, x + gp.tileSize, y, -gp.tileSize, gp.tileSize, null);
                    } 
                    else if (col == 1 || col == 6) {
                        g2.drawImage(tile[4].image, x, y, gp.tileSize, gp.tileSize, null);
                    } 
                    else if (col == 2) {
                        g2.drawImage(tile[3].image, x, y, gp.tileSize, gp.tileSize, null);
                    } 
                    else if (col == 5) {
                        // Horizontal flip for the left-facing curb
                        g2.drawImage(tile[3].image, x + gp.tileSize, y, -gp.tileSize, gp.tileSize, null);
                    } 
                    else if (col == 3) {
                        g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
                    } 
                    else if (col == 4) {
                        g2.drawImage(tile[2].image, x, y, gp.tileSize, gp.tileSize, null);
                    }
                }

                // Handle the Horizontal Road segments
                if (isEastWestRoad && ((col >= 0 && col <= 2) || (col >= 5 && col <= 7))) {
                    if (row == 5) {
                        rotateTile(g2, tile[0].image, x, y, 90.0);
                    } else if (row == 6) {
                        rotateTile(g2, tile[0].image, x, y, 270.0);
                    }
                }
            }
        }
    }
}