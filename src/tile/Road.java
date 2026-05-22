package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Road {
    GamePanel gp;
    public Tile[] tile;
    int mapTileNum[][];
    public PedestrianLane pedestrianLane;

    public Road(GamePanel gp) {
        this.gp = gp;
        this.tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap("/map/map.txt");

        this.pedestrianLane = new PedestrianLane(gp, this);
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

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/crosswalk.png"));
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

            while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while(col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col].trim());
                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e) {

        }
    }

    public void draw(Graphics2D g2) {
    

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = mapTileNum[col][row];
            int drawY;

            if ((col == 3 || col == 4) && row != 10 && row != 5 && row != 6 && tileNum != 6) {
                // south and north road tiles illusion
                drawY = (row * gp.tileSize + gp.worldY) % (gp.tileSize * gp.maxScreenRow);

                if(drawY < 0) {
                    drawY += gp.tileSize * gp.maxScreenRow;
                }
            } else {
                drawY = row * gp.tileSize; // sidewalks, crosswalk, and middle intersection stay locked in place
            }

            if(tile[tileNum] != null && tile[tileNum].image != null) {
                g2.drawImage(tile[tileNum].image, x, drawY, gp.tileSize, gp.tileSize, null);
            }

            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
            }
        }

        if (pedestrianLane != null) {
            pedestrianLane.draw(g2);
        }
    }
}