
package model;

/**
 * Fasadklass för bildbehandlingsmodellen.
 *
 * Klassen ansvarar för att lagra originalbildens
 * pixelmatris och den aktuella pixelmatrisen.
 *
 * Den erbjuder metoder för att utföra bildbehandling,
 * återställa originalbilden och beräkna histogram.
 *
 * Klassen fungerar som ett gränssnitt mellan
 * kontrollern och modellens övriga klasser.
 */
public class ImageModelFacade {

    private int[][] originalPixels;
    private int[][] currentPixels;

    /**
     * Sparar en ny bild i modellen.
     *
     * Originalbilden och den aktuella bilden
     * sparas som separata pixelmatriser.
     *
     * @param pixels pixelmatrisen som ska lagras
     */
    public void setImage(int[][] pixels) {
        originalPixels = copyPixels(pixels);
        currentPixels = copyPixels(pixels);
    }

    /**
     * Returnerar den aktuella pixelmatrisen.
     *
     * @return en kopia av den aktuella pixelmatrisen
     */
    public int[][] getCurrentPixels() {
        return copyPixels(currentPixels);
    }

    /**
     * Utför en bildbehandlingsoperation på den
     * aktuella pixelmatrisen.
     *
     * @param processor bildbehandlingsmetoden som ska användas
     */
    public void applyProcessor(IPixelProcessor processor) {
        currentPixels = processor.processImage(currentPixels);
    }

    /**
     * Återställer den aktuella bilden till originalbilden.
     */
    public void revertToOriginal() {
        currentPixels = copyPixels(originalPixels);
    }

    /**
     * Beräknar histogrammet för den aktuella bilden.
     *
     * @return en 256x3-matris med antalet pixlar
     *         för varje färgintensitet
     */
    public int[][] getHistogram() {
        return HistogramCalculator.calculateHistogram(currentPixels);
    }

    /**
     * Skapar en kopia av en pixelmatris.
     *
     * @param pixels pixelmatrisen som ska kopieras
     * @return en ny pixelmatris med samma värden
     */
    private int[][] copyPixels(int[][] pixels) {

        if (pixels == null) {
            return null;
        }

        int[][] copy = new int[pixels.length][];

        for (int row = 0; row < pixels.length; row++) {
            copy[row] = pixels[row].clone();
        }

        return copy;
    }
}
