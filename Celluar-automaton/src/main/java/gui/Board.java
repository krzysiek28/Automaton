package gui;

import automaton.Automaton;
import cell.Cell;
import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates1D;
import cell.coordinates.CellCoordinates2D;
import cell.states.BinaryState;
import cell.states.CellState;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class Board {

    private final static int BOARD_HEIGHT = 550;

    private final static int BOARD_WIDTH = 1000;

    private final static int DEFAULT_Y_CELL_COUNT = 50;

    private int xCells;

    private int yCells;

    private Map<CellCoordinates, CellState> cells;

    private Pane board;

    private Map<CellCoordinates2D, Tile> tileMap = new HashMap<>();

    private Set<Cell> cellSet;

    private Automaton automaton;

    public Board(Automaton automaton){
        this.automaton = automaton;
        this.cellSet = automaton.getCellSet();
        this.cells = automaton.getCells();
        this.xCells = resolveXCellCount(cells.keySet());
        this.yCells = resolveYCellCount(cells.keySet());
        initBoard();
    }

    private int resolveXCellCount(Set<CellCoordinates> cellCoordinates){
        if(is2DimCoordinate(cellCoordinates)){
            return 1 + cellCoordinates.stream()
                    .map(CellCoordinates2D.class::cast)
                    .map(CellCoordinates2D::getX)
                    .mapToInt(v -> v)
                    .max().orElseThrow(NoSuchElementException::new);
        } else if(is1DimCoordinate(cellCoordinates)){
            return 1+ cellCoordinates.stream()
                    .map(CellCoordinates1D.class::cast)
                    .map(CellCoordinates1D::getX)
                    .mapToInt(v -> v)
                    .max().orElseThrow(NoSuchElementException::new);
        }
        return -1;
    }

    private int resolveYCellCount(Set<CellCoordinates> cellCoordinates){
        if(is2DimCoordinate(cellCoordinates)){
            return 1 + cellCoordinates.stream()
                    .map(CellCoordinates2D.class::cast)
                    .map(CellCoordinates2D::getY)
                    .mapToInt(v -> v)
                    .max().orElseThrow(NoSuchElementException::new);
        } else if(is1DimCoordinate(cellCoordinates)){
            return DEFAULT_Y_CELL_COUNT;
        }
        return -1;
    }

    private boolean is2DimCoordinate(Set<CellCoordinates> cellCoordinates){
        return cellCoordinates.stream()
                .anyMatch(CellCoordinates2D.class::isInstance);
    }

    private boolean is1DimCoordinate(Set<CellCoordinates> cellCoordinates){
        return cellCoordinates.stream()
                .anyMatch(CellCoordinates1D.class::isInstance);
    }

    public Pane initBoard(){
        board = new Pane();
        board.setPrefSize(BOARD_WIDTH, BOARD_HEIGHT);
        GridPane.setMargin(board, new Insets(15,15,10,0));
        createBoard(xCells, yCells, cellSet);
        return board;
    }

    private void createBoard(int xCells, int yCells, Set<Cell> cellSet){
        double cellWidth = BOARD_WIDTH/xCells;
        double cellHeight = BOARD_HEIGHT/yCells;

        for(Cell cell : cellSet){
            Tile tile = new Tile(cellWidth, cellHeight, cell.getState());
            tile.setTranslateX(cellWidth * ((CellCoordinates2D)cell.getCoordinates()).getX());
            tile.setTranslateY(cellHeight * ((CellCoordinates2D)cell.getCoordinates()).getY());
            tileMap.put((CellCoordinates2D) cell.getCoordinates(), tile);
            board.getChildren().add(tile);
        }
    }

    public void update(){
        tileMap.entrySet().stream()
                .forEach(entry -> automaton.changeCellState(entry.getKey(), entry.getValue().getCellState()));
        automaton.nextState();
        cellSet = automaton.getCellSet();
        cellSet.forEach(cell -> tileMap.get(cell.getCoordinates()).updateCellColor(cell.getState()));
    }

    private class Tile extends StackPane {
        private Rectangle rectangle;

        private CellState cellState;

        public Tile(double cellWidth, double cellHeight, CellState cellState){
            this.cellState = cellState;
            this.rectangle = new Rectangle(cellWidth, cellHeight);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(resolveColor(cellState));
            getChildren().add(rectangle);
            setOnMouseClicked(event -> changeState());
        }

        public void changeState() {
            if(cellState == BinaryState.ALIVE){
                cellState = BinaryState.DEAD;
                rectangle.setFill(resolveColor(BinaryState.DEAD));
            } else {
                cellState = BinaryState.ALIVE;
                rectangle.setFill(resolveColor(BinaryState.ALIVE));
            }
        }

        public void updateCellColor(CellState cellState){
            this.cellState = cellState;
            rectangle.setFill(resolveColor(cellState));
        }

        public CellState getCellState(){
            return this.cellState;
        }

        private Color resolveColor(CellState state){
            return state == BinaryState.ALIVE ? Color.BLACK : Color.WHITE;
        }
    }
}
