import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;

/**
 * @author Amogha Thodpunuri
 * @version 1.0
 * A program that creates a Wordle of Java terms.
 */
public class Jordle extends Application {
    private int p, q = 0;
    private Rectangle[][] newgrid = new Rectangle[6][5];
    private Label[][] keyArray = new Label[6][5];



    /**
     * A main method that launches the program.
     * @param args a String object
     */
    public static void main(String[] args) {
        launch(args);
    }
    public int range = Words.list.size();
    public int number = (int) (Math.random() * range);
    public String answer = Words.list.get(number);

    /**
     * Checks if the enter key is pressed.
     * @param ke a KeyEvent event
     * @return a boolean value
     */
    public boolean enterKey(KeyEvent ke) {
        return (ke.getCode() == KeyCode.ENTER);

    }

    /**
     * Checks if the delete key is pressed.
     * @param ke a KeyEvent event
     * @return a boolean value
     */
    public boolean deleteKey(KeyEvent ke) {
        return (ke.getCode() == KeyCode.BACK_SPACE);

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jordle");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(5);
        grid.setHgap(5);
        final Text title = new Text("Jordle");
        BorderPane bp = new BorderPane();
        bp.setCenter(title);
        title.setFont(Font.font("Verdana", 40));
        title.setFill(Color.BLACK);
        Label label = new Label("Try Guessing a Word!");
        HBox buttons = new HBox();
        buttons.setSpacing(5);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Rectangle r = new Rectangle();
                newgrid[i][j] = r;
                r.setFill(Color.WHITE);
                r.setStroke(Color.BLACK);
                r.setWidth(40);
                r.setHeight(40);
                Label l = new Label();
                l.setText(" ");
                GridPane.setRowIndex(r, i);
                GridPane.setColumnIndex(r, j);
                grid.getChildren().addAll(r);
                Label label2 = new Label();
                label2.setText(" ");
                grid.add(label2, j, i);
                keyArray[i][j] = label2;
            }
        }



        Button btn = new Button();
        btn.setText("Instructions");
        Stage secondStage = new Stage();
        secondStage.setTitle("Instructions");
        Pane pane2 = new Pane();
        Scene secondScene = new Scene(pane2, 320, 320);
        Text instructionsText = new Text(8, 12, "Welcome to Jordle! Try guessing some words! Guess 5 \n"
                + "letter words until all the boxes are green. A gray \ncolored box means the letter is not "
                + "in the word. A yellow \ncolored box means the letter is in the wrong spot. A green \ncolored "
                + "box means the letter is in the correct spot.\nYou will have 6 attempts.\nHave fun!");
        pane2.getChildren().add(instructionsText);
        secondStage.setScene(secondScene);
        Pane pane = new Pane();
        btn.setOnAction((event) -> secondStage.show());
        pane.getChildren().add(btn);
        Button reset = new Button();
        reset.setText("Restart");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                p = 0;
                q = 0;
                range = Words.list.size();
                number = (int) (Math.random() * range);
                answer = Words.list.get(number);
                System.out.println(answer);
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        keyArray[i][j].setText(" ");
                        newgrid[i][j].setFill(Color.WHITE);
                        newgrid[i][j].setStroke(Color.BLACK);
                        label.setText("Try Guessing a Word!");

                    }
                }
            }
        });

        buttons.setPadding(new Insets(50, 50, 50, 50));
        buttons.setSpacing(5);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        buttons.getChildren().addAll(label, reset, btn);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(bp, grid, buttons);
        Scene scene = new Scene(vbox, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            String[] arr = new String[5];
            public void handle(KeyEvent event) {
                try {
                    Label l2 = keyArray[p][q];
                    if ((event.getCode().isLetterKey())) {
                        String letter = event.getCode().getName();
                        l2.setText("     " + letter);
                        arr[q] = letter;
                        q++;

                    }

                } catch (Exception e) {
                    System.out.println("You can't type more than 5 words");
                }
                String word = "";
                for (int k = 0; k < 5; k++) {
                    word += arr[k];
                }
                if (enterKey(event) && q < 5 && word.length() != 5) {
                    Alert alert = new Alert(AlertType.ERROR);
                    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please enter a 5-letter word.");
                    alert.showAndWait();
                }
                if (enterKey(event) && word.length() == 5) {
                    p++;
                    q = 0;
                    System.out.println(word);
                }

                if (deleteKey(event)) {
                    if (q > 0) {
                        q--;
                    }
                    keyArray[p][q].setText(" ");
                }
                word = word.toLowerCase();
                int counter = 0;
                for (int ch = 0; ch < 5; ch++) {
                    String checkLetter = String.valueOf(word.charAt(ch));
                    if ((word.charAt(ch) == answer.charAt(ch)) && enterKey(event) && word.length() == 5) {
                        newgrid[p - 1][ch].setFill(Color.GREEN);
                        counter++;
                    } else if (answer.contains(checkLetter) && enterKey(event) && word.length() == 5) {
                        newgrid[p - 1][ch].setFill(Color.YELLOW);
                    } else {
                        if (enterKey(event)) {
                            newgrid[p - 1][ch].setFill(Color.GRAY);
                        }
                    }
                }
                if (counter == 5) {
                    label.setText("Congratulations! You’ve guessed the word!");

                } else if (counter < 5 && p == 6) {
                    label.setText("Game over. The word was " + answer);
                }
            }
        });
    }

}


