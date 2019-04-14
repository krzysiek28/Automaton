package automaton;

import cell.states.CellStateFactory;
import neighbor.CellNeighborhood;

public abstract class Automaton2Dim extends Automaton {

    protected int width;

    protected int height;

    public Automaton2Dim(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood, int width, int height) {
        super(cellStateFactory, cellNeighborhood);
        this.width = width;
        this.height = height;
        initialize();
    }

}
