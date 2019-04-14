package cell.states;

import cell.coordinates.CellCoordinates;

import java.util.Map;

public class GeneralStateFactory implements CellStateFactory {

    private Map<CellCoordinates, CellState> states;

    public GeneralStateFactory(Map<CellCoordinates, CellState> states){
        this.states = states;
    }

    @Override
    public CellState initialState(CellCoordinates cellCoordinates){
        return states.get(cellCoordinates);
    }
}
