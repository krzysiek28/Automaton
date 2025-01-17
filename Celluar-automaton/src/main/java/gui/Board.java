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

    private final static double BOARD_HEIGHT = 550;

    private final static double BOARD_WIDTH = 1000;

    private final static int DEFAULT_Y_CELL_COUNT = 50;

    private int xCells;

    private int yCells;

    private Pane board;

    private Map<CellCoordinates, Tile> tileMap = new HashMap<>();

    private Set<Cell> cellSet;

    private Automaton automaton;

    private boolean addStructureState = false;

    private Structures structureKind;

    public Board(Automaton automaton){
        this.automaton = automaton;
        this.cellSet = automaton.getCellSet();
        Map<CellCoordinates, CellState> cells = automaton.getCells();
        this.xCells = resolveXCellCount(cells.keySet());
        this.yCells = resolveYCellCount(cells.keySet());
        initBoard();
    }

    public Pane getBoardPane(){
        return this.board;
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

        boolean is2DCoordinated = cellSet.stream().anyMatch(cell -> cell.getCoordinates().getClass().isAssignableFrom(CellCoordinates2D.class));

        if(is2DCoordinated){
            createBoardFor2DCoordinates(cellWidth, cellHeight, cellSet);
        } else {
            createBoardFor1DCoordinates(cellWidth, cellHeight, cellSet);
        }
    }

    private void createBoardFor1DCoordinates(double cellWidth, double cellHeight, Set<Cell> cellSet){
        for(Cell cell : cellSet){
            Tile tile = new Tile(cellWidth, cellHeight, cell.getState());
            tile.setTranslateX(cellWidth * ((CellCoordinates1D)cell.getCoordinates()).getX());
            tile.setTranslateY(10);
            tileMap.put(cell.getCoordinates(), tile);
            board.getChildren().add(tile);
        }
    }

    private void createBoardFor2DCoordinates(double cellWidth, double cellHeight, Set<Cell> cellSet){
        for(Cell cell : cellSet){
            Tile tile = new Tile(cellWidth, cellHeight, cell.getState());
            tile.setTranslateX(cellWidth * ((CellCoordinates2D)cell.getCoordinates()).getX());
            tile.setTranslateY(cellHeight * ((CellCoordinates2D)cell.getCoordinates()).getY());
            tileMap.put(cell.getCoordinates(), tile);
            board.getChildren().add(tile);
        }
    }

    public void update(){
//        if(tileMap.keySet().stream().anyMatch(CellCoordinates1D.class::isInstance)){
//            handleAutomaton1D();
//        }
        tileMap.entrySet().stream()
                .forEach(entry -> automaton.changeCellState(entry.getKey(), entry.getValue().getCellState()));
        automaton.nextState();
        cellSet = automaton.getCellSet();
        cellSet.forEach(cell -> tileMap.get(cell.getCoordinates()).updateCellColor(cell.getState()));
    }

    private void handleAutomaton1D(){

    }

    public void clear(){
        tileMap.entrySet().stream()
                .forEach(entry -> automaton.changeCellState(entry.getKey(), BinaryState.DEAD));
        cellSet.forEach(cell -> tileMap.get(cell.getCoordinates()).updateCellColor(BinaryState.DEAD));
    }

    public void addStructure(Structures structure){
        addStructureState = true;
        this.structureKind = structure;
    }

    private void addStructureCells(Tile tile){
        CellCoordinates2D coords  = (CellCoordinates2D)tileMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(tile))
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
        Map<CellCoordinates2D, CellState> structureMap;
        switch (this.structureKind){
            case BLOCK:
                structureMap = StructureHelper.putBlock(coords, xCells, yCells);
                break;
            case KITE:
                structureMap = StructureHelper.putKite(coords, xCells, yCells);
                break;
            case BOAT:
                structureMap = StructureHelper.putBoat(coords, xCells, yCells);
                break;
            case GUN:
                structureMap = StructureHelper.putGun(coords, xCells, yCells);
                break;
            default:
                structureMap = new HashMap<>();
        }

        for (Map.Entry<CellCoordinates2D, CellState> entry : structureMap.entrySet()){
            tileMap.get(entry.getKey()).updateCellColor(entry.getValue());
        }
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
            if(addStructureState){
                addStructureCells(this);
                addStructureState = false;
            } else if(cellState == BinaryState.ALIVE){
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
