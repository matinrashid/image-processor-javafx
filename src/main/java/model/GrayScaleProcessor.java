package model;

/**
 * Bildbehandlingsklass som omvandlar en bild till gråskala.
 *
 * Klassen implementerar {@link IPixelProcessor} och behandlar en pixelmatris
 * genom att beräkna medelvärdet av den röda, gröna och blå färgkomponenten
 * för varje pixel. Det beräknade värdet används sedan som nytt värde för
 * röd, grön och blå.
 */
public class GrayScaleProcessor implements IPixelProcessor {

    /**
     * Omvandlar den angivna pixelmatrisen till gråskala.
     *
     * För varje pixel beräknas medelvärdet av pixelns röda, gröna och blå
     * färgkomponenter. En ny pixel skapas därefter där samtliga tre
     * färgkomponenter har detta medelvärde. Alpha-värdet behålls oförändrat.
     *
     * @param originalPixels pixelmatrisen som ska omvandlas till gråskala
     * @return en ny pixelmatris som innehåller den gråskalekonverterade bilden
     */
    @Override
    public int[][] processImage(int[][] originalPixels) {

        int height = originalPixels.length;
        int width = originalPixels[0].length;

        int[][] newPixels = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int pixel = originalPixels[row][col];

                int alpha = PixelConverter.getAlpha(pixel);
                int red = PixelConverter.getRed(pixel);
                int green = PixelConverter.getGreen(pixel);
                int blue = PixelConverter.getBlue(pixel);

                int gray = (int) ((red + green + blue) / 3.0);

                newPixels[row][col] = PixelConverter.toArgbPixel(alpha, gray, gray, gray);
            }
        }

        return newPixels;
    }
}