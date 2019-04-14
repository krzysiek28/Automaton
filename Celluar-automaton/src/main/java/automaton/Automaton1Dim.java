package automaton;

import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates1D;
import cell.states.CellStateFactory;
import neighbor.CellNeighborhood;

public abstract class Automaton1Dim extends Automaton {

    protected int size;

    public Automaton1Dim(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood, int size) {
        super(cellStateFactory, cellNeighborhood);
        this.size = size;
        initializeCoordinates();
    }

    @Override
    protected boolean hasNextCoordinates(CellCoordinates cellCoordinates) {
        CellCoordinates1D coordinates = (CellCoordinates1D) cellCoordinates;
        return !(coordinates.getX() >= size-1);
    }

    @Override
    protected CellCoordinates initialCoordinates() {
        return new CellCoordinates1D(-1);
    }

    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates cellCoordinates) {
        return new CellCoordinates1D(nextValue(cellCoordinates));
    }

    private int nextValue(CellCoordinates cellCoordinates){
        return ((CellCoordinates1D)cellCoordinates).getX() + 1;
    }
}
