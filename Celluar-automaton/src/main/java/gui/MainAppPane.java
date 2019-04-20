package gui;

import automaton.Automaton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MainAppPane {

    private static int MAIN_APP_HEIGHT = 700;

    private static int MAIN_APP_WIDTH = 1200;

    private Button nextStateButton = new Button();

    private GridPane mainWindow = new GridPane();

    private Board board;

    public MainAppPane(Board board){
        this.board = board;
        setUpLayout();
        addListeners();
    }

    private void setUpLayout(){
        mainWindow.setPrefSize(MAIN_APP_WIDTH, MAIN_APP_HEIGHT);
        nextStateButton.setText("Nowy stan");
        mainWindow.add(nextStateButton, 1, 1);
        mainWindow.add(board.initBoard(), 0, 1);
    }

    private void addListeners(){
        nextStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
    }

    public GridPane getMainWindow(){
        return mainWindow;
    }
}
