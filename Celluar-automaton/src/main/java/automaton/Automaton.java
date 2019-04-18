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
            try {
                coordinates = nextCoordinates(coordinates);
                state = stateFactory.initialState(coordinates);
                cells.put(coordinates, state);
            } catch (Exception e){
                System.out.println("dupa");
            }
        }
    }

    protected abstract CellCoordinates initialCoordinates();

    protected abstract boolean hasNextCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellCoordinates nextCoordinates(CellCoordinates cellCoordinates);

    public Automaton nextState(){
        Automaton newAutomaton = newInstance(stateFactory, neighborStrategy);
        CellIterator previousIteration = cellIterator();
        CellIterator nextIteration = newAutomaton.cellIterator();

        while(previousIteration.hasNext()){
            Cell cell = previousIteration.next();
            Set<CellCoordinates> neighborsCoordinates = neighborStrategy.cellNeighbors(cell.getCoordinates());
            Set<Cell> neighbors = mapCoordinates(neighborsCoordinates);
            CellState newState = nextCellState(cell, neighbors);
            nextIteration.next();
            nextIteration.setState(newState);
        }
        return newAutomaton;
    }

    protected abstract Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood);

    private CellIterator cellIterator(){
        return new CellIterator(initialCoordinates());
    }

    private Set<Cell> mapCoordinates(Set<CellCoordinates> cellCoordinates){
        Set<Cell> cellMap = new HashSet<>();
        for(CellCoordinates coordinates : cellCoordinates){
            cellMap.add(new Cell(cells.get(coordinates), coordinates));
        }
        return cellMap;
    }

    protected abstract CellState nextCellState(Cell currentCell, Set<Cell> neighborsStates);

    public Map<CellCoordinates, CellState> getCells(){
        return this.cells;
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
