package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Tile {
    private Pane pane;
    private Image paneImg;
    private int value;
    private int x;
    private int y;

    Tile(int x,int y){
        this.x=x;
        this.y=y;
        this.pane = new Pane();
        this.pane.setPrefSize(90,90);
        this.paneImg = new Image("Game/assets/czyste.png");
        this.value=0;
        this.pane.getChildren().add(new ImageView(this.paneImg));
    }
    public void setImage(int x){
        this.paneImg = getNumber(x);
        this.pane.getChildren().add(new ImageView(this.paneImg));
        value = x;
    }

    public Image getNumber(int x){
        switch (x){
            case 0: return new Image("Game/assets/czyste.png");
            case 1: return new Image("Game/assets/2.png");
            case 2: return new Image("Game/assets/4.png");
            case 3: return new Image("Game/assets/8.png");
            case 4: return new Image("Game/assets/16.png");
            case 5: return new Image("Game/assets/32.png");
            case 6: return new Image("Game/assets/64.png");
            case 7: return new Image("Game/assets/128.png");
            case 8: return new Image("Game/assets/256.png");
            case 9: return new Image("Game/assets/512.png");
            case 10: return new Image("Game/assets/1024.png");
            case 11: return new Image("Game/assets/2048.png");
        }
        return new Image("Game/assets/czyste.png");
    }
    public int getValue(){
        return this.value;
    }
    public Pane getPane(){
        return this.pane;
    }
}