package game;

import automaton.Automaton;
import automaton.Automaton2Dim;
import cell.Cell;
import cell.states.BinaryState;
import cell.states.CellState;
import cell.states.CellStateFactory;
import neighbor.CellNeighborhood;

import java.util.List;
import java.util.Set;

public class GameOfLife extends Automaton2Dim {

    private List<Integer> aliveRules;

    private List<Integer> deadRules;

    public GameOfLife(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood, int width, int height,
                      List<Integer> aliveRules, List<Integer> deadRules){
        super(cellStateFactory, cellNeighborhood, width, height);
        this.aliveRules = aliveRules;
        this.deadRules = deadRules;
    }

    public Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood){
        return new GameOfLife(cellStateFactory, cellNeighborhood, width, height, aliveRules, deadRules);
    }

    @Override
    public CellState nextCellState(Cell currentCell, Set<Cell> neighborState){
        int livingCount = resolveLivingCellCount(neighborState);

        if(currentCell.getState() == BinaryState.ALIVE){
            return resolveNewState(livingCount, aliveRules);
        } else {
            return resolveNewState(livingCount, deadRules);
        }
    }

    private int resolveLivingCellCount(Set<Cell> neighborState) {
        int livingCount = 0;
        for(Cell cell : neighborState){
            if(cell.getState() == BinaryState.ALIVE) {
                livingCount++;
            }
        }
        return livingCount;
    }

    private CellState resolveNewState(int livingCount, List<Integer> rules) {
        for (Integer element : rules) {
            if (livingCount == element) {
                return BinaryState.ALIVE;
            }
        }
        return BinaryState.DEAD;
    }
}
