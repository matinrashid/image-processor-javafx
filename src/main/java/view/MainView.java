
package view;

import controller.MainController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Visar programmets grafiska gränssnitt.
 * Innehåller menyer, bildvisning och histogram.
 */
public class MainView extends BorderPane {

    private final ImageView imageView;
    private final HistogramView histogramView;

    private final MenuItem openItem;
    private final MenuItem saveItem;
    private final MenuItem revertItem;
    private final MenuItem grayScaleItem;
    private final MenuItem blurItem;
    private final MenuItem sharpeningItem;
    private final MenuItem windowLevelItem;

    /**
     * Skapar programmets grafiska gränssnitt.
     */
    public MainView() {

        imageView = new ImageView();
        histogramView = new HistogramView();

        imageView.setFitWidth(500);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

        // Skapar menyerna
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu processingMenu = new Menu("Processing");

        // Skapar menyalternativen
        openItem = new MenuItem("Open Image");
        saveItem = new MenuItem("Save Image");
        revertItem = new MenuItem("Revert to Original");

        grayScaleItem = new MenuItem("GrayScale");
        blurItem = new MenuItem("Blur");
        sharpeningItem = new MenuItem("Sharpening");
        windowLevelItem = new MenuItem("Window/Level");

        // Lägger till alternativen i menyerna
        fileMenu.getItems().addAll(openItem, saveItem);

        editMenu.getItems().add(revertItem);

        processingMenu.getItems().addAll(
                grayScaleItem,
                blurItem,
                sharpeningItem,
                windowLevelItem
        );

        // Skapar menyraden
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(
                fileMenu, editMenu, processingMenu
        );

        // Placerar bilden och histogrammet
        HBox content = new HBox(20);
        content.getChildren().addAll(
                imageView, histogramView
        );

        // Placerar komponenterna i fönstret
        setTop(menuBar);
        setCenter(content);

        // Inaktiverar bildfunktioner innan en bild öppnats
        setImageActionsDisabled(true);
    }

    /**
     * Kopplar menyvalen till kontrollerns metoder.
     *
     * @param controller programmets controller
     */
    public void setController(MainController controller) {

        openItem.setOnAction(
                event -> controller.onLoadImage()
        );

        saveItem.setOnAction(
                event -> controller.onSaveImage()
        );

        revertItem.setOnAction(
                event -> controller.onRevertToOriginal()
        );

        grayScaleItem.setOnAction(
                event -> controller.onGrayScaleSelected()
        );

        blurItem.setOnAction(
                event -> controller.onBlurSelected()
        );

        sharpeningItem.setOnAction(
                event -> controller.onSharpeningSelected()
        );

        windowLevelItem.setOnAction(
                event -> controller.onWindowLevelSelected()
        );
    }

    /**
     * Visar en bild i gränssnittet.
     *
     * @param image bilden som ska visas
     */
    public void setImage(Image image) {
        imageView.setImage(image);
        setImageActionsDisabled(image == null);
    }

    /**
     * Uppdaterar histogrammet för den aktuella bilden.
     *
     * @param histogram matris med histogramvärden
     */
    public void updateHistogram(int[][] histogram) {
        histogramView.updateView(histogram);
    }

    /**
     * Aktiverar eller inaktiverar bildfunktionerna.
     *
     * @param disabled true om funktionerna ska inaktiveras
     */
    private void setImageActionsDisabled(boolean disabled) {

        saveItem.setDisable(disabled);
        revertItem.setDisable(disabled);
        grayScaleItem.setDisable(disabled);
        blurItem.setDisable(disabled);
        sharpeningItem.setDisable(disabled);
        windowLevelItem.setDisable(disabled);
    }
}
