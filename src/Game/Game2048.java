package Game;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class Game2048{

    private Stage primaryStage;
    private String mainscore = "00000";
    private String bestScoreFromFile;
    {
        try {
            bestScoreFromFile = readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Text score = new Text(mainscore);
    Text bestScore = new Text(bestScoreFromFile);

    Tile[][] tab = new Tile[4][4];
    int[][] modTab = new int[4][4];

    Game2048(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    void start(){
        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setPadding(new Insets(30,30,30,30));

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(108);
        flowPane.setPadding(new Insets(40,0,0,0));

        Button backBTN = new Button("Back");
        backBTN.setPrefSize(75,50);
        backBTN.setId("backGame");

        score.setId("score");
        bestScore.setId("bestScore");

        flowPane.getChildren().add(backBTN);
        flowPane.getChildren().add(score);
        flowPane.getChildren().add(bestScore);

        vbox.getChildren().add(flowPane);
        vbox.getChildren().add(grid);
        vbox.setId("game-class");

        Scene scene = new Scene(vbox,465,575);
        scene.getStylesheets().add("myStyle.css");

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    moveUp(); break;
                    case DOWN:  moveDown(); break;
                    case LEFT:  moveLeft(); break;
                    case RIGHT: moveRight(); break;
                }
            }
        });

        backBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    saveToFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                new Menu(primaryStage).start();
            }
        });

        for(int i = 0 ; i<4 ; i++) {
            for (int j = 0; j < 4; j++) {
                tab[j][i] = new Tile(j, i);
                grid.add(tab[j][i].getPane(), j, i);
            }
        }
        randomTileGenerator();
        randomTileGenerator();
/*
        addSpecificTile(0,0,1);
        addSpecificTile(1,0,2);
        addSpecificTile(2,0,3);
        addSpecificTile(3,0,3);
        addSpecificTile(0,1,5);
        addSpecificTile(1,1,6);
        addSpecificTile(2,1,7);
        addSpecificTile(3,1,8);
        addSpecificTile(0,2,9);
        addSpecificTile(1,2,10);
        addSpecificTile(2,2,11);
        addSpecificTile(3,2,1);
        addSpecificTile(0,3,2);
        addSpecificTile(1,3,3);
        addSpecificTile(2,3,4);
        addSpecificTile(3,3,5);
        */
        primaryStage.getIcons().add(new Image("Game/assets/2048.png"));
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Game2048");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

    }
    public void addSpecificTile(int x,int y,int value){
        tab[x][y].setImage(value);
    }
    public void randomTileGenerator(){
        boolean goNext = false;
        int rtgX = 0;
        int rtgY = 0;

            while (goNext == false) {
                rtgX = ThreadLocalRandom.current().nextInt(0, 4);
                rtgY = ThreadLocalRandom.current().nextInt(0, 4);

                if(tab[rtgX][rtgY].getValue()==0){
                    int rtgTwoOrFour = ThreadLocalRandom.current().nextInt(1, 11);
                    if(rtgTwoOrFour<9){
                        tab[rtgX][rtgY].setImage(1);
                        setScore(1);
                        goNext=true;
                    }
                    else{
                        tab[rtgX][rtgY].setImage(2);
                        setScore(2);
                        goNext=true;
                    }
                }
            }
    }

    public void oneOrTwoRandomTileGenerator() {
        int checkContener = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tab[j][i].getValue() == 0)
                    checkContener++;
            }
        }

        if (checkContener == 1) {
            randomTileGenerator();
        }
        else {
            int rtgOneOrTwoNewTiles = ThreadLocalRandom.current().nextInt(1, 11);
            if (rtgOneOrTwoNewTiles > 9) {
                randomTileGenerator();
                randomTileGenerator();
            } else {
                randomTileGenerator();
            }
        }
    }

    public int[][] saveTab(){
        int[][] savedTab = new int[4][4];
        for(int i = 0 ; i<4 ; i++) {
            for (int j = 0; j < 4; j++) {
                savedTab[j][i]=tab[j][i].getValue();
            }
        }
        return savedTab;
    }

    public boolean checkTabModify(int[][] tab1, int[][] tab2){
        int counter=0;
        for(int i = 0 ; i<4 ; i++) {
            for (int j = 0; j < 4; j++) {
                if(tab1[j][i]!=tab2[j][i]){
                    counter++;
                }
            }
        }
        if(counter!=0) {
            return true;
        }
        else{
            return false;
        }
    }

    public void clearModTab(){
        for(int i = 0 ; i<4 ; i++) {
            for (int j = 0; j < 4; j++) {
                modTab[j][i] = 0;
            }
        }
    }
    public void moveLeft(){
        int[][]sTabBefore = saveTab();
        moveILeft();
        moveIILeft();
        moveIIILeft();
        clearModTab();
        int[][]sTabAfter = saveTab();
        if(checkTabModify(sTabBefore,sTabAfter)){
            oneOrTwoRandomTileGenerator();
        }
        gameOverCheck();
    }
    public void moveUp(){
        int[][] sTabBefore = saveTab();
        moveIUp();
        moveIIUp();
        moveIIIUp();
        clearModTab();
        int[][] sTabAfter = saveTab();
        if(checkTabModify(sTabBefore,sTabAfter)){
            oneOrTwoRandomTileGenerator();
        }
        gameOverCheck();
    }
    public void moveRight(){
        int[][] sTabBefore = saveTab();
        moveIRight();
        moveIIRight();
        moveIIIRight();
        clearModTab();
        int[][] sTabAfter = saveTab();
        if(checkTabModify(sTabBefore,sTabAfter)){
            oneOrTwoRandomTileGenerator();
        }
        gameOverCheck();
    }
    public void moveDown(){
        int[][] sTabBefore = saveTab();
        moveIDown();
        moveIIDown();
        moveIIIDown();
        clearModTab();
        int[][] sTabAfter = saveTab();
        if(checkTabModify(sTabBefore,sTabAfter)){
            oneOrTwoRandomTileGenerator();
        }
        gameOverCheck();
    }
    public void setScore(int point){
        switch(point) {
            case 1: point = 2;
                break;
            case 2: point = 4;
                break;
            case 3: point = 8;
                break;
            case 4: point = 16;
                break;
            case 5: point = 32;
                break;
            case 6: point = 64;
                break;
            case 7: point = 128;
                break;
            case 8: point = 256;
                break;
            case 9: point = 512;
                break;
            case 10: point = 1024;
                break;
            case 11: point = 2048;
                break;
        }
            int tmpScore = Integer.parseInt(mainscore);
            tmpScore=tmpScore+point;
            mainscore = Integer.toString(tmpScore);
            String zeroString;

            if(tmpScore/10 < 1){
                zeroString = "0000";
            }
            else if(tmpScore/100 < 1){
                zeroString = "000";
            }
            else if(tmpScore/1000 < 1){
                zeroString = "00";
            }
            else if(tmpScore/10000 < 1){
                zeroString = "0";
            }
            else{
                zeroString = "";
            }

            score.setText(zeroString+mainscore);
        }

    public void moveILeft(){
        for(int i=0;i<4;i++){
            if(tab[0][i].getValue()==0){
                tab[0][i].setImage(tab[1][i].getValue());
                tab[1][i].setImage(0);
                if(modTab[1][i]!=0){
                    modTab[0][i]=modTab[1][i];
                    modTab[1][i]=0;
                }
            }
            if(tab[0][i].getValue()==tab[1][i].getValue() && tab[0][i].getValue()!=0 && tab[1][i].getValue()!=0 && modTab[0][i]==0 && modTab[1][i] == 0){
                tab[0][i].setImage(tab[0][i].getValue()+1);
                setScore(tab[0][i].getValue());
                tab[1][i].setImage(0);
                modTab[0][i]=1;
            }
        }
    }
    public void moveIILeft() {
        for (int i = 0; i < 4; i++) {
            if (tab[1][i].getValue() == 0) {
                tab[1][i].setImage(tab[2][i].getValue());
                tab[2][i].setImage(0);
                if(modTab[2][i]!=0){
                    modTab[1][i]=modTab[2][i];
                    modTab[2][i]=0;
                }
            }
            if (tab[1][i].getValue() == tab[2][i].getValue() && tab[1][i].getValue() != 0 && tab[2][i].getValue() != 0 && modTab[1][i] == 0 && modTab[2][i] == 0) {
                tab[1][i].setImage(tab[1][i].getValue() + 1);
                setScore(tab[1][i].getValue());
                tab[2][i].setImage(0);
                modTab[1][i] = 1;
            }
        }
        moveILeft();
    }
    public void moveIIILeft(){
        for (int i = 0; i < 4; i++) {
            if (tab[2][i].getValue() == 0) {
                tab[2][i].setImage(tab[3][i].getValue());
                tab[3][i].setImage(0);
                if(modTab[3][i]!=0){
                    modTab[2][i]=modTab[3][i];
                    modTab[3][i]=0;
                }
            }
            if (tab[2][i].getValue() == tab[3][i].getValue() && tab[2][i].getValue() != 0 && tab[3][i].getValue() != 0 && modTab[2][i] == 0 && modTab[3][i] == 0) {
                tab[2][i].setImage(tab[2][i].getValue() + 1);
                setScore(tab[2][i].getValue());
                tab[3][i].setImage(0);
                modTab[2][i] = 1;
            }
        }
        moveIILeft();
    }

    public void moveIUp(){
        for(int i=0;i<4;i++){
            if(tab[i][0].getValue()==0){
                tab[i][0].setImage(tab[i][1].getValue());
                tab[i][1].setImage(0);
                if(modTab[i][1]!=0){
                    modTab[i][0]=modTab[i][1];
                    modTab[i][1]=0;
                }
            }
            if(tab[i][0].getValue()==tab[i][1].getValue() && tab[i][0].getValue()!=0 && tab[i][1].getValue()!=0 && modTab[i][0]==0 && modTab[i][1] == 0){
                tab[i][0].setImage(tab[i][0].getValue()+1);
                setScore(tab[i][0].getValue());
                tab[i][1].setImage(0);
                modTab[i][0]=1;
            }
        }
    }
    public void moveIIUp() {
        for (int i = 0; i < 4; i++) {
            if (tab[i][1].getValue() == 0) {
                tab[i][1].setImage(tab[i][2].getValue());
                tab[i][2].setImage(0);
                if(modTab[i][2]!=0){
                    modTab[i][1]=modTab[i][2];
                    modTab[i][2]=0;
                }
            }
            if (tab[i][1].getValue() == tab[i][2].getValue() && tab[i][1].getValue() != 0 && tab[i][2].getValue() != 0 && modTab[i][1] == 0 && modTab[i][2] == 0) {
                tab[i][1].setImage(tab[i][1].getValue() + 1);
                setScore(tab[i][1].getValue());
                tab[i][2].setImage(0);
                modTab[i][1] = 1;
            }
        }
        moveIUp();
    }
    public void moveIIIUp() {
        for (int i = 0; i < 4; i++) {
            if (tab[i][2].getValue() == 0) {
                tab[i][2].setImage(tab[i][3].getValue());
                tab[i][3].setImage(0);
                if (modTab[i][3] != 0) {
                    modTab[i][2] = modTab[i][3];
                    modTab[i][3] = 0;
                }
            }
            if (tab[i][2].getValue() == tab[i][3].getValue() && tab[i][2].getValue() != 0 && tab[i][3].getValue() != 0 && modTab[i][2] == 0 && modTab[i][3] == 0) {
                tab[i][2].setImage(tab[i][2].getValue() + 1);
                setScore(tab[i][2].getValue());
                tab[i][3].setImage(0);
                modTab[i][2] = 1;
            }
        }
        moveIIUp();
    }

    public void moveIRight(){
        for(int i=3;i>=0;i--){
            if(tab[3][i].getValue()==0){
                tab[3][i].setImage(tab[2][i].getValue());
                tab[2][i].setImage(0);
                if(modTab[2][i]!=0){
                    modTab[3][i]=modTab[2][i];
                    modTab[2][i]=0;
                }
            }
            if(tab[3][i].getValue()==tab[2][i].getValue() && tab[3][i].getValue()!=0 && tab[2][i].getValue()!=0 && modTab[3][i]==0 && modTab[2][i] == 0){
                tab[3][i].setImage(tab[3][i].getValue()+1);
                setScore(tab[3][i].getValue());
                tab[2][i].setImage(0);
                modTab[3][i]=1;
            }
        }
    }
    public void moveIIRight() {
        for (int i=3;i>=0;i--) {
            if (tab[2][i].getValue() == 0) {
                tab[2][i].setImage(tab[1][i].getValue());
                tab[1][i].setImage(0);
                if(modTab[1][i]!=0){
                    modTab[2][i]=modTab[1][i];
                    modTab[1][i]=0;
                }
            }
            if (tab[2][i].getValue() == tab[1][i].getValue() && tab[2][i].getValue() != 0 && tab[1][i].getValue() != 0 && modTab[2][i] == 0 && modTab[1][i] == 0) {
                tab[2][i].setImage(tab[2][i].getValue() + 1);
                setScore(tab[2][i].getValue());
                tab[1][i].setImage(0);
                modTab[2][i] = 1;
            }
        }
        moveIRight();
    }
    public void moveIIIRight(){
        for (int i=3;i>=0;i--) {
            if (tab[1][i].getValue() == 0) {
                tab[1][i].setImage(tab[0][i].getValue());
                tab[0][i].setImage(0);
                if(modTab[0][i]!=0){
                    modTab[1][i]=modTab[0][i];
                    modTab[0][i]=0;
                }
            }
            if (tab[1][i].getValue() == tab[0][i].getValue() && tab[1][i].getValue() != 0 && tab[0][i].getValue() != 0 && modTab[1][i] == 0 && modTab[0][i] == 0) {
                tab[1][i].setImage(tab[1][i].getValue() + 1);
                setScore(tab[1][i].getValue());
                tab[0][i].setImage(0);
                modTab[1][i] = 1;
            }
        }
        moveIIRight();
    }

    public void moveIDown(){
        for(int i=3;i>=0;i--){
            if(tab[i][3].getValue()==0){
                tab[i][3].setImage(tab[i][2].getValue());
                tab[i][2].setImage(0);
                if(modTab[i][2]!=0){
                    modTab[i][3]=modTab[i][2];
                    modTab[i][2]=0;
                }
            }
            if(tab[i][3].getValue()==tab[i][2].getValue() && tab[i][3].getValue()!=0 && tab[i][2].getValue()!=0 && modTab[i][3]==0 && modTab[i][2] == 0){
                tab[i][3].setImage(tab[i][3].getValue()+1);
                setScore(tab[i][3].getValue());
                tab[i][2].setImage(0);
                modTab[i][3]=1;
            }
        }
    }
    public void moveIIDown() {
        for (int i=3;i>=0;i--) {
            if (tab[i][2].getValue() == 0) {
                tab[i][2].setImage(tab[i][1].getValue());
                tab[i][1].setImage(0);
                if(modTab[i][1]!=0){
                    modTab[i][2]=modTab[i][1];
                    modTab[i][1]=0;
                }
            }
            if (tab[i][2].getValue() == tab[i][1].getValue() && tab[i][2].getValue() != 0 && tab[i][1].getValue() != 0 && modTab[i][2] == 0 && modTab[i][1] == 0) {
                tab[i][2].setImage(tab[i][2].getValue() + 1);
                setScore(tab[i][2].getValue());
                tab[i][1].setImage(0);
                modTab[i][2] = 1;
            }
        }
        moveIDown();
    }
    public void moveIIIDown(){
        for (int i=3;i>=0;i--) {
            if (tab[i][1].getValue() == 0) {
                tab[i][1].setImage(tab[i][0].getValue());
                tab[i][0].setImage(0);
                if(modTab[i][0]!=0){
                    modTab[i][1]=modTab[i][0];
                    modTab[i][0]=0;
                }
            }
            if (tab[i][1].getValue() == tab[i][0].getValue() && tab[i][1].getValue() != 0 && tab[i][0].getValue() != 0 && modTab[i][1] == 0 && modTab[i][0] == 0) {
                tab[i][1].setImage(tab[i][1].getValue() + 1);
                setScore(tab[i][1].getValue());
                tab[i][0].setImage(0);
                modTab[i][1] = 1;
            }
        }
        moveIIDown();
    }
    public void gameOverCheck(){
        int checkContener = 0;
        boolean cornerCheck = false;
        boolean borderCheck = false;
        boolean centerCheck = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tab[j][i].getValue() == 0)
                    checkContener++;
            }
        }
        if(checkContener==0){
            if(tab[0][0].getValue() == tab[1][0].getValue() || tab[0][0].getValue() == tab[0][1].getValue()){
                cornerCheck = true;
            }
            if(tab[3][3].getValue() == tab[3][2].getValue() || tab[3][3].getValue() == tab[2][3].getValue()){
                cornerCheck = true;
            }
            if(tab[0][3].getValue() == tab[1][3].getValue() || tab[0][3].getValue() == tab[0][2].getValue()){
                cornerCheck = true;
            }
            if(tab[3][0].getValue() == tab[3][1].getValue() || tab[3][0].getValue() == tab[2][0].getValue()){
                cornerCheck = true;
            }
            if(tab[1][0].getValue() == tab[2][0].getValue() || tab[1][0].getValue() == tab[1][1].getValue()){
                borderCheck = true;
            }
            if(tab[2][0].getValue() == tab[2][1].getValue()){
                borderCheck = true;
            }
            if(tab[3][1].getValue() == tab[2][1].getValue() || tab[3][1].getValue() == tab[3][2].getValue()){
                borderCheck = true;
            }
            if(tab[3][2].getValue() == tab[2][2].getValue()){
                borderCheck = true;
            }
            if(tab[2][3].getValue() == tab[2][2].getValue() || tab[2][3].getValue() == tab[1][3].getValue()){
                borderCheck = true;
            }
            if(tab[1][3].getValue() == tab[1][2].getValue()){
                borderCheck = true;
            }
            if(tab[0][2].getValue() == tab[1][2].getValue() || tab[0][2].getValue() == tab[0][1].getValue()){
                borderCheck = true;
            }
            if(tab[0][1].getValue() == tab[1][1].getValue()){
                borderCheck = true;
            }
            if(tab[1][1].getValue() == tab[2][1].getValue() || tab[1][1].getValue() == tab[1][2].getValue()){
                centerCheck = true;
            }
            if(tab[2][2].getValue() == tab[2][1].getValue() || tab[2][2].getValue() == tab[1][2].getValue()){
                centerCheck = true;
            }

            if(cornerCheck==false && borderCheck==false && centerCheck==false){
                Stage GameOverStage = new Stage();
                gameOverFrame(GameOverStage);
            }
        }
    }

    public void gameOverFrame(Stage gameOverStage){
        try {
            saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VBox vboxGO = new VBox();
        vboxGO.setId("menu-class");
        vboxGO.setAlignment(Pos.CENTER);
        vboxGO.setSpacing(30);

        Text gameOverTxt = new Text("Game Over!");
        gameOverTxt.setId("credits1");

        String scoreInfoString = "Your score: "+mainscore;
        Text scoreInfo = new Text(scoreInfoString);
        scoreInfo.setId("credits2");

        Button backBTN = new Button("Back");
        backBTN.setId("backGame");

        vboxGO.getChildren().add(gameOverTxt);
        vboxGO.getChildren().add(scoreInfo);
        vboxGO.getChildren().add(backBTN);

        backBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                gameOverStage.close();
                new Menu(primaryStage).start();
            }
        });

        Scene scene2 = new Scene(vboxGO, 465,250);
        scene2.getStylesheets().add("myStyle.css");


        gameOverStage.getIcons().add(new Image("Game/assets/2048.png"));
        gameOverStage.setResizable(false);
        gameOverStage.centerOnScreen();
        gameOverStage.setTitle("Game Over");
        gameOverStage.setScene(scene2);
        gameOverStage.show();
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
    public void saveToFile() throws IOException {
        FileWriter fileWriter = null;
        int realCurrentScore = Integer.parseInt(mainscore);
        int realBestScore = Integer.parseInt(bestScoreFromFile);

        if(realCurrentScore>realBestScore){
            try{
                fileWriter = new FileWriter("F:/JavaWorkspace/2048Projekt/src/Game/assets/score.txt");
                String zeroString;
                int tmpScore = Integer.parseInt(mainscore);
                String stringToWrite;

                if(tmpScore/10 < 1){
                    zeroString = "0000";
                }
                else if(tmpScore/100 < 1){
                    zeroString = "000";
                }
                else if(tmpScore/1000 < 1){
                    zeroString = "00";
                }
                else if(tmpScore/10000 < 1){
                    zeroString = "0";
                }
                else{
                    zeroString = "";
                }

                stringToWrite = zeroString+mainscore;
                fileWriter.write(stringToWrite);
            } finally {
                if(fileWriter != null){
                    fileWriter.close();
                }
            }
        }
    }
}
