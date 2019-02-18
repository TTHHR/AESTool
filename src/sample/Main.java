package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // 这里的root从FXML文件中加载进行初始化，这里FXMLLoader类用于加载FXML文件
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        GridPane root = fxmlLoader.load();
        primaryStage.setTitle("AESTool");
        primaryStage.setScene(new Scene(root, 640, 480));
        Controller controller=fxmlLoader.getController();
        controller.init(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
