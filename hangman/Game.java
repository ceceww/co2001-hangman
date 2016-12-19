package hangman;

import static hangman.RandomWord.randomWord;
import javafx.collections.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author cecew
 */
public class Game {
    Label text;
    ToggleGroup group;
    RadioButton easy, medium, hard, hell;
    Button play;
    HBox hbox, hbox2;
    VBox vbox;
    Stage stage2;
    
    Scene scene3;
    String lettersEntered;
    char[] alphabet;
    SimpleStringProperty randomWordProperty;
    String randomWord;
    char guess;
    Label updatedRandomWordLabel;
    Label wordArea;
    VBox vbox2;
    TilePane letterRack;
    ImageView imgpic;
    Image hangman;
    Button newGame;
    Label answerLabel;
    
    public Game(){}
    
    
     public void start(){  
        stage2 = new Stage();
        stage2.centerOnScreen();
        stage2.setTitle("Hangman");
        stage2.setResizable(false);
        stage2.show();

        text = new Label("Select difficulty:");

        group = new ToggleGroup();

        easy = new RadioButton("Easy");
        medium = new RadioButton("Medium");
        hard = new RadioButton("Hard");
        hell = new RadioButton("Hell");
        easy.setToggleGroup(group);
        medium.setToggleGroup(group);
        hard.setToggleGroup(group);
        hell.setToggleGroup(group);
        easy.setSelected(true);

        hbox = new HBox(easy, medium, hard, hell);
        hbox.setSpacing(20.0);
        hbox.setAlignment(Pos.CENTER);

        play = new Button("Play");

        vbox = new VBox();
        vbox.setSpacing(10.0);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(text, hbox, play);
        
        play.setOnAction(e -> handleButtonAction2(e));
        
        Scene scene2 = new Scene(vbox, 350, 100);
        
        stage2.setScene(scene2);
        
        //Game scene
       
        randomWordProperty = new SimpleStringProperty(); 
        updatedRandomWordLabel = new Label();
        wordArea = new Label();
        answerLabel = new Label();
        answerLabel.setVisible(false);
        
        randomWordProperty.addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue){
               
                System.out.println(randomWordProperty.getValue());
                updatedRandomWordLabel.setText((String) newValue);
                randomWord = randomWordProperty.getValue();
                lettersEntered = randomWord;
                lettersEntered = lettersEntered.replaceAll(".", "_");
              
                wordArea.setText(lettersEntered);  
                wordArea.setFont(new Font("Cambria", 32));
                
                answerLabel.setText("Answer: " + randomWord.toUpperCase());
            }
            
        });
        
        hangman = new Image("images/hangman0.png");
        
        imgpic = new ImageView();
        imgpic.setImage(hangman);
        imgpic.setFitWidth(400);
        imgpic.setPreserveRatio(true);
         
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        
        letterRack = new TilePane();
        letterRack.setHgap(8);
        letterRack.setVgap(8);
        letterRack.setPrefColumns(13);
        letterRack.setPrefRows(2);
        letterRack.setMaxWidth(500);
        letterRack.setPadding(new Insets(20));
        
        for (int i = 0; i < 26; i++) {
            letterRack.getChildren().add(new Button(String.valueOf(alphabet[i])));
            letterRack.getChildren().get(i).setAccessibleText(String.valueOf(alphabet[i]));
            String t = letterRack.getChildren().get(i).getAccessibleText();
            int child = i;
            letterRack.getChildren().get(i).setOnMouseClicked(e -> handleButtonAction3(e, t, child));
           
        }
        
        newGame = new Button("New game");
        newGame.setVisible(false);
        newGame.setOnMouseClicked(e -> handleButtonAction4(e));
        
        hbox2 = new HBox();
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setPadding(new Insets(0, 0, 0, 150));
        hbox2.setSpacing(25);
        hbox2.getChildren().addAll(wordArea, answerLabel);
        
        vbox2 = new VBox();
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(newGame, imgpic, hbox2, letterRack);
        scene3 = new Scene(vbox2, 500, 400);

        
    }
    
    private void handleButtonAction2(ActionEvent e) {
        if(easy.isSelected()) {
            randomWordProperty.set(randomWord(5, "Lists.ser"));
            stage2.setScene(scene3);
        }
        if(medium.isSelected()){
            randomWordProperty.set(randomWord(6, "Lists.ser"));
            stage2.setScene(scene3);
        }
         if(hard.isSelected()) {
             randomWordProperty.set(randomWord(7, "Lists.ser"));
             stage2.setScene(scene3);
        }
        if(hell.isSelected()){
           randomWordProperty.set(randomWord(8, "Lists.ser"));
            stage2.setScene(scene3);
        }
    }
    
    int wrong=0;
    
    ListView<String> imgList = new ListView<String>();
    ObservableList<String> items = FXCollections.observableArrayList(
            "images/hangman0.png", "images/hangman1.png", "images/hangman2.png", "images/hangman3.png", 
            "images/hangman4.png", "images/hangman5.png", "images/lose.png");

    private void handleButtonAction3(MouseEvent e, String t, int x) {
        letterRack.getChildren().get(x).setVisible(false);
        imgList.setItems(items);
        char[] c = t.toCharArray();
        guess = c[0];
        randomWord = randomWord.toUpperCase();
        StringBuilder lettersEnteredBuilder = new StringBuilder(lettersEntered);
      
        for (int i=0; i<randomWord.length(); i++){
            if(guess==randomWord.charAt(i)){
               lettersEnteredBuilder.setCharAt(i, guess);
               lettersEntered = lettersEnteredBuilder.toString();
               lettersEnteredBuilder = new StringBuilder(lettersEntered);
            }
              wordArea.setText(lettersEnteredBuilder.toString());
       
        }
        if(!randomWord.contains(t)){
                wrong = wrong + 1;
                imgpic.setImage(new Image(imgList.getItems().get(wrong)));
        }
        if(!lettersEnteredBuilder.toString().contains("_")){
            imgpic.setImage(new Image("images/win.png"));
            newGame.setVisible(true);
            for(int i=0; i<26; i++){
                letterRack.getChildren().get(i).setDisable(true);
            }
        }
        if(wrong == 6){
            answerLabel.setVisible(true);
            newGame.setVisible(true);
            for(int i=0; i<26; i++){
               letterRack.getChildren().get(i).setDisable(true);
            }
        }
        
    }
    
     private void handleButtonAction4(MouseEvent e){
         stage2.close();
         Game game = new Game();
         game.start();
    }
}
