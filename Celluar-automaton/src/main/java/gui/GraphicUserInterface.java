package gui;

import automaton.Automaton;
import cell.states.BinaryState;
import cell.states.UniformStateFactory;
import game.GameOfLife;
import game.GameOfLifeHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import neighbor.MooreNeighborhood;

public class GraphicUserInterface extends Application {

    private static int MAIN_APP_HEIGHT = 700;

    private static int MAIN_APP_WIDTH = 1200;

    private Automaton automaton;

    private static int xCellCount = 30;

    private static int yCellCount = 30;

    private GridPane mainWindow = new GridPane();

    public GraphicUserInterface(){
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        mainWindow.setPrefSize(MAIN_APP_WIDTH, MAIN_APP_HEIGHT);
        automaton = createNewGameOfLife();

        Board board = new Board(automaton.getCells());
        mainWindow.add(board.initBoard(), 0,1);

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
