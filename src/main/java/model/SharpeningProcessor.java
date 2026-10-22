package model;

/**
 * Gör kanterna tydligare
 *
 *
 * Intensitetsvärden som hamnar utanför intervallet 0 till 255
 * begränsas till detta intervall.
 */
public class SharpeningProcessor implements IPixelProcessor {

    /**
     *
     * Metoden skapar först en suddig version av originalbilden med hjälp
     * av {@link BlurProcessor}.
     *
     *
     * @param originalPixels pixelmatrisen som ska göras skarpare
     * @return en ny pixelmatris med den skärpta bilden
     */
    @Override
    public int[][] processImage(int[][] originalPixels) {

        BlurProcessor blurProcessor = new BlurProcessor();
        int[][] blurredPixels = blurProcessor.processImage(originalPixels);

        int height = originalPixels.length;
        int width = originalPixels[0].length;

        int[][] newPixels = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int originalPixel = originalPixels[row][col];
                int blurredPixel = blurredPixels[row][col];


                /*Endast getRed då r=b=g i en grå bild */
                int originalValue = PixelConverter.getRed(originalPixel);

                int blurredValue = PixelConverter.getRed(blurredPixel);

                /**
                 * Differans mellan original och blurrad bild som appliceras till originalet.
                 * Där det är skarpa kanter får vi högt värde och jämna områden ger mot 0
                 * Bilden blir ljusare jämfört med grannarna
                 */

                int difference = originalValue - blurredValue;

                int sharpenedValue = originalValue + difference;

                if (sharpenedValue < 0)
                {
                    sharpenedValue = 0;
                }

                /**
                 * begränsar till intervallet
                 */

                if (sharpenedValue > 255)
                {
                    sharpenedValue = 255;
                }

                int alpha =
                        PixelConverter.getAlpha(originalPixel);

                newPixels[row][col] =
                        PixelConverter.toArgbPixel(alpha,sharpenedValue,sharpenedValue,sharpenedValue);
            }
        }

        return newPixels;
    }
}