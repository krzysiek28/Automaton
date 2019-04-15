package gui;

import cell.Cell;
import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates2D;
import cell.states.BinaryState;
import cell.states.CellState;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;
import java.util.Set;

public class Board {

    private final static int BOARD_HEIGHT = 600;

    private final static int BOARD_WIDTH = 1000;

    private int xCells;

    private int yCells;

    Pane board;

    private Set<Tile> tileSet = new HashSet<>();

    public Board(int xCells, int yCells){
        this.xCells = xCells;
        this.yCells = yCells;
        initBoard();
    }

    public Pane initBoard(){
        board = new Pane();
        board.setPrefSize(BOARD_WIDTH, BOARD_HEIGHT);
        return board;
    }

    public void changeBoardCellCount(int xCells, int yCells) {
        this.xCells = xCells;
        this.yCells = yCells;
        createBoard(xCells, yCells);
    }

    private void createBoard(int xCells, int yCells){
        double cellWidth = BOARD_WIDTH/xCells;
        double cellHeight = BOARD_HEIGHT/yCells;

        for(int x = 0; x < xCells; x++){
            for(int y = 0; y < yCells; y++){
                Tile tile = new Tile(cellWidth, cellHeight, new Cell(BinaryState.DEAD, new CellCoordinates2D(x, y)));
                tile.setTranslateX(cellWidth * x);
                tile.setTranslateY(cellHeight * y);
                tileSet.add(tile);
                board.getChildren().add(tile);
            }
        }
    }

    private class Tile extends StackPane {
        private Color cellColor = Color.WHITE;

        private Rectangle rectangle;

        private Cell cell;

        public Tile(double cellWidth, double cellHeight, Cell cell){
            Rectangle rectangle = new Rectangle(cellWidth, cellHeight);
            rectangle.setStroke(resolveColor(cell.getState()));
            getChildren().add(rectangle);
            setOnMouseClicked(event -> changeState());
        }

        public void changeState() {
            if(cell.getState() == BinaryState.ALIVE){
                cell.setState(BinaryState.DEAD);
                rectangle.setStroke(resolveColor(BinaryState.DEAD));
            } else {
                cell.setState(BinaryState.ALIVE);
                rectangle.setStroke(resolveColor(BinaryState.ALIVE));
            }
        }

        private Color resolveColor(CellState state){
            return state == BinaryState.ALIVE ? Color.BLACK : Color.WHITE;
        }

        public CellCoordinates getCellCoordinates(){
            return cell.getCoordinates();
        }

        public CellState getCellState(){
            return cell.getState();
        }
    }
}
