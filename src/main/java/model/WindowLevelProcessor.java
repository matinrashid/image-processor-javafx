package model;

/**
 * Bildbehandlingsklass som ändrar kontrasten i en gråskalebild
 * med hjälp av Window/Level.
 *
 * Level anger början på intensitetsintervallet som ska förstärkas
 * och window anger intervallets bredd.
 *
 * Intensiteter under level sätts till 0.
 * Intensiteter inom intervallet från level till level + window
 * skalas om till intervallet 0 till 255.
 * Intensiteter över level + window sätts till 255.
 */
public class WindowLevelProcessor implements IPixelProcessor {

    private double level;
    private double window;

    /**
     * Skapar en WindowLevelProcessor med angivna värden
     * för level och window.
     *
     * @param level början på intensitetsintervallet
     * @param window bredden på intensitetsintervallet
     */
    public WindowLevelProcessor(double level, double window) {
        if (window <= 0) {
            throw new IllegalArgumentException(
                    "Window must be greater than 0"
            );
        }
        this.level = level;
        this.window = window;
    }

    /**
     * Applicerar Window/Level på den angivna gråskalebilden.
     *
     * För varje pixel hämtas intensiteten. Intensiteter under level
     * sätts till 0 och intensiteter över level + window sätts till 255.
     * Intensiteter inom intervallet skalas linjärt om till intervallet
     * 0 till 255.
     *
     * @param originalPixels pixelmatrisen som ska behandlas
     * @return en ny pixelmatris med ändrad kontrast
     */
    @Override
    public int[][] processImage(int[][] originalPixels) {

        int height = originalPixels.length;
        int width = originalPixels[0].length;

        int[][] newPixels = new int[height][width];

        double max = level + window;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int pixel = originalPixels[row][col];

                int alpha = PixelConverter.getAlpha(pixel);
                int intensity = PixelConverter.getRed(pixel);

                int newIntensity;

                if (intensity < level) {
                    newIntensity = 0;

                } else if (intensity > max) {
                    newIntensity = 255;

                } else {
                    newIntensity = (int) ((intensity - level) * (255.0 / window));
                }

                newPixels[row][col] = PixelConverter.toArgbPixel(alpha, newIntensity, newIntensity, newIntensity);
            }
        }

        return newPixels;
    }
}