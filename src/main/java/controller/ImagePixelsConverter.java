
package controller;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Omvandlar bilder till pixelmatriser och tillbaka.
 */
public class ImagePixelsConverter {

    /**
     * Omvandlar en bild till en pixelmatris.
     *
     * @param image bilden som ska omvandlas
     * @return en pixelmatris med bildens färgvärden
     */
    public static int[][] imageToPixels(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        int[][] pixelMatrix = new int[height][width];
        PixelReader pixelReader = image.getPixelReader();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixelMatrix[row][col] =
                        pixelReader.getArgb(col, row);
            }
        }

        return pixelMatrix;
    }

    /**
     * Omvandlar en pixelmatris till en bild.
     *
     * @param pixelMatrix matrisen som ska omvandlas
     * @return bilden som ett Image-objekt
     */
    public static Image pixelsToImage(int[][] pixelMatrix) {

        int height = pixelMatrix.length;
        int width = pixelMatrix[0].length;

        WritableImage writableImage =
                new WritableImage(width, height);

        PixelWriter pixelWriter = writableImage.getPixelWriter();

        int argbValue;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                argbValue = pixelMatrix[row][col];
                pixelWriter.setArgb(col, row, argbValue);
            }
        }

        return writableImage;
    }
}
