package hangman;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author cecew
 */
public class MiniProject extends Application {
    
    Label text;
    ToggleGroup group;
    RadioButton sequential, parallel;
    Button submit;
    HBox hbox;
    VBox vbox;
  
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
        
    }
       
    private void handleButtonAction(ActionEvent e){
        if(sequential.isSelected() == true) {
             String[] args = new String[0];
             FileHandling.main(args);
             Stage stage = (Stage) submit.getScene().getWindow();
             stage.close();
             Game game = new Game();
             game.start();
        }
        if(parallel.isSelected() == true){
             String[] args = new String[0];
             ParallelSelection.main(args);
             Stage stage = (Stage) submit.getScene().getWindow();
             stage.close();
             Game game = new Game();
             game.start();
        }
    }
    
    public static void main(String [] args) {
            launch();
        }
}
