package gui;

import automaton.Automaton;
import cell.coordinates.CellCoordinates;
import cell.states.BinaryState;
import cell.states.CellState;
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

import java.util.Map;

public class GraphicUserInterface extends Application {

    private static int MAIN_APP_HEIGHT = 700;

    private static int MAIN_APP_WIDTH = 1200;

//    private Automaton automaton;

    private static int xCellCount = 30;

    private static int yCellCount = 30;

//    private Button nextStateButton = new Button();

//    private GridPane mainWindow = new GridPane();

    public GraphicUserInterface(){
    }

//    private void setUpLayout(){
//        mainWindow.setPrefSize(MAIN_APP_WIDTH, MAIN_APP_HEIGHT);
//        nextStateButton.setText("Nowy stan");
//        mainWindow.add(nextStateButton, 1, 1);
//    }

//    private void addListeners(){
//        nextStateButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
//    }

    @Override
    public void start(Stage stage) throws Exception {
        GridPane mainWindow = new GridPane();
        mainWindow.setPrefSize(MAIN_APP_WIDTH, MAIN_APP_HEIGHT);
        Automaton automaton = createNewGameOfLife();
        Map<CellCoordinates, CellState> cells = automaton.getCells();

        Board board = new Board(cells);
        mainWindow.add(board.initBoard(), 0,1);

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
