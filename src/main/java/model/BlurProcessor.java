package model;

/**
 * Bildbehandlingsklass som gör en bild suddigare.
 *
 * Klassen implementerar {@link IPixelProcessor} och behandlar bilden
 * genom att beräkna ett viktat medelvärde av varje pixel och dess
 * åtta grannpixlar.
 *
 * Vikterna som används är:
 *
 * 1 2 1
 * 2 4 2
 * 1 2 1
 *
 * Resultatet blir en utjämnad bild där skarpa övergångar mellan
 * pixlar minskar.
 */
public class BlurProcessor implements IPixelProcessor {

    /**
     *
     *  Den starka pixeln “blurras” mot sina grannar.
     *
     * @param originalPixels pixelmatrisen som ska göras suddigare
     * @return en ny pixelmatris som innehåller den suddigare bilden
     */
    @Override
    public int[][] processImage(int[][] originalPixels) {

        int height = originalPixels.length;
        int width = originalPixels[0].length;

        int[][] newPixels = new int[height][width];

        int[][] weights = {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                if (row == 0 || row == height - 1 ||
                        col == 0 || col == width - 1) {

                    newPixels[row][col] = originalPixels[row][col];
                    continue;
                }

                /**
                 * rad 0 och kolumn 0 har ingar grannar (kanter) så vi överför de som de är.
                 */

                //Alpha spelar ingen roll här eftersom den är 255.
                double redSum = 0;
                double greenSum = 0;
                double blueSum = 0;

                for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                    for (int colOffset = -1; colOffset <= 1; colOffset++) {

                        int pixel =
                                originalPixels[row + rowOffset][col + colOffset];

                        int weight =
                                weights[rowOffset + 1][colOffset + 1];

                        redSum += PixelConverter.getRed(pixel) * weight;
                        greenSum += PixelConverter.getGreen(pixel) * weight;
                        blueSum += PixelConverter.getBlue(pixel) * weight;
                    }
                }

                int red = (int) (redSum / 16.0);
                int green = (int) (greenSum / 16.0);
                int blue = (int) (blueSum / 16.0);

                int alpha =
                        PixelConverter.getAlpha(originalPixels[row][col]);

                newPixels[row][col] =
                        PixelConverter.toArgbPixel(alpha, red, green, blue);
            }
        }

        return newPixels;
    }
}