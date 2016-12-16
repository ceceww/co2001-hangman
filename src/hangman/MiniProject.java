package hangman;

import static hangman.RandomWord.randomWord;
import javafx.collections.*;
import javafx.application.Application;
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
public class MiniProject extends Application {
    
    Label text, text2;
    ToggleGroup group, group2;
    RadioButton sequential, parallel, easy, medium, hard, hell;
    Button submit, play;
    HBox hbox, hbox2;
    VBox vbox, vbox2;
    Stage stage2;
    
    //Game variables
    Scene scene3;
    String lettersEntered;
    char[] alphabet;
    SimpleStringProperty randomWordProperty;
    String randomWord;
    SimpleStringProperty wordGuessProperty;
    String wordGuess;
    char guess;
    Label updatedRandomWordLabel; 
    Label updatedWordGuessLabel;
    Label wordArea;
    VBox vbox3;
    TilePane letterRack;
    ImageView imgpic;
    Image hangman;
  
    
    @Override
    public void start(Stage stage){

        stage.setTitle("Hangman Word Chooser");
        stage.setResizable(false);
  
        text = new Label("Please choose which mode you wish to choose words from.");

        group = new ToggleGroup();

        sequential = new RadioButton("Sequential");
        parallel = new RadioButton("Parallel");
        sequential.setToggleGroup(group);
        parallel.setToggleGroup(group);
        sequential.setSelected(true);

        hbox = new HBox(sequential, parallel);
        hbox.setSpacing(20.0);
        hbox.setAlignment(Pos.CENTER);

        submit = new Button("Submit");

        vbox = new VBox();
        vbox.setSpacing(10.0);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(text, hbox, submit);
        
        Scene scene = new Scene(vbox, 350, 100);

        stage.setScene(scene);
        
        stage.show();
        
        submit.setOnAction(e -> handleButtonAction(e));
       
        //Difficulty window
        
        stage2 = new Stage();
        stage2.setTitle("Hangman");
        stage2.setResizable(false);

        text2 = new Label("Select difficulty:");

        group2 = new ToggleGroup();

        easy = new RadioButton("Easy");
        medium = new RadioButton("Medium");
        hard = new RadioButton("Hard");
        hell = new RadioButton("Hell");
        easy.setToggleGroup(group2);
        medium.setToggleGroup(group2);
        hard.setToggleGroup(group2);
        hell.setToggleGroup(group2);
        easy.setSelected(true);

        hbox2 = new HBox(easy, medium, hard, hell);
        hbox2.setSpacing(20.0);
        hbox2.setAlignment(Pos.CENTER);

        play = new Button("Play");

        vbox2 = new VBox();
        vbox2.setSpacing(10.0);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(text2, hbox2, play);
        
        play.setOnAction(e -> handleButtonAction2(e));
        
        Scene scene2 = new Scene(vbox2, 350, 100);
        
        stage2.setScene(scene2);
        
        //Game scene
       
        randomWordProperty = new SimpleStringProperty(); 
        updatedRandomWordLabel = new Label();
        wordArea = new Label();
        
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
            }
            
        });
        
        hangman = new Image("file:src/images/hangman0.png");
        
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
        
        vbox3 = new VBox();
        vbox3.setAlignment(Pos.CENTER);
        vbox3.getChildren().addAll(imgpic, wordArea, letterRack);
        scene3 = new Scene(vbox3, 500, 400);

        
    }
   
    private void handleButtonAction(ActionEvent e){
        if(sequential.isSelected() == true) {
             String[] args = new String[0];
             FileHandling.main(args);
             Stage stage = (Stage) submit.getScene().getWindow();
             stage.close();
             stage2.show();
        }
        if(parallel.isSelected() == true){
             String[] args = new String[0];
             ParallelSelection.main(args);
             Stage stage = (Stage) submit.getScene().getWindow();
             stage.close();
             stage2.show();
        }
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
            "file:src/images/hangman0.png", "file:src/images/hangman1.png", "file:src/images/hangman2.png", "file:src/images/hangman3.png", 
            "file:src/images/hangman4.png", "file:src/images/hangman5.png", "file:src/images/lose.png");

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
            imgpic.setImage(new Image("file:src/images/win.png"));
        }
      }
    
    public static void main(String [] args) {
            launch();
        }
}
