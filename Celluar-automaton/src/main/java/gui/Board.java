package gui;

import automaton.Automaton;
import cell.Cell;
import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates1D;
import cell.coordinates.CellCoordinates2D;
import cell.states.BinaryState;
import cell.states.CellState;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class Board {

    private final static int BOARD_HEIGHT = 550;

    private final static int BOARD_WIDTH = 1000;

    private final static int DEFAULT_Y_CELL_COUNT = 50;

    private int xCells;

    private int yCells;

    private Map<CellCoordinates, CellState> cells;

    private Pane board;

    private Set<Tile> tileSet = new HashSet<>();

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

    public Board(Map<CellCoordinates, CellState> cells){
        this.cells = cells;
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
        createBoard(xCells, yCells, cellSet);
        return board;
    }

    public void changeBoardCellCount(int xCells, int yCells) {
        this.xCells = xCells;
        this.yCells = yCells;
        createBoard(xCells, yCells, cellSet);
    }

    private void createBoard(int xCells, int yCells, Set<Cell> cellSet){
        double cellWidth = BOARD_WIDTH/xCells;
        double cellHeight = BOARD_HEIGHT/yCells;

        for(Cell cell : cellSet){
            Tile tile = new Tile(cellWidth, cellHeight, cell);
            tile.setTranslateX(cellWidth * ((CellCoordinates2D)cell.getCoordinates()).getX());
            tile.setTranslateY(cellHeight * ((CellCoordinates2D)cell.getCoordinates()).getY());
            tileSet.add(tile);
            board.getChildren().add(tile);
        }
    }

    public void update(){
        tileSet.forEach(tile -> automaton.changeCellState(tile.getCell()));
        automaton.nextState();
        tileSet = new HashSet<>();
        cellSet = automaton.getCellSet();
        createBoard(xCells, yCells, cellSet);
        tileSet.forEach(Tile::updateCellColor);
    }

    private class Tile extends StackPane {
        private Rectangle rectangle;

        private Cell cell;

        public Tile(double cellWidth, double cellHeight, Cell cell){
            this.cell = cell;
            this.rectangle = new Rectangle(cellWidth, cellHeight);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(resolveColor(cell.getState()));
            getChildren().add(rectangle);
            setOnMouseClicked(event -> changeState());
        }

        public void changeState() {
            if(cell.getState() == BinaryState.ALIVE){
                cell.setState(BinaryState.DEAD);
                rectangle.setFill(resolveColor(BinaryState.DEAD));
            } else {
                cell.setState(BinaryState.ALIVE);
                rectangle.setFill(resolveColor(BinaryState.ALIVE));
            }
        }

        public void updateCellColor(){
            rectangle.setFill(resolveColor(cell.getState()));
        }

        public Cell getCell(){
            return this.cell;
        }

        private Color resolveColor(CellState state){
            return state == BinaryState.ALIVE ? Color.BLACK : Color.WHITE;
        }

        public CellCoordinates getCellCoordinates(){
            return cell.getCoordinates();
        }
    }
}
