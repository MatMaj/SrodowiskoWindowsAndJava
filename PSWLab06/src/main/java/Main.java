
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/appLoginView.fxml"));
        Parent root = (Parent)fxmlLoader.load();

        primaryStage.setTitle("Aplikacja logująca");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.getScene().getStylesheets().add("/appLoginStyle.css");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
