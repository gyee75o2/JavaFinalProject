import battle.BattleController;
import common.AuthorAnno;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.event.KeyEvent;

@AuthorAnno(author = "何峰彬")
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("HuLuBattle SPACE:开始；F:切换阵型；L:打开记录");
        primaryStage.setOnCloseRequest(event-> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}