package gui;

import automaton.Automaton;
import cell.states.BinaryState;
import cell.states.UniformStateFactory;
import game.GameOfLife;
import game.GameOfLifeHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import neighbor.MooreNeighborhood;

public class GraphicUserInterface extends Application {

    private static int MAIN_APP_HEIGHT = 700;

    private static int MAIN_APP_WIDTH = 1200;

    private static int NEXT_STATE_BUTTON_HEIGHT = 50;

    private static int NEXT_STATE_BUTTON_WIDTH = 200;

    private static int xCellCount = 10;

    private static int yCellCount = 10;

    private GridPane mainWindow = new GridPane();

    private Board board;

    private Button nextStateButton = new Button();

    public GraphicUserInterface(){
    }

    private void setUpLayout(Board board){
        mainWindow.setPrefSize(MAIN_APP_WIDTH, MAIN_APP_HEIGHT);
        setUpNextStateButton();

        mainWindow.add(board.initBoard(), 1,0);
    }

    private void setUpNextStateButton(){
        nextStateButton.setText("Nowy stan");
        nextStateButton.setPrefSize(NEXT_STATE_BUTTON_WIDTH, NEXT_STATE_BUTTON_HEIGHT);
        addNextStateButtonListener();
        mainWindow.add(nextStateButton, 1, 1);
    }

    private void addNextStateButtonListener(){
        nextStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.update();
            }
        });
    }

    @Override
    public void start(Stage stage) {
        Automaton automaton = createNewGameOfLife();
        board = new Board(automaton);

        setUpLayout(board);

        stage.setTitle("Automaty komórkowe");
        stage.setScene(new Scene(mainWindow));
        stage.show();
    }

    public Automaton createNewGameOfLife(){
        return new GameOfLife(
            new UniformStateFactory(BinaryState.DEAD),
            new MooreNeighborhood(1, false, xCellCount, yCellCount),
            xCellCount, yCellCount,
            GameOfLifeHelper.convertStringToCellRulesList("3,2"),
            GameOfLifeHelper.convertStringToCellRulesList("2"));
    }
}
