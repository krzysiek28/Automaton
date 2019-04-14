package cell.states;

import cell.coordinates.CellCoordinates;

public interface CellStateFactory {

    CellState initialState(CellCoordinates cellCoordinates);

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);
}
