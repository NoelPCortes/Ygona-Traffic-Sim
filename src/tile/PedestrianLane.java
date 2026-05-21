package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.IOException;

public class PedestrianLane {

    private GamePanel gp;
    private Road road;
    
    public final int CROSSWALK_INDEX = 6;
    private boolean isSafeToCross = false;

    private int pedestrianX = 0;
    private final int walkSpeed = 2;

    public PedestrianLane(GamePanel gp, Road road) {
        this.gp = gp;
        this.road = road;
        loadPedestrianAssets();
    }

    private void loadPedestrianAssets() {
        try {
            road.tile[CROSSWALK_INDEX] = new Tile();
            road.tile[CROSSWALK_INDEX].image = ImageIO.read(getClass().getResourceAsStream("/tiles/crosswalk.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deployCrosswalk(int startCol, int endCol, int row) {
        if (row >= 0 && row < gp.maxScreenRow) {
            for (int c = startCol; c <= endCol; c++) {
                if (c >= 0 && c < gp.maxScreenCol) {
                    road.mapTileNum[c][row] = CROSSWALK_INDEX;
                }
            }
        }
    }

    public void update() {
        if (gp.signManager.getCurrentSign().equals("RED") && gp.signManager.canNPCMove()) {
            isSafeToCross = true;
            pedestrianX += walkSpeed;
            if (pedestrianX > gp.screenWidth) {
                pedestrianX = -gp.tileSize; 
            }
        } else {
            isSafeToCross = false;
            pedestrianX = 0; 
        }
    }

    public void drawPedestrians(Graphics2D g2, int crosswalkRowY) {
        if (isSafeToCross) {
            g2.setColor(java.awt.Color.BLUE);
            g2.fillOval(pedestrianX, crosswalkRowY + (gp.tileSize / 4), gp.tileSize / 2, gp.tileSize / 2);
        }
    }

    public boolean isSafeToCross() {
        return isSafeToCross;
    }

    public void setSafeToCross(boolean safe) {
        this.isSafeToCross = safe;
    }
}