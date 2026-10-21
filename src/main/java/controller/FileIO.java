
package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Klass för att läsa in och spara bilder.
 */
public class FileIO {

    /**
     * Läser in en bild från en fil.
     *
     * @param file filen som ska läsas
     * @return bilden som ett Image-objekt
     * @throws IOException om filen inte kan läsas
     */
    public static Image loadImage(File file) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(file);

        if (bufferedImage == null) {
            throw new IOException("Kunde inte läsa bilden.");
        }

        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    /**
     * Sparar en bild som en PNG-fil.
     *
     * @param image bilden som ska sparas
     * @param file filen där bilden ska sparas
     * @throws IOException om bilden inte kan sparas
     */
    public static void saveImage(Image image, File file) throws IOException {

        BufferedImage bufferedImage =
                SwingFXUtils.fromFXImage(image, null);

        boolean success = ImageIO.write(
                bufferedImage, "png", file
        );

        if (!success) {
            throw new IOException("Kunde inte spara bilden.");
        }
    }
}
