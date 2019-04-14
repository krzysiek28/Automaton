package cell.states;

import cell.coordinates.CellCoordinates;

import java.util.Map;
import java.util.Objects;

public class GeneralStateFactory implements CellStateFactory {

    private Map<CellCoordinates, CellState> states;

    public GeneralStateFactory(Map<CellCoordinates, CellState> states){
        this.states = states;
    }

    @Override
    public CellState initialState(CellCoordinates cellCoordinates){
        return states.get(cellCoordinates);
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneralStateFactory)) return false;
        GeneralStateFactory that = (GeneralStateFactory) o;
        return Objects.equals(states, that.states);
    }

    @Override
    public int hashCode() {
        return Objects.hash(states);
    }
}
