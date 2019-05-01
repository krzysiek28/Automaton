package automaton;

import cell.Cell;
import cell.coordinates.CellCoordinates;
import cell.states.CellState;
import cell.states.CellStateFactory;
import neighbor.CellNeighborhood;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Automaton {

    protected Map<CellCoordinates, CellState> cells = new HashMap<>();

    private Set<Cell> cellSet = new HashSet<>();

    private CellNeighborhood neighborStrategy;

    private CellStateFactory stateFactory;

    public Automaton(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood) {
        stateFactory = cellStateFactory;
        neighborStrategy = cellNeighborhood;
    }

    protected void initializeCoordinates(){
        CellCoordinates coordinates = initialCoordinates();
        CellState state;
        while(hasNextCoordinates(coordinates)){
            coordinates = nextCoordinates(coordinates);
            state = stateFactory.initialState(coordinates);
            cells.put(coordinates, state);
            cellSet.add(new Cell(state, coordinates));
        }
    }

    protected abstract CellCoordinates initialCoordinates();

    protected abstract boolean hasNextCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellCoordinates nextCoordinates(CellCoordinates cellCoordinates);

    public Automaton nextState(){
        Automaton newAutomaton = newInstance(stateFactory, neighborStrategy);
        CellIterator previousIteration = cellIterator();
        CellIterator nextIteration = newAutomaton.cellIterator();

        cellSet = new HashSet<>();
        while(previousIteration.hasNext()){
            CellState newState = resolveNewState(previousIteration);
            Cell newCell = nextIteration.next();
            nextIteration.setState(newState);
            newCell.setState(newState);
            cellSet.add(newCell);
        }
        return newAutomaton;
    }

    private CellState resolveNewState(CellIterator previousIteration) {
        Cell cell = previousIteration.next();
        Set<CellCoordinates> neighborsCoordinates = neighborStrategy.cellNeighbors(cell.getCoordinates());
        Set<Cell> neighbors = mapCoordinates(neighborsCoordinates);
        return nextCellState(cell, neighbors);
    }

    public void changeCellState(Cell cell){
        cells.put(cell.getCoordinates(), cell.getState());
    }

    protected abstract Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood);

    private CellIterator cellIterator(){
        return new CellIterator(initialCoordinates());
    }

    private Set<Cell> mapCoordinates(Set<CellCoordinates> cellCoordinates){
        Set<Cell> cellSet = new HashSet<>();
        for(CellCoordinates coordinates : cellCoordinates){
            cellSet.add(new Cell(cells.get(coordinates), coordinates));
        }
        return cellSet;
    }

    protected abstract CellState nextCellState(Cell currentCell, Set<Cell> neighborsStates);

    public Map<CellCoordinates, CellState> getCells(){
        return this.cells;
    }

    public Set<Cell> getCellSet(){
        return cellSet;
    }

    public class CellIterator{
        private CellCoordinates currentCoordinates;

        public CellIterator(CellCoordinates initialCoordinates){
            this.currentCoordinates = initialCoordinates;
        }

        public boolean hasNext(){
            return hasNextCoordinates(currentCoordinates);
        }

        public Cell next(){
            currentCoordinates = nextCoordinates(currentCoordinates);
            return new Cell(cells.get(currentCoordinates), currentCoordinates);
        }

        public void setState(CellState cellState){
            cells.put(currentCoordinates, cellState);
        }
    }
}
