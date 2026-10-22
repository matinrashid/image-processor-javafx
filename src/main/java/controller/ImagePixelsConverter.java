package controller;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Omvandling från JavaFX Image objekt till en int[][] ARGB matris
 */
public class ImagePixelsConverter {

    public static int[][] imageToPixels(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        int[][] pixelMatrix = new int[height][width];
        PixelReader pixelReader = image.getPixelReader();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixelMatrix[row][col] = pixelReader.getArgb(col, row);
            }
        }

        return pixelMatrix;
    }

    /**
     * Omvandling åt andra hållet
     */

    public static Image pixelsToImage(int[][] pixelMatrix) {

        int width = pixelMatrix.length;
        int height = pixelMatrix[0].length;

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        int argbValue;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                argbValue = pixelMatrix[x][y];
                pixelWriter.setArgb(x,y,argbValue);
            }
        }

        return writableImage;
    }
}