package Game;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Menu {
    private Stage menuStage;

    Menu(Stage primaryStage){
        this.menuStage = primaryStage;
    }

    void start(){
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(50));
        vbox.setSpacing(50);
        vbox.setId("menu-class");

        Text title = new Text("Welcome in 2048!");
        title.setId("title");
        vbox.getChildren().add(title);

        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.TOP_CENTER);

        Button startGame = new Button("Start");
        startGame.setPrefSize(200,50);
        startGame.setId("startGame");
        vbox2.getChildren().add(startGame);

        Button highscores = new Button("High scores");
        highscores.setPrefSize(200,50);
        highscores.setId("highscores");
        vbox2.getChildren().add(highscores);

        Button credits = new Button("Credits");
        credits.setPrefSize(200,50);
        credits.setId("credits");
        vbox2.getChildren().add(credits);

        Button exit = new Button("Exit");
        exit.setPrefSize(200,50);
        exit.setId("exit");
        vbox2.getChildren().add(exit);

        vbox.getChildren().add(vbox2);

        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                new Game2048(menuStage).start();
            }
        });

        highscores.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                new HighScore(getMenuStage()).start();
            }
        });

        credits.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                new Credits(getMenuStage()).start();
            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        menuStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        Scene scene = new Scene(vbox, 465,465);
        scene.getStylesheets().add("myStyle.css");

        menuStage.getIcons().add(new Image("Game/assets/2048.png"));
        menuStage.setResizable(false);
        menuStage.centerOnScreen();
        menuStage.setTitle("MENU");
        menuStage.setScene(scene);
        menuStage.show();
    }

    public Stage getMenuStage(){
        return menuStage;
    }

}
