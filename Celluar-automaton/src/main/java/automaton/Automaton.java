package automaton;

import cell.coordinates.CellCoordinates;
import cell.states.CellState;
import cell.states.CellStateFactory;
import neighbor.CellNeighborhood;

import java.util.HashMap;
import java.util.Map;

public abstract class Automaton {

    public Map<CellCoordinates, CellState> cells = new HashMap<>();

    private CellNeighborhood neighborStrategy;

    private CellStateFactory stateFactory;

    public Automaton(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood) {
        stateFactory = cellStateFactory;
        neighborStrategy = cellNeighborhood;
    }

    protected void initialize(){

    }
}
