package cr.ac.una.proyecto1;

import cr.ac.una.proyecto1.util.FlowController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage,null);
        stage.getIcons().add(new Image("cr/ac/una/proyecto1/resources/LogoApp.jpg"));
        stage.setTitle("UNA Proyecto");
        FlowController.getInstance().goViewInWindow("InicioView");
    }

    public static void main(String[] args) {
        launch();
    }

}