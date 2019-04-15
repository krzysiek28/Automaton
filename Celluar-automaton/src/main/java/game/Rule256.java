package game;

import automaton.Automaton;
import automaton.Automaton1Dim;
import cell.Cell;
import cell.states.BinaryState;
import cell.states.CellState;
import cell.states.CellStateFactory;
import neighbor.CellNeighborhood;

import java.util.Set;

public class Rule256 extends Automaton1Dim {

    private int rule;

    public Rule256(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood, int size, int rule){
        super(cellStateFactory, cellNeighborhood, size);
        this.rule = rule;
    }

    @Override
    public Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood){
        return new Rule256(cellStateFactory, cellNeighborhood, size, rule);
    }

    @Override
    public CellState nextCellState(Cell currentCell, Set<Cell> neighborCells) {
        int[] numberOfRule = initRules();
        int ruleOperator = rule;
        resolveNewStateNumberOfRule(numberOfRule, ruleOperator);

        CellState[] neighborCellStates = new CellState[3];
        establishCellStates(neighborCells, neighborCellStates);

        if(neighborCellStates[0] == BinaryState.ALIVE &&
                neighborCellStates[1] == BinaryState.ALIVE &&
                neighborCellStates[2] == BinaryState.ALIVE)
        {
            return resolveNewState(numberOfRule, 0);
        }
        //dla 2 bitu
        else if(neighborCellStates[0] == BinaryState.ALIVE &&
                neighborCellStates[1] == BinaryState.ALIVE &&
                neighborCellStates[2] == BinaryState.DEAD)
        {
            return resolveNewState(numberOfRule, 1);
        }
        //dla 3 bitu
        else if(neighborCellStates[0] == BinaryState.ALIVE &&
                neighborCellStates[1] == BinaryState.DEAD &&
                neighborCellStates[2] == BinaryState.ALIVE)
        {
            return resolveNewState(numberOfRule, 2);
        }
        //dla 4 bitu
        else if(neighborCellStates[0] == BinaryState.ALIVE &&
                neighborCellStates[1] == BinaryState.DEAD &&
                neighborCellStates[2] == BinaryState.DEAD)
        {
            return resolveNewState(numberOfRule, 3);
        }
        //dla 5 bitu
        else if(neighborCellStates[0] == BinaryState.DEAD &&
                neighborCellStates[1] == BinaryState.ALIVE &&
                neighborCellStates[2] == BinaryState.ALIVE)
        {
            return resolveNewState(numberOfRule, 4);
        }
        //dla 6 bitu
        else if(neighborCellStates[0] == BinaryState.DEAD &&
                neighborCellStates[1] == BinaryState.ALIVE &&
                neighborCellStates[2] == BinaryState.DEAD)
        {
            return resolveNewState(numberOfRule, 5);
        }
        //dla 7 bitu
        else if(neighborCellStates[0] == BinaryState.DEAD &&
                neighborCellStates[1] == BinaryState.DEAD &&
                neighborCellStates[2] == BinaryState.ALIVE)
        {
            return resolveNewState(numberOfRule, 6);
        }
        //dla 8 bitu
        else {
            return resolveNewState(numberOfRule, 7);
        }
    }

    private int[] initRules() {
        int[] numberOfRule = new int[8];
        for(int i=0; i<8; i++){
            numberOfRule[i] = 0;
        }
        return numberOfRule;
    }

    private void resolveNewStateNumberOfRule(int[] numberOfRule, int ruleOperator) {
        int i = 7;
        while(ruleOperator != 0){
            numberOfRule[i--] = ruleOperator % 2;
            ruleOperator /= 2;
        }
    }

    private void establishCellStates(Set<Cell> neighborCells, CellState[] neighborCellStates) {
        int i = 0;
        for(Cell cell : neighborCells){
            if(cell.getState() == BinaryState.ALIVE){
                neighborCellStates[i] = BinaryState.ALIVE;
            } else{
                neighborCellStates[i] = BinaryState.DEAD;
            }
            i++;
        }
    }

    private CellState resolveNewState(int[] numberOfRule, int i) {
        if (numberOfRule[i] == 1) {
            return BinaryState.ALIVE;
        }
        else {
            return BinaryState.DEAD;
        }
    }
}
