package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PedestrianLane {

    private GamePanel gp;
    private Road road;

    public final int CROSSWALK_INDEX = 6;

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
}