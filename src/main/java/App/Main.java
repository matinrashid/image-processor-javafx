package App;

import controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ImageModelFacade;
import view.MainView;

/**
 * Startar bildbehandlingsprogrammet.
 */
public class Main extends Application {

    /**
     * Skapar och visar programmets fönster.
     *
     * @param primaryStage programmets huvudfönster
     */
    @Override
    public void start(Stage primaryStage) {

        ImageModelFacade model = new ImageModelFacade();

        MainView view = new MainView();

        MainController controller = new MainController(view, model, primaryStage);

        view.setController(controller);

        Scene scene = new Scene(view, 1100, 600);

        primaryStage.setTitle("Bildbehandlingsprogram");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Startar JavaFX.
     *
     * @param args programmets argument
     */
    public static void main(String[] args) {
        launch(args);
    }
}
