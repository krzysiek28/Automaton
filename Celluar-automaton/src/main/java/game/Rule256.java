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

        //dla 1 bitu
        if(neighborCellStates[0] == BinaryState.ALIVE &&
                neighborCellStates[1] == BinaryState.ALIVE &&
                neighborCellStates[2] == BinaryState.ALIVE)
        {
            if(numberOfRule[0]==1)
                return BinaryState.ALIVE;
            else
                return BinaryState.DEAD;
        }
        //dla 2 bitu
        else if(neighborCellStates[0] == BinaryState.ALIVE &&
                neighborCellStates[1] == BinaryState.ALIVE &&
                neighborCellStates[2] == BinaryState.DEAD)
        {
            if(numberOfRule[1]==1)
                return BinaryState.ALIVE;
            else
                return BinaryState.DEAD;
        }
        //dla 3 bitu
        else if(neighborCellStates[0] == BinaryState.ALIVE &&
                neighborCellStates[1] == BinaryState.DEAD &&
                neighborCellStates[2] == BinaryState.ALIVE)
        {
            if(numberOfRule[2]==1)
                return BinaryState.ALIVE;
            else
                return BinaryState.DEAD;
        }
        //dla 4 bitu
        else if(neighborCellStates[0] == BinaryState.ALIVE &&
                neighborCellStates[1] == BinaryState.DEAD &&
                neighborCellStates[2] == BinaryState.DEAD)
        {
            if(numberOfRule[3]==1)
                return BinaryState.ALIVE;
            else
                return BinaryState.DEAD;
        }
        //dla 5 bitu
        else if(neighborCellStates[0] == BinaryState.DEAD &&
                neighborCellStates[1] == BinaryState.ALIVE &&
                neighborCellStates[2] == BinaryState.ALIVE)
        {
            if(numberOfRule[4]==1)
                return BinaryState.ALIVE;
            else
                return BinaryState.DEAD;
        }
        //dla 6 bitu
        else if(neighborCellStates[0] == BinaryState.DEAD &&
                neighborCellStates[1] == BinaryState.ALIVE &&
                neighborCellStates[2] == BinaryState.DEAD)
        {
            if(numberOfRule[5]==1)
                return BinaryState.ALIVE;
            else
                return BinaryState.DEAD;
        }
        //dla 7 bitu
        else if(neighborCellStates[0] == BinaryState.DEAD &&
                neighborCellStates[1] == BinaryState.DEAD &&
                neighborCellStates[2] == BinaryState.ALIVE)
        {
            if(numberOfRule[6]==1)
                return BinaryState.ALIVE;
            else
                return BinaryState.DEAD;
        }
        //dla 8 bitu
        else {
            if(numberOfRule[7]==1)
                return BinaryState.ALIVE;
            else
                return BinaryState.DEAD;
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
}
