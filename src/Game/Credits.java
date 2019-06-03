package Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Credits {

    private Stage creditsStage;

    Credits(Stage creditsStage){
        this.creditsStage = creditsStage;
    }

    void start(){
        BorderPane bpane = new BorderPane();
        bpane.setPadding(new Insets(50,0,50,0));
        bpane.setId("credits-class");

        Text credits1 = new Text("Game created by:");
        credits1.setId("credits1");

        Text credits2 = new Text("Mateusz Wietrak");
        credits2.setId("credits2");

        bpane.setTop(credits1);
        bpane.setCenter(credits2);
        BorderPane.setAlignment(credits1,Pos.CENTER);

        Button backBTN = new Button("Back");
        backBTN.setId("backbtn");
        backBTN.setPrefSize(200,50);
        bpane.setBottom(backBTN);
        BorderPane.setAlignment(backBTN,Pos.CENTER);

        backBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                new Menu(creditsStage).start();
            }
        });

        Scene scene = new Scene(bpane, 465,465);
        scene.getStylesheets().add("myStyle.css");

        creditsStage.getIcons().add(new Image("Game/assets/2048.png"));
        creditsStage.setResizable(false);
        creditsStage.centerOnScreen();
        creditsStage.setTitle("Credits");
        creditsStage.setScene(scene);
        creditsStage.show();

    }
}
