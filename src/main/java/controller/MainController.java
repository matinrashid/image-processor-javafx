
package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.*;
import view.MainView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Hanterar användarens val i programmet.
 * Kopplar ihop vyn och modellen.
 */
public class MainController {

    private final MainView view;
    private final ImageModelFacade model;
    private final Stage stage;

    /**
     * Skapar en controller med en vy och en modell.
     *
     * @param view programmets grafiska gränssnitt
     * @param model modellen för bildbehandling
     * @param stage programmets fönster
     */
    public MainController(MainView view, ImageModelFacade model, Stage stage) {
        this.view = view;
        this.model = model;
        this.stage = stage;
    }

    /**
     * Öppnar en bild från datorn.
     */
    public void onLoadImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Öppna bild");

        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Bildfiler", "*.png", "*.jpg",
                        "*.jpeg", "*.bmp", "*.gif")
        );

        File file = chooser.showOpenDialog(stage);

        if (file == null) {
            return;
        }

        try {
            Image image = FileIO.loadImage(file);

            int[][] pixels =
                    ImagePixelsConverter.imageToPixels(image);

            model.setImage(pixels);
            updateView();

        } catch (IOException | RuntimeException e) {
            showError("Kunde inte öppna bilden.");
        }
    }

    /**
     * Sparar den aktuella bilden som en PNG-fil.
     */
    public void onSaveImage() {
        if (!hasImage()) {
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Spara bild");
        chooser.setInitialFileName("bild.png");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "PNG-bild", "*.png")
        );

        File file = chooser.showSaveDialog(stage);

        if (file == null) {
            return;
        }

        if (!file.getName().toLowerCase().endsWith(".png")) {
            file = new File(file.getAbsolutePath() + ".png");
        }

        try {
            Image image = ImagePixelsConverter.pixelsToImage(
                    model.getCurrentPixels()
            );

            FileIO.saveImage(image, file);

        } catch (IOException | RuntimeException e) {
            showError("Kunde inte spara bilden.");
        }
    }

    /**
     * Gör den aktuella bilden gråskalig.
     */
    public void onGrayScaleSelected() {
        applyProcessor(new GrayScaleProcessor());
    }

    /**
     * Gör den aktuella bilden suddigare.
     */
    public void onBlurSelected() {
        applyProcessor(new BlurProcessor());
    }

    /**
     * Gör den aktuella bilden skarpare.
     * Metoden är avsedd för gråskalebilder.
     */
    public void onSharpeningSelected() {
        applyProcessor(new SharpeningProcessor());
    }

    /**
     * Ändrar bildens kontrast med Window/Level.
     * Användaren väljer värden med två sliders.
     */
    public void onWindowLevelSelected() {
        if (!hasImage()) {
            return;
        }

        Dialog<double[]> dialog = new Dialog<>();
        dialog.setTitle("Window/Level");
        dialog.setHeaderText("Välj kontrastvärden");

        Slider levelSlider = new Slider(0, 255, 75);
        Slider windowSlider = new Slider(1, 255, 35);

        levelSlider.setShowTickLabels(true);
        windowSlider.setShowTickLabels(true);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0,
                new javafx.scene.control.Label("Level"),
                levelSlider);

        grid.addRow(1,
                new javafx.scene.control.Label("Window"),
                windowSlider);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK, ButtonType.CANCEL
        );

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return new double[]{
                        levelSlider.getValue(),
                        windowSlider.getValue()
                };
            }
            return null;
        });

        Optional<double[]> result = dialog.showAndWait();

        if (result.isPresent()) {
            double[] values = result.get();

            applyProcessor(new WindowLevelProcessor(
                    values[0], values[1]
            ));
        }
    }

    /**
     * Återställer bilden till originalet.
     */
    public void onRevertToOriginal() {
        if (!hasImage()) {
            return;
        }

        model.revertToOriginal();
        updateView();
    }

    /**
     * Utför en bildbehandling och uppdaterar vyn.
     *
     * @param processor bildbehandlingen som ska utföras
     */
    private void applyProcessor(IPixelProcessor processor) {
        if (!hasImage()) {
            return;
        }

        try {
            model.applyProcessor(processor);
            updateView();

        } catch (RuntimeException e) {
            showError("Bildbehandlingen misslyckades.");
        }
    }

    /**
     * Uppdaterar bilden och histogrammet i vyn.
     */
    private void updateView() {
        int[][] pixels = model.getCurrentPixels();

        Image image =
                ImagePixelsConverter.pixelsToImage(pixels);

        view.setImage(image);
        view.updateHistogram(model.getHistogram());
    }

    /**
     * Kontrollerar att en bild finns inläst.
     *
     * @return true om en bild finns, annars false
     */
    private boolean hasImage() {
        if (model.getCurrentPixels() == null) {
            showError("Öppna en bild först.");
            return false;
        }

        return true;
    }

    /**
     * Visar ett felmeddelande för användaren.
     *
     * @param message felmeddelandet som ska visas
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fel");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
