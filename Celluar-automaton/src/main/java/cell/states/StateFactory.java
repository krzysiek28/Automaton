package cell.states;

import cell.coordinates.CellCoordinates;

public class StateFactory implements CellStateFactory {

    private CellState state;

    public StateFactory(CellState state){
        this.state = state;
    }

    @Override
    public CellState initialState(CellCoordinates cellCoordinates){
        return state;
    }
}
