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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HighScore {

    private Stage highScoreStage;

    HighScore(Stage highScoreStage){
        this.highScoreStage = highScoreStage;
    }

    void start(){
        BorderPane bpane = new BorderPane();
        bpane.setPadding(new Insets(50,0,50,0));
        bpane.setId("credits-class");

        Text highScore1 = new Text("High Score:");
        highScore1.setId("credits1");

        String scoreString = "00000";
        try {
            scoreString = readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Text highScore2 = new Text(scoreString);
        highScore2.setId("credits2");

        bpane.setTop(highScore1);
        bpane.setCenter(highScore2);
        BorderPane.setAlignment(highScore1,Pos.CENTER);

        Button backBTN = new Button("Back");
        backBTN.setId("backbtn");
        backBTN.setPrefSize(200,50);
        bpane.setBottom(backBTN);
        BorderPane.setAlignment(backBTN,Pos.CENTER);

        backBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                new Menu(highScoreStage).start();
            }
        });

        Scene scene = new Scene(bpane, 465,465);
        scene.getStylesheets().add("myStyle.css");

        highScoreStage.getIcons().add(new Image("Game/assets/2048.png"));
        highScoreStage.setResizable(false);
        highScoreStage.centerOnScreen();
        highScoreStage.setTitle("High Score");
        highScoreStage.setScene(scene);
        highScoreStage.show();

    }
    public String readFromFile() throws IOException {
        String fileText;
        BufferedReader fileReader = null;

        try{
            fileReader = new BufferedReader(new FileReader("F:/JavaWorkspace/2048Projekt/src/Game/assets/score.txt"));
            fileText = fileReader.readLine();
        } finally {
            if(fileReader != null){
                fileReader.close();
            }
        }
        return fileText;
    }
}
