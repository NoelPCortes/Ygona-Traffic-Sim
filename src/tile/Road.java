package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Road {

    GamePanel gp;
    public Tile[] tile;

    int mapTileNum[][];

    public Road(GamePanel gp) {

        this.gp = gp;
        this.tile = new Tile[10];

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/map/map.txt");
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

        if (tile[0].image == null) System.out.println("ERROR: leftVerticalRoad.png not loaded");
        if (tile[1].image == null) System.out.println("ERROR: middleRoad.png not loaded");
    }

    public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col].trim());
                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        //CAMERA IMPLEMENTATION
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

//            int drawY;
//
//            if(col == 3 || col == 4) {
//                // south and north road tiles illusion
//                drawY = (row * gp.tileSize + gp.worldY) % (gp.tileSize * gp.maxScreenRow);
//
//                if(drawY < 0) {
//                    drawY += gp.tileSize * gp.maxScreenRow;
//                }
//            } else {
//                drawY = row * gp.tileSize; // then sidewalks and crosswalk stay locked in place
//            }

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(tile[tileNum] != null && tile[tileNum].image != null) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }
}