package gui;

import automaton.Automaton;
import cell.states.BinaryState;
import cell.states.UniformStateFactory;
import game.GameOfLife;
import game.GameOfLifeHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import neighbor.MooreNeighborhood;

public class GraphicUserInterface extends Application {

    private Automaton automaton;

    private static int xCellCount = 10;

    private static int yCellCount = 10;

    public GraphicUserInterface(){}

    @Override
    public void start(Stage stage) throws Exception {
        Board board = new Board(xCellCount, yCellCount);
        stage.setScene(new Scene(board.initBoard()));
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
