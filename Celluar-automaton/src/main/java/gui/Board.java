package gui;

import automaton.Automaton1Dim;
import automaton.Automaton2Dim;
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

    public Board(Map<CellCoordinates, CellState> cells){
        this.cells = cells;
        this.xCells = resolveXCellCount(cells.keySet());
        this.yCells = resolveYCellCount(cells.keySet());
        initBoard();
    }

    private int resolveXCellCount(Set<CellCoordinates> cellCoordinates){
        if(is2DimCoordinate(cellCoordinates)){
            return cellCoordinates.stream()
                    .map(CellCoordinates2D.class::cast)
                    .map(CellCoordinates2D::getX)
                    .mapToInt(v -> v)
                    .max().orElseThrow(NoSuchElementException::new);
        } else if(is1DimCoordinate(cellCoordinates)){
            return cellCoordinates.stream()
                    .map(CellCoordinates1D.class::cast)
                    .map(CellCoordinates1D::getX)
                    .mapToInt(v -> v)
                    .max().orElseThrow(NoSuchElementException::new);
        }
        return -1;
    }

    private int resolveYCellCount(Set<CellCoordinates> cellCoordinates){
        if(is2DimCoordinate(cellCoordinates)){
            return cellCoordinates.stream()
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
        createBoard(xCells, yCells);
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
