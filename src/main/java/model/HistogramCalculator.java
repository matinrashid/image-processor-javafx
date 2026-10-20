package model;

/**
 * Klass som beräknar histogrammet för en bild.
 *
 * Histogrammet visar antalet pixlar för varje
 * intensitetsvärde mellan 0 och 255 för färgerna
 * röd, grön och blå.
 */
public class HistogramCalculator {
    /**
     * Beräknar histogrammet för en given pixelmatris.
     *
     * Metoden går igenom bildens alla pixlar och räknar
     * hur många gånger varje intensitetsvärde förekommer
     * för respektive färgkanal.
     *
     * @param pixels pixelmatrisen som representerar bilden
     * @return en 256x3-matris med antalet pixlar för varje
     *         intensitetsvärde för röd, grön och blå
     */
    public static int[][] calculateHistogram(int[][] pixels) {

        int[][] histogram = new int[256][3];

        int height = pixels.length;
        int width = pixels[0].length;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int pixel = pixels[row][col];

                int red = PixelConverter.getRed(pixel);
                int green = PixelConverter.getGreen(pixel);
                int blue = PixelConverter.getBlue(pixel);

                histogram[red][0]++;
                histogram[green][1]++;
                histogram[blue][2]++;
            }
        }

        return histogram;
    }
}
