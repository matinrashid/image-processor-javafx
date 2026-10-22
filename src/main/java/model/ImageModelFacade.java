
package model;

/**
 * ---FAÇADE---
 *
 * Den erbjuder metoder för att utföra bildbehandling,
 * återställa originalbilden och beräkna histogram.
 *
 * Det är detta controllern känner till, inte logiken bakom.
 */
public class ImageModelFacade {

    private int[][] originalPixels; // orginalet som aldrig ändras
    private int[][] currentPixels;

    public void setImage(int[][] pixels) {
        originalPixels = copyPixels(pixels);
        currentPixels = copyPixels(pixels);
    }

    /**
     * Returnerar kopia av aktuella matrisen
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
     * varje rad med clone() (en djup kopia)
     * Endast pixels.clone() hade gett en ytlig kopia där raderna delas
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
