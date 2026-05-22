package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PedestrianLane {

    private GamePanel gp;
    private Road road;

    public final int CROSSWALK_INDEX = 6;
    public BufferedImage pedestrianSignImg;
    private boolean dynamicLaneActive = false;
    private int flashTimer = 0;

    public PedestrianLane(GamePanel gp, Road road) {
        this.gp = gp;
        this.road = road;
        loadPedestrianAssets();
    }

    private void loadPedestrianAssets() {
        try {
            road.tile[CROSSWALK_INDEX] = new Tile();
            road.tile[CROSSWALK_INDEX].image = ImageIO.read(getClass().getResourceAsStream("/tiles/crosswalk.png"));
            pedestrianSignImg = ImageIO.read(getClass().getResourceAsStream("/signs/pedestrianSign.png"));
        } catch (IOException e) {
            System.err.println("ERROR: Could not load pedestrian assets!");
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

    public void spawnDynamicLane() {
        if (!dynamicLaneActive) {
            dynamicLaneActive = true;
            road.mapTileNum[3][7] = CROSSWALK_INDEX;
            road.mapTileNum[4][7] = CROSSWALK_INDEX;
        }
    }

    public void despawnDynamicLane() {
        if (dynamicLaneActive) {
            dynamicLaneActive = false;
            road.mapTileNum[3][7] = 0;
            road.mapTileNum[4][7] = 2;
        }
    }

    public boolean isDynamicLaneActive() {
        return dynamicLaneActive;
    }

    public void update() {
        if (dynamicLaneActive) {
            flashTimer++;
        } else {
            flashTimer = 0;
        }
    }

    public void draw(Graphics2D g2) {
        if (!dynamicLaneActive) {
            return;
        }

        int y = 7 * gp.tileSize;
        int xStart = 3 * gp.tileSize;
        int xEnd = 5 * gp.tileSize;
        int height = gp.tileSize;

        g2.setStroke(new BasicStroke(3));
        float glowAlpha = 0.5f + 0.3f * (float) Math.sin(flashTimer * 0.1);
        if (glowAlpha < 0f) glowAlpha = 0f;
        if (glowAlpha > 1f) glowAlpha = 1f;

        g2.setColor(new Color(255, 235, 59, (int) (glowAlpha * 255)));
        g2.drawLine(xStart, y, xEnd, y);
        g2.drawLine(xStart, y + height, xEnd, y + height);

        int signCol = 2;
        int signWidth = (int) (gp.tileSize * 0.85);
        int signHeight = (int) (gp.tileSize * 0.85);

        int postX = signCol * gp.tileSize + gp.tileSize / 2 - 3;
        int postY = 7 * gp.tileSize - (int) (gp.tileSize * 0.2);
        int postHeight = (int) (gp.tileSize * 1.2);
        int postWidth = 6;

        g2.setColor(new Color(50, 50, 50));
        g2.fillRect(postX, postY, postWidth, postHeight);
        g2.fillRect(postX - 4, postY + postHeight - 6, postWidth + 8, 6);

        int signX = signCol * gp.tileSize + (gp.tileSize - signWidth) / 2;
        int signY = postY - signHeight;

        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillRect(signX + 3, signY + 3, signWidth, signHeight);

        if (pedestrianSignImg != null) {
            g2.drawImage(pedestrianSignImg, signX, signY, signWidth, signHeight, null);
        } else {
            g2.setColor(new Color(255, 215, 0));
            Polygon diamond = new Polygon();
            diamond.addPoint(signX + signWidth / 2, signY);
            diamond.addPoint(signX + signWidth, signY + signHeight / 2);
            diamond.addPoint(signX + signWidth / 2, signY + signHeight);
            diamond.addPoint(signX, signY + signHeight / 2);
            g2.fillPolygon(diamond);
            g2.setColor(Color.BLACK);
            g2.drawPolygon(diamond);
        }

        boolean flashOn = (flashTimer / 15) % 2 == 0;
        int signPostLightSize = 12;
        int signPostLightX = signX + signWidth / 2 - signPostLightSize / 2;
        int signPostLightY = signY - signPostLightSize + 3;

        g2.setColor(Color.BLACK);
        g2.fillRect(signPostLightX - 2, signPostLightY, signPostLightSize + 4, signPostLightSize);

        if (flashOn) {
            RadialGradientPaint glowPaint = new RadialGradientPaint(
                new Point(signPostLightX + signPostLightSize / 2, signPostLightY + signPostLightSize / 2),
                signPostLightSize * 1.8f,
                new float[]{0.0f, 1.0f},
                new Color[]{new Color(255, 165, 0, 255), new Color(255, 165, 0, 0)}
            );
            g2.setPaint(glowPaint);
            g2.fillOval(signPostLightX - signPostLightSize / 2, signPostLightY - signPostLightSize / 2, signPostLightSize * 2, signPostLightSize * 2);

            g2.setColor(Color.WHITE);
            g2.fillOval(signPostLightX + 2, signPostLightY + 2, signPostLightSize - 4, signPostLightSize - 4);
        } else {
            g2.setColor(new Color(130, 65, 0));
            g2.fillOval(signPostLightX, signPostLightY, signPostLightSize, signPostLightSize);
        }
    }
}