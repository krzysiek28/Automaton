package cell;

import cell.coordinates.CellCoordinates;
import cell.states.CellState;

import java.util.Objects;

public class Cell {

    private CellState state;

    private CellCoordinates coordinates;

    public Cell(CellState state, CellCoordinates coordinates){
        this.state = state;
        this.coordinates = coordinates;
    }

    public CellState getState() {
        return state;
    }

    public CellCoordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return Objects.equals(getState(), cell.getState()) &&
                Objects.equals(getCoordinates(), cell.getCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getState(), getCoordinates());
    }
}
