package model;
/**
 * Interface for image processing operations.
 * All image processors implement this interface.
 *
 * ---STRATEGY---
 * Fasaden kan köra vilken som helst utan att veta vilken det är (polymorfism)
 */
public interface IPixelProcessor {


    /**
     * Processes an image represented by a pixel matrix.
     *
     * @param originalPixels the original pixel matrix
     * @return a new pixel matrix containing the processed image
     */
    public int[][]  processImage(int[][] originalPixels);
}
