package automaton;

import cell.states.CellStateFactory;
import neighbor.CellNeighborhood;

public abstract class Automaton1Dim extends Automaton {

    protected int size;

    public Automaton1Dim(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood, int size) {
        super(cellStateFactory, cellNeighborhood);
        this.size = size;
        initialize();
    }

}
