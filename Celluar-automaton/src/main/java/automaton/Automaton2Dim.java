package automaton;

import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates2D;
import cell.states.CellStateFactory;
import neighbor.CellNeighborhood;

public abstract class Automaton2Dim extends Automaton {

    protected int width;

    protected int height;

    public Automaton2Dim(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood, int width, int height) {
        super(cellStateFactory, cellNeighborhood);
        this.width = width;
        this.height = height;
        initializeCoordinates();
    }

    @Override
    protected boolean hasNextCoordinates(CellCoordinates cellCoordinates){
        CellCoordinates2D coordinates = (CellCoordinates2D) cellCoordinates;
        return !(coordinates.getX() > width && coordinates.getY() > height);
    }

    @Override
    protected CellCoordinates initialCoordinates() {
        return new CellCoordinates2D(-1,0);
    }

    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates cellCoordinates) {
        CellCoordinates2D coordinates = (CellCoordinates2D) cellCoordinates;
        return isLastElementInRow(coordinates) ?  new CellCoordinates2D(0, coordinates.getY()+1)
                : new CellCoordinates2D(coordinates.getX()+1, coordinates.getY());
    }

    private boolean isLastElementInRow(CellCoordinates2D cellCoordinates2D){
        return cellCoordinates2D.getX() == width -1;
    }
}
